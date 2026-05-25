package com.sportt5.controller;

import com.sportt5.dao.library.library_dao;
import com.sportt5.model.Songs;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.*;

import java.sql.SQLException;
import java.util.ArrayList;

public class LibraryController {

    @FXML private Label favouritesCount;
    @FXML private VBox trackListContainer;
    @FXML private Label tabSaved;
    @FXML private Label tabGenres;
    @FXML private Label tabArtists;

    private library_dao dao;

    // Placeholder until UserSession is implemented
    private static final int CURRENT_USER_ID = 1;

    private static final String[] THUMB_STYLES = {"thumb-city", "thumb-orange", "thumb-blue", "thumb-gold"};

    @FXML
    public void initialize() {
        try {
            dao = new library_dao();
            updateLikedCount();
            loadTab("saved");
            setupTabHandlers();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void updateLikedCount() throws SQLException {
        int count = dao.getLikedCount(CURRENT_USER_ID);
        favouritesCount.setText(count + " songs");
    }

    private void setupTabHandlers() {
        tabSaved.setOnMouseClicked(e -> selectTab(tabSaved, "saved"));
        tabGenres.setOnMouseClicked(e -> selectTab(tabGenres, "genres"));
        tabArtists.setOnMouseClicked(e -> selectTab(tabArtists, "artists"));
    }

    private void selectTab(Label clicked, String tab) {
        tabSaved.getStyleClass().setAll("library-tab");
        tabGenres.getStyleClass().setAll("library-tab");
        tabArtists.getStyleClass().setAll("library-tab");
        clicked.getStyleClass().setAll("library-tab-active");
        try {
            loadTab(tab);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void loadTab(String tab) throws SQLException {
        switch (tab) {
            case "saved"   -> dao.filterByLiked(CURRENT_USER_ID);
            case "genres"  -> dao.viewAllSongs();
            case "artists" -> dao.sortByPlayCount();
        }
        renderSongs(dao.getSonglist(), dao.getAlbumTitles(), dao.getDatesAdded());
    }

    private void renderSongs(ArrayList<Songs> songs, ArrayList<String> albumTitles, ArrayList<String> dates) {
        trackListContainer.getChildren().clear();

        for (int i = 0; i < songs.size(); i++) {
            String album = i < albumTitles.size() ? albumTitles.get(i) : "";
            String date  = i < dates.size()       ? dates.get(i)       : "";
            trackListContainer.getChildren().add(buildSongRow(i + 1, songs.get(i), album, date));
        }

        if (songs.isEmpty()) {
            Label empty = new Label("No songs found.");
            empty.getStyleClass().add("table-text");
            empty.setPadding(new Insets(16));
            trackListContainer.getChildren().add(empty);
        }
    }

    private HBox buildSongRow(int index, Songs song, String albumTitle, String dateAdded) {
        HBox row = new HBox(16);
        row.getStyleClass().add("song-row");
        row.setAlignment(Pos.CENTER_LEFT);
        row.setPrefHeight(60);
        row.setPadding(new Insets(0, 10, 0, 10));

        Label idxLabel = new Label(String.format("%02d", index));
        idxLabel.getStyleClass().add("table-text");
        idxLabel.setMinWidth(40);
        idxLabel.setMaxWidth(40);
        idxLabel.setAlignment(Pos.CENTER);

        StackPane thumb = new StackPane();
        thumb.setPrefSize(40, 40);
        thumb.setMinSize(40, 40);
        thumb.setMaxSize(40, 40);
        thumb.getStyleClass().addAll("song-thumb", THUMB_STYLES[index % THUMB_STYLES.length]);

        VBox titleBox = new VBox(2);
        Label titleLabel = new Label(song.getTitle());
        titleLabel.getStyleClass().add("table-title");
        Label artistLabel = new Label("Artist");
        artistLabel.getStyleClass().add("table-text");
        titleBox.getChildren().addAll(titleLabel, artistLabel);
        HBox.setHgrow(titleBox, Priority.ALWAYS);

        Label albumLabel = new Label(albumTitle);
        albumLabel.getStyleClass().add("table-text");
        albumLabel.setMinWidth(200);
        albumLabel.setMaxWidth(200);

        Label dateLabel = new Label(dateAdded);
        dateLabel.getStyleClass().add("table-text");
        dateLabel.setMinWidth(150);
        dateLabel.setMaxWidth(150);

        int dur = song.getDuration_seconds();
        Label durLabel = new Label(String.format("%d:%02d", dur / 60, dur % 60));
        durLabel.getStyleClass().add("table-text");
        durLabel.setMinWidth(60);
        durLabel.setMaxWidth(60);

        row.getChildren().addAll(idxLabel, thumb, titleBox, albumLabel, dateLabel, durLabel);
        return row;
    }
}
