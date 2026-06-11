package com.sportt5.controller.pages;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.sportt5.controller.AppController;
import com.sportt5.controller.components.PlayerBarController;
import com.sportt5.model.Albums;
import com.sportt5.model.ArtistEarnings;
import com.sportt5.model.Songs;
import com.sportt5.model.Users;
import com.sportt5.model.enums.Status;
import com.sportt5.service.AlbumService;
import com.sportt5.service.EarningsService;
import com.sportt5.service.FollowService;
import com.sportt5.service.SongService;
import com.sportt5.session.UserSession;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.shape.Rectangle;

import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

public class ArtistDashboardController {

    @FXML private Label  lblSubtitle;
    @FXML private Label  lblTotalRevenue;
    @FXML private Label  lblTrendRevenue;
    @FXML private Label  lblMonthlyListeners;
    @FXML private Label  lblTrendListeners;
    @FXML private Label  lblFollowersGrowth;
    @FXML private Label  lblTrendFollowers;
    @FXML private HBox   revenueBarChart;
    @FXML private GridPane contentTable;
    @FXML private Button btnLibraryCount;
    @FXML private Button btnUploadMusic;


    private final EarningsService earningsService = new EarningsService();
    private final SongService     songService     = new SongService();
    private final AlbumService    albumService    = new AlbumService();
    private final FollowService   followService   = new FollowService();
    private List<Songs>  cachedSongs  = new ArrayList<>();
    private List<Albums> cachedAlbums = new ArrayList<>();

    private static final DateTimeFormatter DATE_FMT =
            DateTimeFormatter.ofPattern("MMM dd, yyyy");

    @FXML
    public void initialize() {
        Users user = UserSession.getInstance().getCurrentUser();
        if (user != null) {
            lblSubtitle.setText("Good morning, " + user.getDisplayName()
                    + ". Here's what's happening with your music today.");
        }

        int artistId = UserSession.getInstance().getCurrentUserId();

        CompletableFuture<List<ArtistEarnings>> earningsFuture =
                CompletableFuture.supplyAsync(earningsService::getMyEarnings);
        CompletableFuture<List<Songs>> songsFuture =
                CompletableFuture.supplyAsync(() -> songService.getSongsByArtist(artistId));
        CompletableFuture<List<Albums>> albumsFuture =
                CompletableFuture.supplyAsync(() -> albumService.getAlbumsByArtist(artistId));
        CompletableFuture<Long> followersFuture =
                CompletableFuture.supplyAsync(followService::getMyFollowerCount);

        CompletableFuture.allOf(earningsFuture, songsFuture, albumsFuture, followersFuture)
                .thenRun(() -> Platform.runLater(() -> {
                    cachedSongs  = songsFuture.join();
                    cachedAlbums = albumsFuture.join();
                    populateMetrics(earningsFuture.join(), followersFuture.join());
                    populateContentLibrary(cachedSongs, cachedAlbums);
                }));

        btnLibraryCount.setOnAction(e -> {
            showAllContent = !showAllContent;
            populateContentLibrary(cachedSongs, cachedAlbums);
        });

        btnUploadMusic.setOnAction(e -> {
            AppController app = AppController.getInstance();
            if (app != null) app.showArtistUploadPage();
        });
    }

    // ── Metrics ──────────────────────────────────────────────────────────────

    private void populateMetrics(List<ArtistEarnings> earnings, long followerCount) {

        // Followers card — use real count, no trend available
        lblFollowersGrowth.setText(formatCompact(followerCount));
        lblTrendFollowers.setText("—");

        if (earnings.isEmpty()) return;

        // Total revenue
        BigDecimal totalRevenue = earnings.stream()
                .map(ArtistEarnings::getAmount)
                .filter(Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        lblTotalRevenue.setText(String.format("$%,.2f", totalRevenue));

        // Total streams (monthly listeners proxy)
        long totalStreams = earnings.stream()
                .mapToLong(ArtistEarnings::getStreamCount).sum();
        lblMonthlyListeners.setText(String.format("%,d", totalStreams));

        // Sort ascending for trend + bar chart
        List<ArtistEarnings> sorted = earnings.stream()
                .filter(e -> e.getPeriodStart() != null)
                .sorted(Comparator.comparing(ArtistEarnings::getPeriodStart))
                .collect(Collectors.toList());

        if (sorted.size() >= 2) {
            ArtistEarnings latest = sorted.get(sorted.size() - 1);
            ArtistEarnings prev   = sorted.get(sorted.size() - 2);

            // Revenue trend
            if (prev.getAmount() != null && prev.getAmount().compareTo(BigDecimal.ZERO) > 0
                    && latest.getAmount() != null) {
                double pct = latest.getAmount()
                        .subtract(prev.getAmount())
                        .divide(prev.getAmount(), 4, java.math.RoundingMode.HALF_UP)
                        .multiply(BigDecimal.valueOf(100))
                        .doubleValue();
                lblTrendRevenue.setText(String.format("%+.1f%%", pct));
            }

            // Streams trend — only set if meaningfully different from revenue trend
            if (prev.getStreamCount() > 0) {
                double pct = (double)(latest.getStreamCount() - prev.getStreamCount())
                        / prev.getStreamCount() * 100;
                lblTrendListeners.setText(String.format("%+.1f%%", pct));
            }

            // Revenue bar chart
            populateBarChart(revenueBarChart,
                    sorted.stream()
                            .map(e -> e.getAmount() == null ? 0.0 : e.getAmount().doubleValue())
                            .collect(Collectors.toList()),
                    18.0);
        }
    }

    private void populateBarChart(HBox container, List<Double> values, double barWidth) {
        container.getChildren().clear();
        if (values.isEmpty()) return;
        double maxVal   = values.stream().mapToDouble(Double::doubleValue).max().orElse(1);
        double maxHeight = 44.0;
        for (Double v : values) {
            double h = maxVal == 0 ? 4 : Math.max(4, (v / maxVal) * maxHeight);
            Region bar = new Region();
            bar.setPrefWidth(barWidth);
            bar.setPrefHeight(h);
            container.getChildren().add(bar);
        }
        container.setAlignment(Pos.BOTTOM_LEFT);
    }

    /** Formats large numbers compactly: 1200 → 1.2K, 1500000 → 1.5M */
    private String formatCompact(long value) {
        if (value >= 1_000_000) return String.format("%.1fM", value / 1_000_000.0);
        if (value >= 1_000)     return String.format("%.1fK", value / 1_000.0);
        return String.valueOf(value);
    }

    // ── Content Library ───────────────────────────────────────────────────────
    private boolean showAllContent = false;

    private void populateContentLibrary(List<Songs> songs, List<Albums> albums) {
        contentTable.getChildren().removeIf(node -> {
            Integer row = GridPane.getRowIndex(node);
            return row != null && row > 0;
        });

        List<ContentItem> allItems = new ArrayList<>();
        songs.stream()
                .sorted(Comparator.comparingLong(Songs::getPlayCount).reversed())
                .forEach(s -> allItems.add(new ContentItem(s)));
        albums.stream()
                .sorted(Comparator.comparing(
                        a -> a.getReleaseDate() == null ? java.time.LocalDate.MIN : a.getReleaseDate(),
                        Comparator.reverseOrder()))
                .forEach(a -> allItems.add(new ContentItem(a)));

        // Show 5 or all depending on toggle state
        List<ContentItem> items = showAllContent
                ? allItems
                : allItems.stream().limit(5).collect(Collectors.toList());

        int row = 1;
        for (ContentItem item : items) {
            final ContentItem fi = item;

            // ── Thumbnail ────────────────────────────────────────────────────
            ImageView thumbImg = new ImageView();
            thumbImg.setFitWidth(38);
            thumbImg.setFitHeight(38);
            thumbImg.setPreserveRatio(false);

            Rectangle clip = new Rectangle(38, 38);
            clip.setArcWidth(8);
            clip.setArcHeight(8);
            thumbImg.setClip(clip);

            if (item.coverUrl != null && !item.coverUrl.isBlank()) {
                new Thread(() -> {
                    try {
                        Image img = new Image(item.coverUrl, 38, 38, false, true, true);
                        Platform.runLater(() -> thumbImg.setImage(img));
                    } catch (Exception ignored) {}
                }).start();
            }

            StackPane thumb = new StackPane(thumbImg);
            thumb.setPrefWidth(38);
            thumb.setPrefHeight(38);
            thumb.getStyleClass().addAll("admin-thumb",
                    item.thumbStyle != null ? item.thumbStyle : "thumb-gray");

            // ── Title + meta ─────────────────────────────────────────────────
            Label titleLbl = new Label(item.title);
            titleLbl.getStyleClass().add("table-title");
            Label metaLbl = new Label(item.meta);
            metaLbl.getStyleClass().add("table-text");
            VBox titleBox = new VBox(2, titleLbl, metaLbl);
            titleBox.setAlignment(Pos.CENTER_LEFT);

            HBox trackCell = new HBox(12, thumb, titleBox);
            trackCell.setAlignment(Pos.CENTER_LEFT);

            if ("SONG".equals(item.type) && item.songRef != null) {
                trackCell.getStyleClass().add("track-cell-clickable");
                trackCell.setOnMouseClicked(e -> {
                    PlayerBarController player = PlayerBarController.getInstance();
                    if (player != null) player.playSong(fi.songRef);
                });
            }

            // ── Other columns ─────────────────────────────────────────────────
            Label dateLabel    = new Label(item.releaseDate);
            dateLabel.getStyleClass().add("table-text");
            Label statusLabel  = new Label(item.status);
            statusLabel.getStyleClass().add(item.statusStyle);
            Label streamsLabel = new Label(item.streams);
            streamsLabel.getStyleClass().add("table-text");

            // ── Action buttons ────────────────────────────────────────────────
            HBox actionBox = new HBox(4);
            actionBox.setAlignment(javafx.geometry.Pos.CENTER_LEFT);

            if ("SONG".equals(item.type)) {
                Button btnEdit = new Button("✎");
                btnEdit.getStyleClass().add("action-edit-btn");
                btnEdit.setOnAction(e -> handleUpdateSong(fi));

                Button btnDelete = new Button("✕");
                btnDelete.getStyleClass().add("action-delete-btn");
                btnDelete.setOnAction(e -> handleDeleteSong(fi));

                actionBox.getChildren().addAll(btnEdit, btnDelete);
            } else {
                Button btnDelete = new Button("✕");
                btnDelete.getStyleClass().add("action-delete-btn");
                btnDelete.setOnAction(e -> handleDeleteAlbum(fi));

                actionBox.getChildren().add(btnDelete);
            }

            // ── Row hover highlight ───────────────────────────────────────────
            String statusStyle = switch (item.statusStyle) {
                case "status-live"   -> "-fx-text-fill: #1db954; -fx-font-weight: bold;";
                case "status-review" -> "-fx-text-fill: #ef4444; -fx-font-weight: bold;";
                default              -> "-fx-text-fill: #a0a0a0; -fx-font-weight: bold;";
            };

            statusLabel.setStyle(statusStyle);

            List<javafx.scene.Node> rowNodes =
                    List.of(trackCell, dateLabel, statusLabel, streamsLabel, actionBox);
            for (javafx.scene.Node node : rowNodes) {
                node.setOnMouseEntered(e -> rowNodes.forEach(n -> {
                    if (n == statusLabel) {
                        n.setStyle(statusStyle + " -fx-background-color: rgba(255,255,255,0.04);");
                    } else if (n != actionBox) {
                        n.setStyle("-fx-background-color: rgba(255,255,255,0.04);");
                    }
                }));
                node.setOnMouseExited(e -> rowNodes.forEach(n -> {
                    if (n == statusLabel) {
                        n.setStyle(statusStyle);
                    } else if (n != actionBox) {
                        n.setStyle("");
                    }
                }));
            }

            contentTable.add(trackCell,    0, row);
            contentTable.add(dateLabel,    1, row);
            contentTable.add(statusLabel,  2, row);
            contentTable.add(streamsLabel, 3, row);
            contentTable.add(actionBox,    4, row);
            row++;
        }

        // ── Bottom button ─────────────────────────────────────────────────────
        if (showAllContent) {
            btnLibraryCount.setText("Show Less");
        } else {
            int remaining = allItems.size() - 5;
            btnLibraryCount.setText("View All Content Library (" + allItems.size() + " Items)");
        }
    }

    private void handleUpdateSong(ContentItem item) {
        // Fetch albums for the dropdown on a background thread first
        int artistId = UserSession.getInstance().getCurrentUserId();
        CompletableFuture.supplyAsync(() -> albumService.getAlbumsByArtist(artistId))
                .thenAccept(albums -> Platform.runLater(() ->
                        showUpdateDialog(item, albums)));
    }

    private void showUpdateDialog(ContentItem item, List<Albums> albums) {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Update Song");
        dialog.setHeaderText(null);

        // ── Form fields ──────────────────────────────────────────────────────
        javafx.scene.control.TextField titleField =
                new javafx.scene.control.TextField(item.title);
        titleField.setPromptText("Song title");

        javafx.scene.control.ComboBox<Albums> albumCombo =
                new javafx.scene.control.ComboBox<>();
        albumCombo.getItems().addAll(albums);
        albumCombo.setConverter(new javafx.util.StringConverter<>() {
            public String toString(Albums a)   { return a == null ? "" : a.getTitle(); }
            public Albums fromString(String s) { return null; }
        });
        albums.stream()
                .filter(a -> a.getId() == item.albumId)
                .findFirst()
                .ifPresent(albumCombo::setValue);
        albumCombo.setMaxWidth(Double.MAX_VALUE);

        javafx.scene.control.ComboBox<String> accountTypeCombo =
                new javafx.scene.control.ComboBox<>();
        accountTypeCombo.getItems().addAll("NORMAL", "PRO", "PREMIUM");
        accountTypeCombo.setValue(item.requiredAccountType != null
                ? item.requiredAccountType : "NORMAL");
        accountTypeCombo.setMaxWidth(Double.MAX_VALUE);

        javafx.scene.control.ComboBox<String> statusCombo =
                new javafx.scene.control.ComboBox<>();
        statusCombo.getItems().addAll("LIVE", "PENDING");
        statusCombo.setValue(item.status != null ? item.status : "LIVE");
        statusCombo.setMaxWidth(Double.MAX_VALUE);
        statusCombo.getStyleClass().add("dialog-combo");

        // ── Layout ───────────────────────────────────────────────────────────
        Label heading = new Label("Edit Song");
        heading.getStyleClass().add("dialog-heading");

        Label titleLbl       = new Label("Title");         titleLbl.getStyleClass().add("dialog-label");
        Label albumLbl       = new Label("Album");         albumLbl.getStyleClass().add("dialog-label");
        Label accountTypeLbl = new Label("Required Tier"); accountTypeLbl.getStyleClass().add("dialog-label");

        titleField.getStyleClass().add("dialog-field");
        albumCombo.getStyleClass().add("dialog-combo");
        accountTypeCombo.getStyleClass().add("dialog-combo");

        Label statusLbl = new Label("Status");
        statusLbl.getStyleClass().add("dialog-label");

        VBox form = new VBox(14,
                heading,
                new VBox(6, titleLbl, titleField),
                new VBox(6, albumLbl, albumCombo),
                new VBox(6, accountTypeLbl, accountTypeCombo),
                new VBox(6, statusLbl, statusCombo)
        );
        form.setPadding(new javafx.geometry.Insets(24));
        form.setPrefWidth(380);
        form.getStyleClass().add("dialog-form");

        dialog.getDialogPane().setContent(form);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        // ── Style the dialog pane itself ──────────────────────────────────────
        dialog.getDialogPane().getStyleClass().add("custom-dialog");
        dialog.getDialogPane().getStylesheets().add(
                getClass().getResource("/com.sportt5/css/artist.css").toExternalForm()
        );

        // ── Style OK / Cancel buttons ─────────────────────────────────────────
        javafx.scene.Node okBtn     = dialog.getDialogPane().lookupButton(ButtonType.OK);
        javafx.scene.Node cancelBtn = dialog.getDialogPane().lookupButton(ButtonType.CANCEL);
        okBtn.getStyleClass().add("dialog-btn-ok");
        cancelBtn.getStyleClass().add("dialog-btn-cancel");

        dialog.showAndWait().ifPresent(result -> {
            if (result != ButtonType.OK) return;

            String newTitle = titleField.getText().trim();
            if (newTitle.isEmpty()) {
                showErrorAlert("Validation Error", "Title cannot be empty.");
                return;
            }

            Albums selectedAlbum = albumCombo.getValue();
            if (selectedAlbum == null) {
                showErrorAlert("Validation Error", "Please select an album.");
                return;
            }

            String accountType = accountTypeCombo.getValue();

            CompletableFuture.supplyAsync(() ->
                            songService.updateSong(
                                    item.id, newTitle, selectedAlbum.getId(),
                                    item.durationSeconds, item.trackNumber,
                                    accountType, statusCombo.getValue()))   // ← add statusCombo.getValue()
                    .thenAccept(success -> Platform.runLater(() -> {
                        if (success) {
                            showConfirmationToast("\"" + newTitle + "\" updated.");
                            refreshContentLibrary();
                        } else {
                            showErrorAlert("Update Failed",
                                    "Could not update \"" + item.title + "\".");
                        }
                    }));
        });
    }

    private void onRowAction(ContentItem item, Button sourceBtn) {
        ContextMenu menu = new ContextMenu();

        if ("SONG".equals(item.type)) {
            MenuItem updateItem = new MenuItem("✎  Update");
            updateItem.setOnAction(e -> handleUpdateSong(item));

            if (!"LIVE".equals(item.status)) {
                MenuItem publishItem = new MenuItem("✓  Publish");
                publishItem.setOnAction(e -> handlePublish(item));
                menu.getItems().add(publishItem);
            }

            MenuItem deleteItem = new MenuItem("⌫  Delete");
            deleteItem.setOnAction(e -> handleDeleteSong(item));

            menu.getItems().addAll(updateItem, deleteItem);
        } else {
            MenuItem deleteItem = new MenuItem("⌫  Delete Album");
            deleteItem.setOnAction(e -> handleDeleteAlbum(item));
            menu.getItems().add(deleteItem);
        }

        menu.show(sourceBtn, javafx.geometry.Side.BOTTOM, 0, 4);
    }

    private void handlePublish(ContentItem item) {
        CompletableFuture.supplyAsync(() -> songService.publishSong(item.id))
                .thenAccept(success -> Platform.runLater(() -> {
                    if (success) {
                        showConfirmationToast("\"" + item.title + "\" is now live.");
                        refreshContentLibrary();
                    } else {
                        showErrorAlert("Publish Failed", "Could not publish \"" + item.title + "\".");
                    }
                }));
    }

    private void handleDeleteSong(ContentItem item) {
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Delete Song");
        confirm.setHeaderText("Delete \"" + item.title + "\"?");
        confirm.setContentText("This action cannot be undone.");
        confirm.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                CompletableFuture.supplyAsync(() -> songService.deleteSong(item.id))
                        .thenAccept(success -> Platform.runLater(() -> {
                            if (success) {
                                showConfirmationToast("\"" + item.title + "\" deleted.");
                                refreshContentLibrary();
                            } else {
                                showErrorAlert("Delete Failed",
                                        "Could not delete \"" + item.title + "\".");
                            }
                        }));
            }
        });
    }

    private void handleDeleteAlbum(ContentItem item) {
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Delete Album");
        confirm.setHeaderText("Delete album \"" + item.title + "\"?");
        confirm.setContentText("This will not delete the songs inside it.");
        confirm.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                CompletableFuture.supplyAsync(() -> albumService.deleteAlbum(item.id))
                        .thenAccept(success -> Platform.runLater(() -> {
                            if (success) {
                                showConfirmationToast("Album \"" + item.title + "\" deleted.");
                                refreshContentLibrary();
                            } else {
                                showErrorAlert("Delete Failed",
                                        "Could not delete \"" + item.title + "\".");
                            }
                        }));
            }
        });
    }

    private void refreshContentLibrary() {
        int artistId = UserSession.getInstance().getCurrentUserId();
        CompletableFuture<List<Songs>> songsFuture =
                CompletableFuture.supplyAsync(() -> songService.getSongsByArtist(artistId));
        CompletableFuture<List<Albums>> albumsFuture =
                CompletableFuture.supplyAsync(() -> albumService.getAlbumsByArtist(artistId));
        CompletableFuture.allOf(songsFuture, albumsFuture)
                .thenRun(() -> Platform.runLater(() -> {
                    cachedSongs  = songsFuture.join();
                    cachedAlbums = albumsFuture.join();
                    populateContentLibrary(cachedSongs, cachedAlbums);
                }));
    }

    private void showConfirmationToast(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.show();
        // Auto-close after 2 seconds
        new java.util.Timer().schedule(new java.util.TimerTask() {
            public void run() { Platform.runLater(alert::close); }
        }, 2000);
    }

    private void showErrorAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // ── Content item ─────────────────────────────────────────────────────────

    static class ContentItem {
        String title, meta, releaseDate, status, statusStyle,
                streams, thumbStyle, type, requiredAccountType, coverUrl;
        int id, albumId, trackNumber, durationSeconds;
        Songs songRef;

        private static final DateTimeFormatter DATE_FMT =
                DateTimeFormatter.ofPattern("MMM dd, yyyy");

        ContentItem(Songs s) {
            songRef             = s;
            type                = "SONG";
            id                  = s.getId();
            albumId             = s.getAlbumId();
            trackNumber         = s.getTrackNumber();
            durationSeconds     = s.getDurationSeconds();
            requiredAccountType = s.getRequiredAccountType() != null
                    ? s.getRequiredAccountType().name() : "NORMAL";
            title               = s.getTitle();
            meta                = "Single • " + formatDuration(s.getDurationSeconds());
            releaseDate         = "—";
            status              = s.getStatus() != null ? s.getStatus().name() : "LIVE";
            statusStyle         = resolveStatusStyle(s.getStatus());
            streams             = String.format("%,d", s.getPlayCount());
            thumbStyle          = "thumb-pink";
            coverUrl            = s.getCoverUrl();
        }

        ContentItem(Albums a) {
            songRef     = null;
            type        = "ALBUM";
            id          = a.getId();
            title       = a.getTitle();
            meta        = "Album";
            releaseDate = a.getReleaseDate() != null
                    ? a.getReleaseDate().format(DATE_FMT) : "—";
            status      = "LIVE";
            statusStyle = "status-live";
            streams     = "—";
            thumbStyle  = "thumb-gray";
            coverUrl    = a.getCoverUrl();
        }

        private static String formatDuration(int s) {
            return String.format("%d:%02d", s / 60, s % 60);
        }

        private static String resolveStatusStyle(Status status) {
            if (status == null) return "status-live";
            return switch (status) {
                case LIVE    -> "status-live";
                case PENDING -> "status-review";
                default      -> "status-review";
            };
        }
    }
}