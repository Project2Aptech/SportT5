package com.sportt5.controller;

import com.sportt5.dao.playlist.playlist_dao;
import com.sportt5.model.Playlists;
import com.sportt5.model.Songs;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PlaylistController {

    @FXML private HBox playlistCardsBox;
    @FXML private Label playlistEyebrow;
    @FXML private Label playlistHeading;
    @FXML private Label playlistSongCount;
    @FXML private Label playlistDuration;
    @FXML private VBox songListVBox;

    private playlist_dao dao;

    private static final String[] ART_STYLES = {"art-green", "art-city", "art-gold"};
    private static final String[] THUMB_STYLES = {"thumb-city", "thumb-orange", "thumb-blue", "thumb-gold"};

    @FXML
    public void initialize() {
        try {
            dao = new playlist_dao();
            dao.loadAllPlaylists();
            renderPlaylistCards();
            if (!dao.getPlaylists().isEmpty()) {
                selectPlaylist(dao.getPlaylists().get(0));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void renderPlaylistCards() {
        playlistCardsBox.getChildren().clear();
        List<Playlists> playlists = dao.getPlaylists();

        for (int i = 0; i < playlists.size(); i++) {
            playlistCardsBox.getChildren().add(buildPlaylistCard(playlists.get(i), ART_STYLES[i % ART_STYLES.length]));
        }

        if (playlists.isEmpty()) {
            Label empty = new Label("No public playlists available.");
            empty.getStyleClass().add("table-text");
            playlistCardsBox.getChildren().add(empty);
        }
    }

    private VBox buildPlaylistCard(Playlists pl, String artStyle) {
        VBox card = new VBox(8);
        card.getStyleClass().add("playlist-card");
        card.setPrefWidth(130);
        card.setPadding(new Insets(10));

        StackPane art = new StackPane();
        art.setPrefSize(110, 110);
        art.setMinSize(110, 110);
        art.setMaxSize(110, 110);
        art.getStyleClass().addAll("playlist-art", artStyle);
        Label artIcon = new Label("♬");
        art.getChildren().add(artIcon);

        Label title = new Label(pl.getTitle());
        title.getStyleClass().add("playlist-title");
        title.setWrapText(true);
        title.setMaxWidth(110);

        card.getChildren().addAll(art, title);
        card.setOnMouseClicked(e -> selectPlaylist(pl));
        return card;
    }

    private void selectPlaylist(Playlists pl) {
        playlistHeading.setText(pl.getTitle());
        playlistEyebrow.setText(pl.isIs_public() ? "PUBLIC PLAYLIST" : "PRIVATE PLAYLIST");

        try {
            dao.loadSongsByPlaylist(pl.getId());
            List<Songs> songs = dao.getSongs();
            ArrayList<String> albumTitles = dao.getAlbumTitles();

            playlistSongCount.setText(songs.size() + " songs");

            int totalSec = songs.stream().mapToInt(Songs::getDuration_seconds).sum();
            if (totalSec > 0) {
                playlistDuration.setText(String.format("%dh %02dmin", totalSec / 3600, (totalSec % 3600) / 60));
            } else {
                playlistDuration.setText("");
            }

            renderSongs(songs, albumTitles);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void renderSongs(List<Songs> songs, List<String> albumTitles) {
        songListVBox.getChildren().clear();

        for (int i = 0; i < songs.size(); i++) {
            String albumTitle = i < albumTitles.size() ? albumTitles.get(i) : "";
            songListVBox.getChildren().add(buildSongRow(i + 1, songs.get(i), albumTitle, i == 0));
        }

        if (songs.isEmpty()) {
            Label empty = new Label("This playlist has no songs yet.");
            empty.getStyleClass().add("table-text");
            empty.setPadding(new Insets(16));
            songListVBox.getChildren().add(empty);
        }
    }

    private HBox buildSongRow(int rank, Songs song, String albumTitle, boolean active) {
        HBox row = new HBox(18);
        row.getStyleClass().add("song-row");
        row.setPadding(new Insets(10, 12, 10, 12));

        Label rankLabel = new Label(String.valueOf(rank));
        rankLabel.getStyleClass().add(active ? "playlist-rank-active" : "table-text");
        rankLabel.setMinWidth(40);
        rankLabel.setMaxWidth(40);

        StackPane thumb = new StackPane();
        thumb.setPrefSize(32, 32);
        thumb.setMinSize(32, 32);
        thumb.setMaxSize(32, 32);
        thumb.getStyleClass().addAll("song-thumb", THUMB_STYLES[rank % THUMB_STYLES.length]);

        VBox titleBox = new VBox(1);
        Label titleLabel = new Label(song.getTitle());
        titleLabel.getStyleClass().add(active ? "playlist-song-active" : "table-title");
        titleBox.getChildren().add(titleLabel);
        HBox.setHgrow(titleBox, Priority.ALWAYS);

        Label albumLabel = new Label(albumTitle);
        albumLabel.getStyleClass().add("table-text");
        albumLabel.setMinWidth(180);
        albumLabel.setMaxWidth(180);

        int dur = song.getDuration_seconds();
        Label durationLabel = new Label(String.format("%d:%02d", dur / 60, dur % 60));
        durationLabel.getStyleClass().add("table-text");
        durationLabel.setMinWidth(60);
        durationLabel.setMaxWidth(60);

        row.getChildren().addAll(rankLabel, thumb, titleBox, albumLabel, durationLabel);
        return row;
    }
}
