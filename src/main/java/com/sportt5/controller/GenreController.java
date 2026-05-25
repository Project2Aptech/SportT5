package com.sportt5.controller;

import com.sportt5.model.Genres;
import com.sportt5.model.Songs;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;

import java.sql.SQLException;
import java.util.List;

public class GenreController {

    @FXML private FlowPane genreChipsBox;
    @FXML private VBox songListVBox;
    @FXML private Label songCountLabel;

    private static final String[] THUMB_STYLES = {"thumb-city", "thumb-orange", "thumb-blue", "thumb-gold"};

    @FXML
    public void initialize() {
    }


    private void renderGenreChips() {
        genreChipsBox.getChildren().clear();

        Button allChip = new Button("All Genres");
        allChip.getStyleClass().add("genre-chip-active");
        allChip.setOnAction(e -> selectGenre(-1, allChip));
        genreChipsBox.getChildren().add(allChip);

        for (Genres g : dao.getGenres()) {
            Button chip = new Button(g.getName());
            chip.getStyleClass().add("genre-chip");
            chip.setOnAction(e -> selectGenre(g.getId(), chip));
            genreChipsBox.getChildren().add(chip);
        }
    }

    private void selectGenre(int genreId, Button clickedChip) {
        genreChipsBox.getChildren().forEach(n -> {
            if (n instanceof Button btn) btn.getStyleClass().setAll("genre-chip");
        });
        clickedChip.getStyleClass().setAll("genre-chip-active");

        try {
            if (genreId == -1) dao.loadAllSongs();
            else dao.loadSongsByGenre(genreId);
            renderSongs();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void renderSongs() {
        songListVBox.getChildren().clear();
        List<Songs> songs = dao.getSongs();
        songCountLabel.setText(songs.size() + " songs");

        for (int i = 0; i < songs.size(); i++) {
            songListVBox.getChildren().add(buildSongRow(i + 1, songs.get(i)));
        }

        if (songs.isEmpty()) {
            Label empty = new Label("No songs found for this genre.");
            empty.getStyleClass().add("table-text");
            empty.setPadding(new Insets(16));
            songListVBox.getChildren().add(empty);
        }
    }

    private HBox buildSongRow(int rank, Songs song) {
        HBox row = new HBox(16);
        row.getStyleClass().add("song-row");
        row.setPadding(new Insets(10, 12, 10, 12));

        Label rankLabel = new Label(String.format("%02d", rank));
        rankLabel.getStyleClass().add("table-text");
        rankLabel.setMinWidth(30);
        rankLabel.setMaxWidth(30);

        StackPane thumb = new StackPane();
        thumb.setPrefSize(34, 34);
        thumb.setMinSize(34, 34);
        thumb.setMaxSize(34, 34);
        thumb.getStyleClass().addAll("song-thumb", THUMB_STYLES[rank % THUMB_STYLES.length]);

        VBox titleBox = new VBox(2);
        Label titleLabel = new Label(song.getTitle());
        titleLabel.getStyleClass().add("table-title");
        titleBox.getChildren().add(titleLabel);
        HBox.setHgrow(titleBox, Priority.ALWAYS);

        int dur = song.getDuration_seconds();
        Label duration = new Label(String.format("%d:%02d", dur / 60, dur % 60));
        duration.getStyleClass().add("table-text");
        duration.setMinWidth(45);
        duration.setMaxWidth(45);

        row.getChildren().addAll(rankLabel, thumb, titleBox, duration);
        return row;
    }
}
