package com.sportt5.controller.components;

import com.sportt5.model.Albums;
import com.sportt5.service.AlbumService;
import com.sportt5.service.SongService;
import com.sportt5.session.UserSession;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.util.StringConverter;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class UploadController {

    @FXML private ComboBox<Object> albumCombo;   // Albums + sentinel "+ New Album"
    @FXML private VBox songRowsContainer;

    private final AlbumService albumService = new AlbumService();
    private final SongService  songService  = new SongService();

    private final List<ArtistSongRowController> rows = new ArrayList<>();

    private static final String NEW_ALBUM_SENTINEL = "__NEW_ALBUM__";

    @FXML
    public void initialize() {
        loadAlbums();
        addRow();          // start with one empty row
    }

    // ── Albums ────────────────────────────────────────────────────────────────

    private void loadAlbums() {
        int artistId = UserSession.getInstance().getCurrentUserId();
        List<Albums> albums = albumService.getAlbumsByArtist(artistId);

        List<Object> items = new ArrayList<>(albums);
        items.add(NEW_ALBUM_SENTINEL);          // sentinel at the bottom

        albumCombo.setItems(FXCollections.observableArrayList(items));
        albumCombo.setConverter(new StringConverter<>() {
            @Override public String toString(Object o) {
                if (o == null) return "";
                if (o instanceof Albums a) return a.getTitle();
                return "+ New Album";
            }
            @Override public Object fromString(String s) { return null; }
        });

        albumCombo.valueProperty().addListener((obs, oldVal, newVal) -> {
            if (NEW_ALBUM_SENTINEL.equals(newVal)) {
                showNewAlbumDialog();
            }
        });
    }

    private void showNewAlbumDialog() {
        Dialog<Void> dialog = new Dialog<>();
        dialog.setTitle("Create New Album");
        dialog.setHeaderText("New Album");
        dialog.getDialogPane().getStylesheets().add(
                getClass().getResource("/com.sportt5/css/artist.css").toExternalForm()
        );

        ButtonType createBtn = new ButtonType("Create", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(createBtn, ButtonType.CANCEL);

        // Form fields
        TextField titleField = new TextField();
        titleField.setPromptText("Album title...");
        titleField.getStyleClass().add("auth-input");

        DatePicker datePicker = new DatePicker(LocalDate.now());
        datePicker.getStyleClass().add("auth-input");

        // Cover image picker
        Label coverPickLabel = new Label("Drop cover or click to browse");
        coverPickLabel.getStyleClass().add("admin-subtitle");
        ImageView coverPreview = new ImageView();
        coverPreview.setFitWidth(80);
        coverPreview.setFitHeight(80);
        coverPreview.setPreserveRatio(true);
        coverPreview.setVisible(false);
        coverPreview.setManaged(false);

        StackPane coverZone = new StackPane(coverPickLabel, coverPreview);
        coverZone.setPrefHeight(100);
        coverZone.getStyleClass().add("upload-drop-zone");

        final File[] selectedCover = {null};

        // Click to pick
        coverZone.setOnMouseClicked(e -> {
            FileChooser fc = new FileChooser();
            fc.setTitle("Select Album Cover");
            fc.getExtensionFilters().add(
                    new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.jpeg", "*.png"));
            File f = fc.showOpenDialog(dialog.getOwner());
            if (f != null) setCoverInDialog(f, selectedCover, coverPickLabel, coverPreview);
        });

        // Drag & drop
        coverZone.setOnDragOver(e -> {
            if (e.getDragboard().hasFiles()) e.acceptTransferModes(TransferMode.COPY);
            e.consume();
        });
        coverZone.setOnDragDropped(e -> {
            var files = e.getDragboard().getFiles();
            if (!files.isEmpty())
                setCoverInDialog(files.get(0), selectedCover, coverPickLabel, coverPreview);
            e.setDropCompleted(true);
            e.consume();
        });

        // Status label for feedback
        Label statusLabel = new Label();
        statusLabel.getStyleClass().add("field-label");
        statusLabel.setVisible(false);
        statusLabel.setManaged(false);

        VBox content = new VBox(10,
                new Label("TITLE"),       titleField,
                new Label("RELEASE DATE"), datePicker,
                new Label("COVER IMAGE"),  coverZone,
                statusLabel
        );
        content.setPadding(new javafx.geometry.Insets(10, 0, 0, 0));
        for (var lbl : content.getChildren()) {
            if (lbl instanceof Label l) l.getStyleClass().add("field-label");
        }
        dialog.getDialogPane().setContent(content);

        // Disable create button if title is empty
        var createButton = dialog.getDialogPane().lookupButton(createBtn);
        createButton.setDisable(true);
        titleField.textProperty().addListener((obs, o, n) ->
                createButton.setDisable(n.trim().isEmpty()));

        // Override default close so we can do async work inside
        createButton.addEventFilter(javafx.event.ActionEvent.ACTION, event -> {
            event.consume(); // prevent dialog auto-close

            String title = titleField.getText().trim();
            LocalDate date = datePicker.getValue();

            statusLabel.setText("Creating album…");
            statusLabel.setVisible(true);
            statusLabel.setManaged(true);
            createButton.setDisable(true);

            new Thread(() -> {
                Albums created = albumService.createAlbum(title, date);

                if (created == null) {
                    Platform.runLater(() -> {
                        statusLabel.setText("✗ Failed to create album.");
                        createButton.setDisable(false);
                    });
                    return;
                }

                // Upload cover if one was selected
                if (selectedCover[0] != null) {
                    Platform.runLater(() -> statusLabel.setText("Uploading cover…"));
                    boolean coverOk = albumService.uploadAlbumCover(created.getId(), selectedCover[0]);
                    if (coverOk) {
                        created.setCoverUrl(selectedCover[0].toURI().toString());
                    }
                }

                Platform.runLater(() -> {
                    // Insert before sentinel, select it, close dialog
                    int sentinelIdx = albumCombo.getItems().indexOf(NEW_ALBUM_SENTINEL);
                    albumCombo.getItems().add(sentinelIdx, created);
                    albumCombo.setValue(created);
                    dialog.close();
                });
            }).start();
        });

        dialog.showAndWait();
    }

    // Helper to update cover preview inside the dialog
    private void setCoverInDialog(File f, File[] holder,
                                  Label label, ImageView preview) {
        holder[0] = f;
        label.setVisible(false);
        label.setManaged(false);
        preview.setImage(new javafx.scene.image.Image(f.toURI().toString()));
        preview.setVisible(true);
        preview.setManaged(true);
    }

    // ── Song rows ─────────────────────────────────────────────────────────────

    @FXML
    private void handleAddTrack() {
        addRow();
    }

    private void addRow() {
        try {
            // Use class-relative resource loading to handle the named module structure
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/com.sportt5/view/artist/song_row.fxml"));
            VBox rowNode = loader.load();
            ArtistSongRowController ctrl = loader.getController();
            ctrl.setupDragDrop();

            ctrl.setOnRemove(() -> {
                songRowsContainer.getChildren().remove(rowNode);
                rows.remove(ctrl);
            });

            rows.add(ctrl);
            songRowsContainer.getChildren().add(rowNode);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // ── Submit ────────────────────────────────────────────────────────────────

    @FXML
    private void handleSubmit() {
        Object selectedAlbum = albumCombo.getValue();
        if (!(selectedAlbum instanceof Albums album)) {
            showAlert("Please select an album.");
            return;
        }
        if (rows.isEmpty()) {
            showAlert("Please add at least one track.");
            return;
        }
        for (ArtistSongRowController row : rows) {
            if (row.getTitle().isEmpty())    { showAlert("Every track needs a title.");      return; }
            if (row.getAudioFile() == null)  { showAlert("Every track needs an audio file."); return; }
        }

        // Snapshot rows so the list can't change mid-upload
        List<ArtistSongRowController> snapshot = new ArrayList<>(rows);

        new Thread(() -> {
            for (ArtistSongRowController row : snapshot) {
                Platform.runLater(() -> {
                    row.setProgress(ProgressBar.INDETERMINATE_PROGRESS);
                    row.setStatus("Uploading…");
                });

                int songId = songService.createSong(
                        row.getTitle(), album.getId(), 0, "NORMAL", row.getAudioFile());

                if (songId == -1) {
                    Platform.runLater(() -> row.setStatus("✗ Upload failed"));
                    continue;
                }

                if (row.getCoverFile() != null) {
                    Platform.runLater(() -> row.setStatus("Uploading cover…"));
                    songService.uploadSongCover(songId, row.getCoverFile());
                }

                Platform.runLater(() -> {
                    row.setProgress(1.0);
                    row.setStatus("✓ Done — Song ID: " + songId);
                });
            }
        }).start();
    }

    private void showAlert(String msg) {
        new Alert(Alert.AlertType.WARNING, msg).showAndWait();
    }
}