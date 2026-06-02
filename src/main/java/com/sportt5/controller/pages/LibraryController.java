package com.sportt5.controller.pages;

import com.sportt5.model.Songs;
import com.sportt5.service.LibraryService;
import com.sportt5.session.UserSession;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

import java.util.List;
import java.util.Map;

public class LibraryController {
    // Tabs
    @FXML private Label tabPlaylists, tabGenres, tabArtists, tabAlbums, tabDownloaded;
    // Views
    @FXML private VBox playlistsView, genresView, artistsView, albumsView;
    // Playlists view
    @FXML private StackPane likedBtn;
    @FXML private Label favouritesCount;
    @FXML private HBox playlistCardsBox;
    // Genres view
    @FXML private FlowPane genreChipsBox;
    @FXML private GridPane songListTable;
    @FXML private Label songCountLabel;
    // Artists view
    @FXML private ComboBox<String> artistComboBox;
    @FXML private ImageView artistAvatarImg;
    @FXML private Label artistNameLabel, artistFollowersLabel, artistSongsLabel, artistAlbumsLabel;
    @FXML private GridPane artistSongTable;
    // Albums view
    @FXML private ComboBox<String> albumComboBox;
    @FXML private ImageView albumCoverImg;
    @FXML private Label albumTitleLabel, albumArtistLabel, albumYearLabel, albumSongsLabel, albumDurationLabel;
    @FXML private GridPane albumSongTable;
    // Recently Played
    @FXML private GridPane playHistoryTable;

    private final LibraryService libraryService = new LibraryService();

    @FXML
    public void initialize() {
        if (tabPlaylists != null) {
            tabPlaylists.setOnMouseClicked(e -> showTab(tabPlaylists, playlistsView));
            tabGenres.setOnMouseClicked(e -> showTab(tabGenres, genresView));
            tabArtists.setOnMouseClicked(e -> showTab(tabArtists, artistsView));
            tabAlbums.setOnMouseClicked(e -> showTab(tabAlbums, albumsView));
            tabDownloaded.setOnMouseClicked(e -> showTab(tabDownloaded, null));
        }
    }

    private void showTab(Label activeTab, VBox view) {
        for (Label t : new Label[]{tabPlaylists, tabGenres, tabArtists, tabAlbums, tabDownloaded}) {
            t.getStyleClass().setAll("library-tab");
        }
        activeTab.getStyleClass().setAll("library-tab-active");
        setVisible(playlistsView, false);
        setVisible(genresView, false);
        setVisible(artistsView, false);
        setVisible(albumsView, false);
        if (view != null) setVisible(view, true);
    }

    private void setVisible(Node node, boolean visible) {
        if (node == null) return;
        node.setVisible(visible);
        node.setManaged(visible);
    }

    public void loadPlayHistory() {
        UserSession session = UserSession.getInstance();
        if (session == null || session.getCurrentUserId() == -1) return;
        if (playHistoryTable == null) return;

        playHistoryTable.getChildren().removeIf(node ->
                GridPane.getRowIndex(node) != null && GridPane.getRowIndex(node) > 0);

        new Thread(() -> {
            try {
                List<Songs> songs = libraryService.getPlayHistory();
                Map<Integer, String> users = libraryService.getAllUsers();

                Platform.runLater(() -> {
                    for (int i = 0; i < songs.size(); i++) {
                        addSongToTable(playHistoryTable, i + 1, songs.get(i), users);
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    public void loadFavouritesSongs() {
        UserSession session = UserSession.getInstance();
        if (session == null || session.getCurrentUserId() == -1) return;

        if (favouritesCount != null) favouritesCount.setText("0 songs");

        new Thread(() -> {
            try {
                List<Songs> favSongs = libraryService.getLikedSongs();
                Map<Integer, String> users = libraryService.getAllUsers();

                Platform.runLater(() -> {
                    if (favSongs != null) {
                        if (favouritesCount != null) {
                            favouritesCount.setText(favSongs.size() == 1
                                    ? "1 song"
                                    : favSongs.size() + " songs");
                        }
                        likedBtn.setOnMouseClicked(e -> {
                            for (Label t : new Label[]{tabPlaylists, tabGenres, tabArtists, tabAlbums, tabDownloaded})
                                t.getStyleClass().setAll("library-tab");

                            setVisible(playlistsView, false);
                            setVisible(genresView, true);

                            songListTable.getChildren().clear();

                            for (int i = 0; i < favSongs.size(); i++) {
                                addSongToTable(songListTable, i + 1, favSongs.get(i), users);
                            }
                        });
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void addSongToTable(GridPane table, int index, Songs s, Map<Integer, String> users) {
        Label lblIndex = new Label(String.format("%02d", index));
        lblIndex.getStyleClass().add("table-text");

        VBox titleBox = new VBox(2.0);
        Label lblTitle = new Label(s.getTitle());
        Label lblArtist = new Label(users.getOrDefault(s.getArtistId(), "Unknown"));
        lblTitle.getStyleClass().add("table-title");
        lblArtist.getStyleClass().add("table-artist");
        titleBox.getChildren().addAll(lblTitle, lblArtist);

        int minutes = s.getDurationSeconds() / 60;
        int seconds = s.getDurationSeconds() % 60;
        Label lblDuration = new Label(String.format("%02dp%ds", minutes, seconds));
        lblDuration.getStyleClass().add("table-text");

        Label lblAction = new Label("•••");
        lblAction.getStyleClass().add("row-action");

        table.add(lblIndex, 0, index);
        table.add(titleBox, 1, index);
        table.add(lblDuration, 4, index);
        table.add(lblAction, 5, index);
    }
}
