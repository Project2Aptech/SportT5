package com.sportt5.controller.pages;

import com.sportt5.model.SongResponse;
import com.sportt5.model.Songs;
import com.sportt5.model.UserResponse;
import com.sportt5.model.Users;
import com.sportt5.model.enums.Status;
import com.sportt5.service.AdminService;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.sportt5.model.enums.Status.DELETED;
import static com.sportt5.model.enums.Status.LIVE;

public class AdminSongController {
    @FXML private GridPane tableGrid;
    private final AdminService adminService = new AdminService();
    private List<Songs> songs;
    private List<Users> users;
    private boolean liveFirst = true;

    @FXML
    public void initialize() {
        loadSong();
    }
    public void loadSong(){
        new Thread(() -> {
            try {
                UserResponse responseUser = adminService.getUser();
                users = responseUser.getContent();

                SongResponse responseSong = adminService.getSong();
                songs = responseSong.getContent();

                Platform.runLater(() -> {
                    renderTable(songs,users);
                });
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }).start();
    }
    public void sortSongLive(){
        if (liveFirst) {
            songs.sort(Comparator.comparingInt(
                    s -> s.getStatus() == Status.LIVE ? 1 : 0
            ));
        } else {
            songs.sort(Comparator.comparingInt(
                    s -> s.getStatus() == Status.LIVE ? 0 : 1
            ));
        }

        liveFirst = !liveFirst;

        renderTable(songs, users);

    }
    private void openPublishSong(Songs song) {
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Confirm");
        confirm.setHeaderText(null);
        confirm.setContentText(
                "Are you sure you want to "
                        + " LIVE "
                        + song.getTitle()
                        + "?"
        );

        if (confirm.showAndWait().orElse(ButtonType.CANCEL) != ButtonType.OK) {
            return;
        }

        try {
            boolean success =
                    adminService.updateStatusSong(song.getId());

            if (success) {
                loadSong();
            } else {
                new Alert(
                        Alert.AlertType.ERROR,
                        "Failed to update status song"
                ).showAndWait();
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    private void handleDeleteSong(Songs song) {
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Delete Song");
        confirm.setHeaderText("Delete Song");
        confirm.setContentText(
                "Are you sure you want to delete song "
                        + song.getTitle()
        );

        Optional<ButtonType> result = confirm.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                boolean success = adminService.deleteSong(song.getId());

                if (success) {
                    loadSong();
                }
                else {
                    new Alert(
                            Alert.AlertType.ERROR,
                            "Failed to delete song."
                    ).showAndWait();
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }


    public void renderTable(List<Songs> song, List<Users> artists) {
        tableGrid.getChildren().removeIf(node ->
                GridPane.getRowIndex(node) != null &&
                        GridPane.getRowIndex(node) > 0
        );
        int row = 1;

        for (Songs s : song) {

            VBox songBox = new VBox(2);
            Label title = new Label(s.getTitle());
            title.getStyleClass().add("table-title");

            int minutes = s.getDurationSeconds() / 60;
            int seconds = s.getDurationSeconds() % 60;

            Label sub = new Label(String.format("%d:%02d", minutes, seconds));            sub.getStyleClass().add("table-text");

            songBox.getChildren().addAll(title, sub);

            HBox trackCell = new HBox(12);
            StackPane thumb = new StackPane();
            thumb.getStyleClass().add("admin-thumb");

            trackCell.getChildren().addAll(thumb, songBox);

            // ARTIST
            String artistName = "Unknown";

            for (Users a : artists) {
                if (Objects.equals(s.getArtistId(), a.getId())) {
                    artistName = a.getUsername();
                    break;
                }
            }
            Label artist = new Label(artistName);
            artist.getStyleClass().add("table-text");

            // Play count
            Label playCount = new Label(String.valueOf(s.getPlayCount()));
            playCount.getStyleClass().add("table-text");

            // STATUS
            String status = s.getStatus().name();
            Label statusLabel = new Label(status);
            statusLabel.getStyleClass().add(
                    switch (status) {
                        case "LIVE" -> "plan-premium";
                        case "DELETED" -> "status-inactive";
                        default -> "plan-pro";
                    }
            );
            // ACTION
            HBox actionBox = new HBox(8);

            Button publishBtn = new Button("Publish");

            Button deleteBtn = new Button("Delete️");

            publishBtn.getStyleClass().add("action-edit-btn");
            deleteBtn.getStyleClass().add("action-delete-btn");

            publishBtn.setOnAction(e -> openPublishSong(s));
            deleteBtn.setOnAction(e -> handleDeleteSong(s));

            actionBox.getChildren().addAll(publishBtn, deleteBtn);

            // add to grid
            tableGrid.add(trackCell, 0, row);
            tableGrid.add(artist, 1, row);
            tableGrid.add(playCount, 2, row);
            tableGrid.add(statusLabel, 3, row);
            tableGrid.add(actionBox, 4, row);

            row++;
        }
    }


}
