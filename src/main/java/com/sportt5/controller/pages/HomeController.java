package com.sportt5.controller.pages;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.sportt5.controller.components.HomeAlbumCardController;
import com.sportt5.controller.components.HomeAlbumRowController;
import com.sportt5.controller.components.HomeSongRowController;
import com.sportt5.controller.components.PlayerBarController;
import com.sportt5.model.Albums;
import com.sportt5.model.PageResponse;
import com.sportt5.model.Songs;
import com.sportt5.model.Users;
import com.sportt5.service.HomeService;
import com.sportt5.session.UserSession;
import com.sportt5.util.ApiClient;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class HomeController {
    @FXML private HBox homeAlbumBox;
    @FXML private VBox homeSingleBox;
    @FXML private VBox homeAlbumBox2;

    private final HomeService homeService = new HomeService();
    private final ObjectMapper mapper = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

    @FXML
    public void initialize() {}

    public void loadHomeAlbums() {
        UserSession session = UserSession.getInstance();
        if (session == null || session.getCurrentUserId() == -1) return;

        homeAlbumBox.getChildren().clear();

        new Thread(() -> {
            try {
                List<Albums> albums = homeService.getHomeAlbums();
                Platform.runLater(() -> {
                    for (int i = 0; i < 6; i++) {
                        try {
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com.sportt5/view/home/home-album-card.fxml"));
                            VBox card = loader.load();
                            HomeAlbumCardController ctrl = loader.getController();
                            ctrl.setAlbum(albums.get(i));
                            homeAlbumBox.getChildren().add(card);
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

    public void loadLatestSingles() {
        UserSession session = UserSession.getInstance();
        if (session == null || session.getCurrentUserId() == -1) return;

        homeSingleBox.getChildren().clear();

        new Thread(() -> {
            try {
                List<Songs> songs = homeService.getHomeSingles();

                HttpResponse<String> response = ApiClient.get("users");
                if (response.statusCode() != 200) return;

                JavaType type = mapper.getTypeFactory().constructParametricType(PageResponse.class, Users.class);
                PageResponse<Users> page = mapper.readValue(response.body(), type);
                List<Users> users = page.getContent();
                Map<Integer, String> artistMap = users.stream()
                        .collect(Collectors.toMap(
                                Users::getId,
                                user -> user.getDisplayName() != null ? user.getDisplayName() : "Unknown",
                                (name1, name2) -> name1
                        ));

                Platform.runLater(() -> {
                    for (int i = 0; i < songs.size(); i++) {
                        try {
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com.sportt5/view/home/home-song-row.fxml"));
                            HBox row = loader.load();
                            HomeSongRowController ctrl = loader.getController();
                            Songs s = songs.get(i);
                            ctrl.setSong(i + 1, s, artistMap.getOrDefault(s.getArtistId(), "Unknown"));
                            row.setOnMouseClicked(e -> {
                                if (PlayerBarController.getInstance() != null) PlayerBarController.getInstance().playSong(s);
                            });
                            homeSingleBox.getChildren().add(row);
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

    public void loadLatestAlbums() {
        UserSession session = UserSession.getInstance();
        if (session == null || session.getCurrentUserId() == -1) return;

        homeAlbumBox2.getChildren().clear();

        new Thread(() -> {
            try {
                List<Albums> albums = homeService.getHomeAlbums();

                Platform.runLater(() -> {
                    for (int i = 0; i < albums.size(); i++) {
                        try {
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com.sportt5/view/home/home-album-row.fxml"));
                            HBox row = loader.load();
                            HomeAlbumRowController ctrl = loader.getController();
                            Albums a = albums.get(i);
                            ctrl.setAlbum(i + 1, a);
                            homeAlbumBox2.getChildren().add(row);
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
}