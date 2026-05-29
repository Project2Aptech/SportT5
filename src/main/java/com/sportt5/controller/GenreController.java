package com.sportt5.controller;

import com.sportt5.model.Genres;
import com.sportt5.model.Songs;
import com.sportt5.service.GenreService;
import com.sportt5.service.SongService;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.*;

import java.util.List;

public class GenreController {

    @FXML private FlowPane genreChipsBox;
    @FXML private VBox songListVBox;
    @FXML private Label songCountLabel;

    private static final String[] THUMB_STYLES = {"thumb-city", "thumb-orange", "thumb-blue", "thumb-gold"};

    private final GenreService genreService = new GenreService();
    private final SongService  songService  = new SongService();

    @FXML
    public void initialize() {
        loadGenres();
    }

    private void loadGenres() {
        Task<List<Genres>> task = new Task<>() {
            @Override
            protected List<Genres> call() throws Exception {
                return genreService.getAll();
            }
        };
        task.setOnSucceeded(e -> renderGenreChips(task.getValue()));
        task.setOnFailed(e -> System.err.println("loadGenres failed: " + task.getException()));
        new Thread(task).start();
    }

    private void loadSongsByGenre(int genreId) {
        if (songCountLabel != null) songCountLabel.setText("Loading…");
        if (songListVBox != null) songListVBox.getChildren().clear();

        Task<List<Songs>> task = new Task<>() {
            @Override
            protected List<Songs> call() throws Exception {
                return songService.getByGenre(genreId);
            }
        };
        task.setOnSucceeded(e -> renderSongRows(task.getValue()));
        task.setOnFailed(e -> {
            if (songCountLabel != null) songCountLabel.setText("");
            System.err.println("loadSongsByGenre failed: " + task.getException());
        });
        new Thread(task).start();
    }

    private void renderGenreChips(List<Genres> genres) {
        if (genreChipsBox == null) return;
        genreChipsBox.getChildren().clear();
        for (Genres genre : genres) {
            Label chip = new Label(genre.getName());
            chip.getStyleClass().add("genre-chip");
            chip.setOnMouseClicked(e -> {
                genreChipsBox.getChildren().forEach(n ->
                        n.getStyleClass().removeAll("genre-chip-active"));
                chip.getStyleClass().add("genre-chip-active");
                loadSongsByGenre(genre.getId());
            });
            genreChipsBox.getChildren().add(chip);
        }
        if (!genres.isEmpty()) {
            genreChipsBox.getChildren().get(0).getStyleClass().add("genre-chip-active");
            loadSongsByGenre(genres.get(0).getId());
        }
    }

    private void renderSongRows(List<Songs> songs) {
        if (songListVBox == null) return;
        songListVBox.getChildren().clear();
        if (songCountLabel != null) {
            songCountLabel.setText(songs.size() + " song" + (songs.size() != 1 ? "s" : ""));
        }
        for (int i = 0; i < songs.size(); i++) {
            songListVBox.getChildren().add(buildSongRow(i + 1, songs.get(i)));
        }
    }

    private HBox buildSongRow(int index, Songs song) {
        HBox row = new HBox(16);
        row.getStyleClass().add("song-row");
        row.setAlignment(Pos.CENTER_LEFT);

        Label idx = new Label(String.format("%02d", index));
        idx.setMinWidth(30); idx.setMaxWidth(30);
        idx.getStyleClass().add("table-text");

        StackPane thumb = new StackPane();
        thumb.setPrefSize(34, 34); thumb.setMinSize(34, 34); thumb.setMaxSize(34, 34);
        thumb.getStyleClass().addAll("song-thumb", THUMB_STYLES[(index - 1) % THUMB_STYLES.length]);

        Label title = new Label(song.getTitle());
        title.getStyleClass().add("table-title");
        VBox titleBox = new VBox(title);
        HBox.setHgrow(titleBox, Priority.ALWAYS);

        int secs = song.getDurationSeconds();
        Label dur = new Label(String.format("%d:%02d", secs / 60, secs % 60));
        dur.setMinWidth(45); dur.setMaxWidth(45);
        dur.getStyleClass().add("table-text");

        row.getChildren().addAll(idx, thumb, titleBox, dur);
        return row;
    }
}
