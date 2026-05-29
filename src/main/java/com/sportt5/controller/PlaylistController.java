package com.sportt5.controller;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.sportt5.model.Albums;
import com.sportt5.model.PageResponse;
import com.sportt5.model.Songs;
import com.sportt5.service.AlbumService;
import com.sportt5.session.UserSession;
import com.sportt5.util.ApiClient;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

import java.io.IOException;
import java.net.http.HttpResponse;
import java.util.List;

public class PlaylistController {

    @FXML private HBox albumCardsBox;
    @FXML private Label playlistEyebrow;
    @FXML private Label playlistHeading;
    @FXML private Label playlistSongCount;
    @FXML private Label playlistDuration;
    @FXML private VBox songListVBox;
    @FXML private ImageView albumCoverImage;

    private final AlbumService albumService = new AlbumService();
    private final ObjectMapper mapper = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

    @FXML
    public void initialize() {
    }

    public void loadUserAlbums() {
        UserSession session = UserSession.getInstance();
        if (session == null || session.getCurrentUserId() == -1) return;

        int userId = session.getCurrentUserId();
        albumCardsBox.getChildren().clear();
        resetDetailSection();

        new Thread(() -> {
            try {
                List<Albums> albums = albumService.getByArtist(userId);
                Platform.runLater(() -> {
                    for (Albums album : albums) {
                        try {
                            FXMLLoader loader = new FXMLLoader(
                                    getClass().getResource("/com.sportt5/view/library/album-card-view.fxml"));
                            VBox card = loader.load();
                            AlbumCardController ctrl = loader.getController();
                            ctrl.setAlbum(album);
                            card.setOnMouseClicked(e -> loadAlbum(album));
                            card.getStyleClass().add("album-card-clickable");
                            albumCardsBox.getChildren().add(card);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void loadAlbum(Albums album) {
        playlistEyebrow.setText("ALBUM");
        playlistHeading.setText(album.getTitle());
        playlistSongCount.setText("Loading...");
        playlistDuration.setText("");
        songListVBox.getChildren().clear();

        String coverUrl = ApiClient.resolveUrl(album.getCoverUrl());
        if (albumCoverImage != null && coverUrl != null) {
            albumCoverImage.setImage(new Image(coverUrl, true));
        }

        new Thread(() -> {
            try {
                HttpResponse<String> response = ApiClient.get(
                        "songs/album/" + album.getId() + "?size=50&sort=trackNumber");

                if (response.statusCode() != 200) return;

                JavaType type = mapper.getTypeFactory()
                        .constructParametricType(PageResponse.class, Songs.class);
                PageResponse<Songs> page = mapper.readValue(response.body(), type);
                List<Songs> songs = page.getContent();

                int totalSeconds = songs.stream().mapToInt(Songs::getDurationSeconds).sum();
                String duration = String.format("%d min %d sec", totalSeconds / 60, totalSeconds % 60);

                Platform.runLater(() -> {
                    playlistSongCount.setText(songs.size() + " songs");
                    playlistDuration.setText(duration);
                    songListVBox.getChildren().clear();

                    for (int i = 0; i < songs.size(); i++) {
                        try {
                            FXMLLoader loader = new FXMLLoader(
                                    getClass().getResource("/com.sportt5/view/library/song-row-view.fxml"));
                            HBox row = loader.load();
                            SongRowController rowCtrl = loader.getController();
                            rowCtrl.setSong(i + 1, songs.get(i), album.getTitle());
                            songListVBox.getChildren().add(row);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
                Platform.runLater(() -> playlistSongCount.setText("Failed to load"));
            }
        }).start();
    }

    private void resetDetailSection() {
        playlistEyebrow.setText("SELECT AN ALBUM");
        playlistHeading.setText("—");
        playlistSongCount.setText("");
        playlistDuration.setText("");
        songListVBox.getChildren().clear();
        if (albumCoverImage != null) albumCoverImage.setImage(null);
    }
}
