package com.sportt5.controller;

import com.sportt5.model.Genres;
import com.sportt5.model.Playlists;
import com.sportt5.model.Songs;
import com.sportt5.service.GenreService;
import com.sportt5.service.LikedSongService;
import com.sportt5.service.PlaylistService;
import com.sportt5.service.SongService;
import com.sportt5.session.UserSession;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;

import java.util.List;

public class LibraryController {

    // Tabs
    @FXML private Label tabPlaylists, tabGenres, tabArtists, tabAlbums, tabDownloaded;

    // Views
    @FXML private VBox playlistsView, genresView;

    // Playlists view
    @FXML private Label favouritesCount;
    @FXML private HBox playlistCardsBox;

    // Genres view
    @FXML private FlowPane genreChipsBox;
    @FXML private VBox songListVBox;
    @FXML private Label songCountLabel;

    private static final String[] ART_STYLES  = {"art-green", "art-city", "art-gold"};
    private static final String[] THUMB_STYLES = {"thumb-city", "thumb-orange", "thumb-blue", "thumb-gold"};

    private final GenreService    genreService    = new GenreService();
    private final SongService     songService     = new SongService();
    private final PlaylistService playlistService = new PlaylistService();
    private final LikedSongService likedSongService = new LikedSongService();

    @FXML
    public void initialize() {
        if (tabPlaylists != null) {
            tabPlaylists.setOnMouseClicked(e -> showTab(tabPlaylists, playlistsView));
            tabGenres.setOnMouseClicked(e -> showTab(tabGenres, genresView));
            tabArtists.setOnMouseClicked(e -> showTab(tabArtists, null));
            tabAlbums.setOnMouseClicked(e -> showTab(tabAlbums, null));
            tabDownloaded.setOnMouseClicked(e -> showTab(tabDownloaded, null));
        }

        loadGenres();
        loadLikedCount();
        if (UserSession.getInstance() != null) {
            loadPlaylists();
        }
    }

    // ─── Data loading ───────────────────────────────────────────────────────

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

    private void loadLikedCount() {
        if (UserSession.getInstance() == null) return;
        Task<Integer> task = new Task<>() {
            @Override
            protected Integer call() throws Exception {
                return likedSongService.getCount();
            }
        };
        task.setOnSucceeded(e -> {
            if (favouritesCount != null) {
                int count = task.getValue();
                favouritesCount.setText(count + " song" + (count != 1 ? "s" : ""));
            }
        });
        task.setOnFailed(e -> System.err.println("loadLikedCount failed: " + task.getException()));
        new Thread(task).start();
    }

    private void loadPlaylists() {
        Task<List<Playlists>> task = new Task<>() {
            @Override
            protected List<Playlists> call() throws Exception {
                return playlistService.getMine();
            }
        };
        task.setOnSucceeded(e -> renderPlaylistCards(task.getValue()));
        task.setOnFailed(e -> System.err.println("loadPlaylists failed: " + task.getException()));
        new Thread(task).start();
    }

    private void loadSongsByGenre(int genreId, String genreName) {
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

    // ─── Rendering ──────────────────────────────────────────────────────────

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
                loadSongsByGenre(genre.getId(), genre.getName());
            });
            genreChipsBox.getChildren().add(chip);
        }

        // Auto-select first genre
        if (!genres.isEmpty()) {
            Genres first = genres.get(0);
            Node firstChip = genreChipsBox.getChildren().get(0);
            firstChip.getStyleClass().add("genre-chip-active");
            loadSongsByGenre(first.getId(), first.getName());
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

    private void renderPlaylistCards(List<Playlists> playlists) {
        if (playlistCardsBox == null) return;
        playlistCardsBox.getChildren().clear();
        for (int i = 0; i < Math.min(playlists.size(), 4); i++) {
            playlistCardsBox.getChildren().add(buildPlaylistCard(playlists.get(i), i));
        }
        // "New Playlist" card placeholder
        playlistCardsBox.getChildren().add(buildNewPlaylistCard());
    }

    // ─── Card / row builders ─────────────────────────────────────────────────

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

    private VBox buildPlaylistCard(Playlists playlist, int index) {
        StackPane art = new StackPane(new Label("◎"));
        art.setPrefSize(96, 96);
        art.getStyleClass().addAll("playlist-art", ART_STYLES[index % ART_STYLES.length]);

        Label titleLabel = new Label(playlist.getTitle());
        titleLabel.getStyleClass().add("playlist-title");

        VBox card = new VBox(8, art, titleLabel);
        card.setPrefWidth(112);
        card.getStyleClass().add("playlist-card");
        return card;
    }

    private VBox buildNewPlaylistCard() {
        StackPane art = new StackPane(new Label("+"));
        art.setPrefSize(96, 96);
        art.getStyleClass().addAll("playlist-art", "new-playlist");

        Label titleLabel = new Label("New Playlist");
        titleLabel.getStyleClass().add("playlist-title");
        Label subLabel = new Label("Create");
        subLabel.getStyleClass().add("playlist-count");

        VBox card = new VBox(8, art, titleLabel, subLabel);
        card.setPrefWidth(112);
        card.getStyleClass().add("playlist-card");
        return card;
    }

    // ─── Tab switching ───────────────────────────────────────────────────────

    private void showTab(Label activeTab, VBox view) {
        for (Label t : new Label[]{tabPlaylists, tabGenres, tabArtists, tabAlbums, tabDownloaded}) {
            t.getStyleClass().setAll("library-tab");
        }
        activeTab.getStyleClass().setAll("library-tab-active");
        setVisible(playlistsView, false);
        setVisible(genresView, false);
        if (view != null) setVisible(view, true);
    }

    private void setVisible(Node node, boolean visible) {
        if (node == null) return;
        node.setVisible(visible);
        node.setManaged(visible);
    }
}
