package com.sportt5.controller.pages;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.sportt5.model.Playlists;
import com.sportt5.model.Songs;
import com.sportt5.service.LibraryService;
import com.sportt5.session.UserSession;
import com.sportt5.util.ApiClient;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static javafx.geometry.Pos.BOTTOM_LEFT;

public class LibraryController {
    // Tabs
    @FXML private Label tabPlaylists, tabGenres, tabArtists, tabAlbums, tabDownloaded;
    // Views
    @FXML private VBox playlistsView, genresView, artistsView, albumsView;
    // Playlists view
    @FXML private StackPane likedBtn;
    @FXML private Label dateAdded;
    @FXML private Label favouritesCount;
    @FXML private HBox playlistCardsBox;
    @FXML private GridPane playlistSongsTable;
    @FXML private Label playlistSongsTitle;
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
    //Map for convenience
    private final LibraryService libraryService = new LibraryService();
    private final Map<Integer, String> usersMap = libraryService.getUsersMap();
    private final Map<Integer, String> albumsMap = libraryService.getAlbumsMap();
    private final Map<Integer, String> genresMap = libraryService.getGenresMap();
    private final ObjectMapper mapper = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

    public LibraryController() throws IOException, InterruptedException {}

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

    public void loadFavouritesSongs() {
        UserSession session = UserSession.getInstance();
        if (session == null || session.getCurrentUserId() == -1) return;

        new Thread(() -> {
            try {
                JsonNode favSongContent = libraryService.getLikedSongs();

                Platform.runLater(() -> {
                    if (favSongContent.isArray()) {
                        //Set count of liked songs
                        if (favSongContent.isEmpty()) favouritesCount.setText("0 songs");
                        else favouritesCount.setText(favSongContent.size() == 1 ? "01 song" : String.format("%02d songs", favSongContent.size()));
                        //Liked btn
                        likedBtn.setOnMouseClicked(e -> {
                            playlistSongsTitle.setText("Liked songs");
                            dateAdded.setText("LIKED AT");

                            playlistSongsTable.getChildren().removeIf(node -> GridPane.getRowIndex(node) != null && GridPane.getRowIndex(node) > 0);

                            int rowIdx = 0;
                            for (JsonNode node : favSongContent) {
                                try {
                                    Songs song = mapper.treeToValue(node, Songs.class);
                                    rowIdx ++;
                                    String artistName = usersMap.getOrDefault(song.getArtistId(), "Unknown");
                                    String[] likedAt = node.get("likedAt").asText().split("T");

                                    addSongToTable(playlistSongsTable, rowIdx, song, artistName, "", likedAt[0]);
                                } catch (JsonProcessingException ex) {
                                    throw new RuntimeException(ex);
                                }
                            }
                        });
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    public void loadPlaylists() {
        UserSession session = UserSession.getInstance();
        if (session == null || session.getCurrentUserId() == -1) return;

        new Thread(() -> {
            try {
                List<Playlists> playlists = libraryService.getUserPlaylists();

                Platform.runLater(() -> {
                    if (playlists != null) {
                        for (Playlists p : playlists) {
                            //Card properties
                            StackPane card = new StackPane();
                            card.getStyleClass().addAll("lib-playlist-card", "playlist-art", "art-green");
                            card.setPrefWidth(130);
                            card.setPrefHeight(130);
                            //Set image
                            Image coverImg = new Image(ApiClient.resolveUrl(p.getCoverUrl()), true);
                            ImageView imgView = new ImageView();
                            imgView.setImage(coverImg);
                            //Title box
                            VBox titleBox = new VBox();
                            titleBox.setAlignment(BOTTOM_LEFT);
                            titleBox.getStyleClass().add("lib-playlist-name-overlay");

                            Label title = new Label(p.getTitle());
                            title.getStyleClass().add("lib-playlist-name");
                            title.setWrapText(true);
                            titleBox.getChildren().add(title);
                            //Put nodes into card
                            card.getChildren().addAll(imgView, titleBox);
                            card.setOnMouseClicked(e -> {
                                try {
                                    playlistSongsTitle.setText("Playlist's Songs");
                                    dateAdded.setText("DATE ADDED");

                                    playlistSongsTable.getChildren().removeIf(node -> GridPane.getRowIndex(node) != null && GridPane.getRowIndex(node) > 0);

                                    List<Songs> songs = libraryService.getPlaylistSongs(p.getId());
                                    int rowIdx = 0;
                                    for (Songs s : songs) {
                                        rowIdx ++;
                                        String artistName = usersMap.getOrDefault(s.getArtistId(), "Unknown");
                                        String albumName = albumsMap.getOrDefault(s.getAlbumId(), "Unknown");

                                        addSongToTable(playlistSongsTable, rowIdx, s, artistName, albumName, "");
                                    }
                                } catch (Exception ex) {
                                    ex.printStackTrace();
                                }
                            });
                            playlistCardsBox.getChildren().add(card);
                        }
                    }
                });

            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    public void filterGenres() {
        UserSession session = UserSession.getInstance();
        if (session == null || session.getCurrentUserId() == -1) return;

        new Thread(() -> {
            try {
                List<Integer> genres = new ArrayList<>(genresMap.keySet());

                Platform.runLater(() -> {
                    if (!genres.isEmpty()) {
                        for (Integer i : genres) {
                            Label genreName = new Label(genresMap.get(i));
                            genreName.getStyleClass().add("genre-chip");
                            genreName.setOnMouseClicked(e -> {
                                try {
                                    List<Songs> songs = libraryService.getSongByGenre(i);

                                    if (songs.isEmpty()) songCountLabel.setText("0 songs");
                                    else songCountLabel.setText(songs.size() == 1 ? "01 song" : String.format("%02d songs", songs.size()));

                                    songListTable.getChildren().removeIf(node -> GridPane.getRowIndex(node) != null && GridPane.getRowIndex(node) > 0);

                                    int rowIdx = 0;
                                    for (Songs s : songs) {
                                        rowIdx ++;
                                        String artistName = usersMap.getOrDefault(s.getArtistId(), "Unknown");
                                        String albumName = albumsMap.getOrDefault(s.getAlbumId(), "Unknown");

                                        addSongToTable(songListTable, rowIdx, s, artistName, albumName, "");
                                    }
                                } catch (Exception ex) {
                                    ex.printStackTrace();
                                }
                            });
                            genreChipsBox.getChildren().add(genreName);
                        }
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void addSongToTable(GridPane table, int index, Songs s, String artistName,String albumName, String dateAdded) {
        //Index
        Label lblIndex = new Label(String.format("%02d", index));
        lblIndex.getStyleClass().add("table-text");
        //Title & Artist
        VBox titleBox = new VBox(2.0);
        Label lblTitle = new Label(s.getTitle());
        Label lblArtist = new Label(artistName);
        lblTitle.getStyleClass().add("table-title");
        lblArtist.getStyleClass().add("table-artist");
        titleBox.getChildren().addAll(lblTitle, lblArtist);
        //Album
        Label lblAlbum = new Label(albumName);
        lblAlbum.getStyleClass().add("table-text");
        //Date added / liked at
        Label lblDate = new Label(dateAdded);
        lblDate.getStyleClass().add("table-text");
        //Duration
        int minutes = s.getDurationSeconds() / 60;
        int seconds = s.getDurationSeconds() % 60;
        Label lblDuration = new Label(String.format("%02dp%ds", minutes, seconds));
        lblDuration.getStyleClass().add("table-text");
        //Action
        Label lblAction = new Label("•••");
        lblAction.getStyleClass().add("row-action");

        table.add(lblIndex, 0, index);
        table.add(titleBox, 1, index);
        table.add(lblAlbum, 2, index);
        table.add(lblDate, 3, index);
        table.add(lblDuration, 4, index);
        table.add(lblAction, 5, index);
    }
}
