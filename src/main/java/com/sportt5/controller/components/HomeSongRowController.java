package com.sportt5.controller.components;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.sportt5.model.Songs;
import com.sportt5.model.Users;
import com.sportt5.model.enums.AccountType;
import com.sportt5.model.enums.RequiredAccountType;
import com.sportt5.service.HomeService;
import com.sportt5.session.UserSession;
import com.sportt5.util.ApiClient;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Path;
import java.nio.file.Paths;

public class HomeSongRowController {
    @FXML private HBox rowRoot;
    @FXML private Label lblIndex;
    @FXML private ImageView imgTrackCover;
    @FXML private Label lblTitle;
    @FXML private Label lblArtist;
    @FXML private Label lockBadge;
    private Songs currentSong;
    private HomeService homeService = new HomeService();
    private final ObjectMapper mapper = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

    @FXML
    public void downloadSongs(ActionEvent event) {
        new Thread(() -> {
            try {
                HttpResponse<String> resp = ApiClient.get("songs/" + currentSong.getId());
                if (resp.statusCode() != 200) return;
                Songs full = mapper.readValue(resp.body(), Songs.class);

                AccountType userType = UserSession.getInstance().getCurrentUser() != null
                        && UserSession.getInstance().getCurrentUser().getAccountType() != null
                        ? UserSession.getInstance().getCurrentUser().getAccountType() : AccountType.NORMAL;
                RequiredAccountType required = full.getRequiredAccountType() != null
                        ? full.getRequiredAccountType() : RequiredAccountType.NORMAL;

                if (userType.ordinal() < required.ordinal()) {
                    Platform.runLater(() -> {
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setTitle("Access Denied");
                        alert.setHeaderText(null);
                        alert.setContentText("Cần tài khoản " + required.name() + " để download bài này");
                        alert.showAndWait();
                    });
                    return;
                }

                HttpClient client = HttpClient.newHttpClient();
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create(full.getFileUrl()))
                        .GET().build();
                String fileName = full.getTitle().replaceAll("[\\\\/:*?\"<>|]", "_") + ".mp3";
                Path path = Paths.get(System.getProperty("user.home"), "Downloads", fileName);
                client.send(request, HttpResponse.BodyHandlers.ofFile(path));

                Platform.runLater(() -> {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Download");
                    alert.setHeaderText(null);
                    alert.setContentText("Downloaded:\n" + path);
                    alert.showAndWait();
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }



    public void setSong(int index, Songs song, String artistName) {
        this.currentSong = song;

        lblTitle.setText(song.getTitle());
        lblArtist.setText(artistName);

        String url = ApiClient.resolveUrl(song.getCoverUrl());
        if (url != null) imgTrackCover.setImage(new Image(url, true));

        boolean locked = !canAccess(song);
        if (locked) applyLockedState(index, song);
        else applyUnlockedState(index, song);

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
}
