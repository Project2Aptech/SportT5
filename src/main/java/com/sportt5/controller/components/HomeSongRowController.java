package com.sportt5.controller.components;

import com.sportt5.model.Songs;
import com.sportt5.model.Playlists;
import com.sportt5.model.Users;
import com.sportt5.model.enums.AccountType;
import com.sportt5.model.enums.RequiredAccountType;
import com.sportt5.service.LibraryService;
import com.sportt5.session.UserSession;
import com.sportt5.util.ApiClient;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class HomeSongRowController {
    @FXML private HBox rowRoot;
    @FXML private Label lblIndex;
    @FXML private ImageView imgTrackCover;
    @FXML private Label lblTitle;
    @FXML private Label lblArtist;
    @FXML private Label lockBadge;
    @FXML private Label likeAction;
    @FXML private Label playlistAction;
    @FXML private Label downloadAction;

    private final LibraryService libraryService = new LibraryService();

    public void setSong(int index, Songs song, String artistName) {
        lblTitle.setText(song.getTitle());
        lblArtist.setText(artistName);
        String url = ApiClient.resolveUrl(song.getCoverUrl());
        if (url != null) imgTrackCover.setImage(new Image(url, true));

        boolean locked = !canAccess(song);
        if (locked) applyLockedState(index, song);
        else applyUnlockedState(index, song);

        wireActions(song);
    }

    private void applyLockedState(int index, Songs song) {
        lblIndex.setText("🔒");

        RequiredAccountType req = song.getRequiredAccountType() != null ? song.getRequiredAccountType() : RequiredAccountType.NORMAL;

        lockBadge.setText(req.name());
        lockBadge.getStyleClass().setAll(
                "lock-badge",
                req == RequiredAccountType.PREMIUM ? "lock-badge-premium" : "lock-badge-pro"
        );
        lockBadge.setVisible(true);
        lockBadge.setManaged(true);

        if (!rowRoot.getStyleClass().contains("song-row-locked")) rowRoot.getStyleClass().add("song-row-locked");
    }

    private void applyUnlockedState(int index, Songs song) {
        lblIndex.setText(String.format("%02d", index));

        lockBadge.setVisible(false);
        lockBadge.setManaged(false);
        rowRoot.getStyleClass().remove("song-row-locked");
    }

    // NORMAL(0) < PRO(1) < PREMIUM(2) — so sánh theo ordinal
    private boolean canAccess(Songs song) {
        UserSession session = UserSession.getInstance();
        if (session == null) return true;
        Users user = session.getCurrentUser();
        if (user == null) return true;

        AccountType userType = user.getAccountType() != null ? user.getAccountType() : AccountType.NORMAL;
        RequiredAccountType required = song.getRequiredAccountType() != null ? song.getRequiredAccountType() : RequiredAccountType.NORMAL;

        return userType.ordinal() >= required.ordinal();
    }

    private void wireActions(Songs song) {
        likeAction.setOnMouseClicked(e -> {
            e.consume();
            new Thread(() -> {
                try {
                    libraryService.likeSong(song.getId());
                    Platform.runLater(() -> showInfo("Liked", "Song added to Liked Songs."));
                } catch (Exception ex) {
                    Platform.runLater(() -> showError("Like song failed", ex.getMessage()));
                }
            }).start();
        });

        playlistAction.setOnMouseClicked(e -> {
            e.consume();
            choosePlaylist(song);
        });

        downloadAction.setOnMouseClicked(e -> {
            e.consume();
            new Thread(() -> {
                try {
                    var path = libraryService.downloadSong(song);
                    Platform.runLater(() -> showInfo("Downloaded", "Saved to " + path));
                } catch (Exception ex) {
                    Platform.runLater(() -> showError("Download failed", ex.getMessage()));
                }
            }).start();
        });
    }

    private void choosePlaylist(Songs song) {
        new Thread(() -> {
            try {
                List<Playlists> playlists = libraryService.getUserPlaylists();
                Platform.runLater(() -> {
                    if (playlists.isEmpty()) {
                        showInfo("No playlists", "Create a playlist first.");
                        return;
                    }
                    Map<String, Playlists> playlistsByTitle = new LinkedHashMap<>();
                    for (Playlists playlist : playlists) playlistsByTitle.put(playlist.getTitle(), playlist);
                    ChoiceDialog<String> dialog = new ChoiceDialog<>(playlists.get(0).getTitle(), playlistsByTitle.keySet());
                    dialog.setTitle("Add to playlist");
                    dialog.setHeaderText("Choose playlist for \"" + song.getTitle() + "\"");
                    dialog.setContentText("Playlist:");
                    dialog.showAndWait()
                            .map(playlistsByTitle::get)
                            .ifPresent(playlist -> addSongToPlaylist(playlist, song));
                });
            } catch (Exception ex) {
                Platform.runLater(() -> showError("Load playlists failed", ex.getMessage()));
            }
        }).start();
    }

    private void addSongToPlaylist(Playlists playlist, Songs song) {
        new Thread(() -> {
            try {
                libraryService.addSongToPlaylist(playlist.getId(), song.getId());
                Platform.runLater(() -> showInfo("Added", "Song added to \"" + playlist.getTitle() + "\"."));
            } catch (Exception ex) {
                Platform.runLater(() -> showError("Add song failed", ex.getMessage()));
            }
        }).start();
    }

    private void showInfo(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, message, ButtonType.OK);
        alert.setHeaderText(title);
        alert.show();
    }

    private void showError(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR, message, ButtonType.OK);
        alert.setHeaderText(title);
        alert.show();
    }
}
