package com.sportt5.controller.pages;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.sportt5.model.Albums;
import com.sportt5.model.Playlists;
import com.sportt5.model.Songs;
import com.sportt5.model.Users;
import com.sportt5.service.LibraryService;
import com.sportt5.session.UserSession;
import com.sportt5.util.ApiClient;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.util.StringConverter;

import java.io.IOException;
import java.util.*;

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
    @FXML private Button createPlaylistButton;
    @FXML private HBox playlistCardsBox;
    @FXML private GridPane playlistSongsTable;
    @FXML private Label playlistSongsTitle;
    // Genres view
    @FXML private FlowPane genreChipsBox;
    @FXML private GridPane songListTable;
    @FXML private Label songCountLabel;
    // Artists view
    @FXML private ComboBox<Users> artistComboBox;
    @FXML private ImageView artistAvatarImg;
    @FXML private Label artistNameLabel, artistFollowersLabel, artistSongsLabel, artistAlbumsLabel;
    @FXML private GridPane artistSongTable;
    // Albums view
    @FXML private ComboBox<Albums> albumComboBox;
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
    //User session
    private final UserSession session = UserSession.getInstance();
    //Others
    private final Set<Integer> selectedGenreIds = new HashSet<>();
    private Integer activePlaylistId;

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
        if (createPlaylistButton != null) {
            createPlaylistButton.setOnAction(e -> promptCreatePlaylist());
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

    public void clearAllBox() {
        playlistCardsBox.getChildren().clear();
        playlistSongsTable.getChildren().removeIf(n -> GridPane.getRowIndex(n) != null && GridPane.getRowIndex(n) > 0);
        genreChipsBox.getChildren().clear();
        songListTable.getChildren().removeIf(n -> GridPane.getRowIndex(n) != null && GridPane.getRowIndex(n) > 0);
        artistComboBox.getItems().clear();
        artistSongTable.getChildren().removeIf(n -> GridPane.getRowIndex(n) != null && GridPane.getRowIndex(n) > 0);
        albumComboBox.getItems().clear();
        albumSongTable.getChildren().removeIf(n -> GridPane.getRowIndex(n) != null && GridPane.getRowIndex(n) > 0);
    }
    //════════════════════Playlists View════════════════════
    public void loadFavouritesSongs() {
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
                            activePlaylistId = null;
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

                                    addSongToTable(playlistSongsTable, rowIdx, song, artistName, "", likedAt[0], true);
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
        if (session == null || session.getCurrentUserId() == -1) return;

        new Thread(() -> {
            try {
                List<Playlists> playlists = libraryService.getUserPlaylists();

                Platform.runLater(() -> {
                    if (playlists != null) {
                        for (Playlists p : playlists) {
                            StackPane card = createPlaylistCard(p);
                            card.setOnMouseClicked(e -> {
                                try {
                                    activePlaylistId = p.getId();
                                    playlistSongsTitle.setText("Playlist's Songs");
                                    dateAdded.setText("DATE ADDED");

                                    loadPlaylistSongsIntoTable(p.getId());
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
    //════════════════════Genres view════════════════════
    public void filterGenres() {
        if (session == null || session.getCurrentUserId() == -1) return;

        new Thread(() -> {
            try {
                List<Integer> genres = new ArrayList<>(genresMap.keySet());

                Platform.runLater(() -> {
                    if (!genres.isEmpty()) {
                        for (Integer i : genres) {
                            Label genreName = new Label(genresMap.get(i));
                            genreName.getStyleClass().add("genre-chip");
                            final int currId = i;
                            genreName.setOnMouseClicked(e -> {
                                try {
                                    if(selectedGenreIds.contains(currId)) {
                                        selectedGenreIds.remove(currId);
                                        genreName.getStyleClass().remove("genre-chip-active");
                                    } else {
                                        selectedGenreIds.add(currId);
                                        genreName.getStyleClass().add("genre-chip-active");
                                    }

                                    List<Songs> songs = libraryService.getSongByGenre(selectedGenreIds, true);

                                    if (songs.isEmpty()) songCountLabel.setText("0 songs");
                                    else songCountLabel.setText(songs.size() == 1 ? "01 song" : String.format("%02d songs", songs.size()));

                                    songListTable.getChildren().removeIf(node -> GridPane.getRowIndex(node) != null && GridPane.getRowIndex(node) > 0);

                                    int rowIdx = 0;
                                    for (Songs s : songs) {
                                        rowIdx ++;
                                        String artistName = usersMap.getOrDefault(s.getArtistId(), "Unknown");
                                        String albumName = albumsMap.getOrDefault(s.getAlbumId(), "Unknown");

                                        addSongToTable(songListTable, rowIdx, s, artistName, albumName, "", false);
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
    //════════════════════Artists view════════════════════
    public void filterArtist() {
        if (session == null || session.getCurrentUserId() == -1) return;

        new Thread(() -> {
            try {
                List<Users> artists = libraryService.getAllArtists();

                Platform.runLater(() -> {
                    if (!artists.isEmpty()) {
                        artistComboBox.getItems().addAll(artists);
                        //Set items name
                        artistComboBox.setConverter(new StringConverter<Users>() {
                            @Override
                            public String toString(Users object) {
                                return (object == null) ? "Unknown" : object.getDisplayName();
                            }

                            @Override
                            public Users fromString(String string) {
                                return null;
                            }
                        });
                        //Set action
                        artistComboBox.setOnAction(e -> {
                            Users selected = artistComboBox.getSelectionModel().getSelectedItem();
                            if (selected != null) {
                                try {
                                    int followersCount = libraryService.getArtistFollowersCount(selected.getId());
                                    int albumsCount = libraryService.getArtistAlbumsCount(selected.getId());
                                    List<Songs> songs = libraryService.getSongByArtist(selected.getId());
                                    //Avatar img
                                    artistAvatarImg.setImage(new Image(ApiClient.resolveUrl(selected.getAvatarUrl()), true));
                                    //Name
                                    artistNameLabel.setText(selected.getDisplayName());
                                    //Followers count
                                    artistFollowersLabel.setText(String.format("Followers: %d", followersCount));
                                    //Songs
                                    artistSongsLabel.setText(String.format("Songs: %02d", songs.size()));
                                    //Albums
                                    artistAlbumsLabel.setText(String.format("Albums: %02d", albumsCount));
                                    //Table
                                    artistSongTable.getChildren().removeIf(node -> GridPane.getRowIndex(node) != null && GridPane.getRowIndex(node) > 0);
                                    int rowIdx = 0;
                                    for (Songs s : songs) {
                                        rowIdx++;
                                        String albumName = albumsMap.getOrDefault(s.getAlbumId(), "Unknown");
                                        addSongToTable(artistSongTable, rowIdx, s, "", albumName, "", false);
                                    }

                                } catch (Exception ex) {
                                    ex.printStackTrace();
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
    //════════════════════Albums view════════════════════
    public void filterAlbums() {
        if (session == null || session.getCurrentUserId() == -1) return;

        new Thread(() -> {
            try {
                List<Albums> albums = libraryService.getAllAlbums();

                Platform.runLater(() -> {
                    if (!albums.isEmpty()) {
                        albumComboBox.getItems().addAll(albums);
                        //Set items name
                        albumComboBox.setConverter(new StringConverter<Albums>() {
                            @Override
                            public String toString(Albums object) {
                                return (object == null) ? "Unknown" : object.getTitle();
                            }

                            @Override
                            public Albums fromString(String string) {
                                return null;
                            }
                        });
                        //Set action
                        albumComboBox.setOnAction(e -> {
                            Albums selected = albumComboBox.getSelectionModel().getSelectedItem();
                            if (selected != null) {
                                try {
                                    Albums selectedDetails = libraryService.getAlbumDetails(selected.getId());
                                    List<Songs> songs = libraryService.getSongByAlbum(selected.getId());
                                    //Cover img
                                    albumCoverImg.setImage(new Image(ApiClient.resolveUrl(selectedDetails.getCoverUrl()), true));
                                    //Title
                                    albumTitleLabel.setText(selectedDetails.getTitle());
                                    //Artist
                                    albumArtistLabel.setText("Artist: " + selectedDetails.getArtistName());
                                    //Year
                                    albumYearLabel.setText(String.format("Year: %d", selectedDetails.getReleaseDate().getYear()));
                                    //Songs
                                    albumSongsLabel.setText(String.format("Songs: %02d", songs.size()));
                                    //Duration
                                    int duration = songs.stream().mapToInt(Songs::getDurationSeconds).sum();
                                    int hours = duration / 3600;
                                    int minutes = (duration - hours * 3600) / 60;
                                    int seconds = duration - hours * 3600 - minutes * 60;
                                    if (hours == 0) albumDurationLabel.setText(String.format("Duration: %02dp%02ds", minutes, seconds));
                                    else albumDurationLabel.setText(String.format("Duration: %dh%02dp%02ds", hours, minutes, seconds));
                                    //Table
                                    albumSongTable.getChildren().removeIf(node -> GridPane.getRowIndex(node) != null && GridPane.getRowIndex(node) > 0);
                                    int rowIdx = 0;
                                    for (Songs s : songs) {
                                        rowIdx++;
                                        addSongToTable(albumSongTable, rowIdx, s, "", selectedDetails.getArtistName(), "", false);
                                    }

                                } catch (Exception ex) {
                                    ex.printStackTrace();
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
    //════════════════════Private methods════════════════════
    public void searchSongs(String query) {
        if (query == null || query.isBlank()) {
            return;
        }

        showTab(tabGenres, genresView);
        songCountLabel.setText("Searching...");
        songListTable.getChildren().removeIf(node -> GridPane.getRowIndex(node) != null && GridPane.getRowIndex(node) > 0);

        new Thread(() -> {
            try {
                List<Songs> songs = libraryService.searchSongs(query.trim());
                Platform.runLater(() -> {
                    songCountLabel.setText(songs.isEmpty() ? "0 songs" : String.format("%02d songs", songs.size()));
                    int rowIdx = 0;
                    for (Songs s : songs) {
                        rowIdx++;
                        String artistName = usersMap.getOrDefault(s.getArtistId(), "Unknown");
                        String albumName = albumsMap.getOrDefault(s.getAlbumId(), "Unknown");
                        addSongToTable(songListTable, rowIdx, s, artistName, albumName, "", false);
                    }
                });
            } catch (Exception e) {
                Platform.runLater(() -> showError("Search failed", e.getMessage()));
            }
        }).start();
    }

    private void addSongToTable(GridPane table, int index, Songs s, String artistName, String albumName, String dateAdded, boolean likedSongsView) {
        //Index
        Label lblIndex = new Label(String.format("%02d", index));
        lblIndex.getStyleClass().add("table-text");
        //Title & Artist
        if (artistName.isEmpty()) {
            Label lblTitle = new Label(s.getTitle());
            lblTitle.getStyleClass().add("table-title");
            table.add(lblTitle, 1, index);
        } else {
            VBox titleBox = new VBox(2.0);
            Label lblTitle = new Label(s.getTitle());
            Label lblArtist = new Label(artistName);
            lblTitle.getStyleClass().add("table-title");
            lblArtist.getStyleClass().add("table-artist");
            titleBox.getChildren().addAll(lblTitle, lblArtist);
            table.add(titleBox, 1, index);
        }
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
        HBox actions = createSongActions(s, likedSongsView);

        table.add(lblIndex, 0, index);
        table.add(lblAlbum, 2, index);
        table.add(lblDate, 3, index);
        table.add(lblDuration, 4, index);
        table.add(actions, 5, index);
    }

    private StackPane createPlaylistCard(Playlists p) {
        StackPane card = new StackPane();
        card.getStyleClass().addAll("lib-playlist-card", "playlist-art", "art-green");
        card.setPrefWidth(130);
        card.setPrefHeight(130);

        ImageView imgView = new ImageView(new Image(ApiClient.resolveUrl(p.getCoverUrl()), true));
        imgView.setFitWidth(130);
        imgView.setFitHeight(130);
        imgView.setPreserveRatio(false);

        VBox titleBox = new VBox();
        titleBox.setAlignment(BOTTOM_LEFT);
        titleBox.getStyleClass().add("lib-playlist-name-overlay");

        Label title = new Label(p.getTitle());
        title.getStyleClass().add("lib-playlist-name");
        title.setWrapText(true);
        titleBox.getChildren().add(title);

        Button deleteButton = new Button("×");
        deleteButton.getStyleClass().add("filter-button");
        StackPane.setAlignment(deleteButton, Pos.TOP_RIGHT);
        deleteButton.setOnAction(e -> {
            e.consume();
            deletePlaylist(p);
        });

        card.getChildren().addAll(imgView, titleBox, deleteButton);
        return card;
    }

    private HBox createSongActions(Songs song, boolean likedSongsView) {
        Button likeButton = new Button(likedSongsView ? "♥" : "♡");
        Button addButton = new Button("+");
        Button downloadButton = new Button("⇩");
        likeButton.getStyleClass().add("filter-button");
        addButton.getStyleClass().add("filter-button");
        downloadButton.getStyleClass().add("filter-button");

        likeButton.setOnAction(e -> {
            if (likedSongsView) unlikeSong(song);
            else likeSong(song);
        });
        addButton.setOnAction(e -> choosePlaylistForSong(song));
        downloadButton.setOnAction(e -> downloadSong(song));

        HBox actions = new HBox(6, likeButton, addButton, downloadButton);
        actions.setAlignment(Pos.CENTER_RIGHT);

        if (activePlaylistId != null && !likedSongsView) {
            Button removeButton = new Button("×");
            removeButton.getStyleClass().add("filter-button");
            removeButton.setOnAction(e -> removeSongFromActivePlaylist(song));
            actions.getChildren().add(removeButton);
        }
        return actions;
    }

    private void promptCreatePlaylist() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Create Playlist");
        dialog.setHeaderText("Create a new playlist");
        dialog.setContentText("Playlist name:");
        dialog.showAndWait()
                .map(String::trim)
                .filter(name -> !name.isEmpty())
                .ifPresent(name -> new Thread(() -> {
                    try {
                        libraryService.createPlaylist(name);
                        Platform.runLater(() -> refreshPlaylists("Playlist created"));
                    } catch (Exception e) {
                        Platform.runLater(() -> showError("Create playlist failed", e.getMessage()));
                    }
                }).start());
    }

    private void deletePlaylist(Playlists playlist) {
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION,
                "Delete playlist \"" + playlist.getTitle() + "\"?", ButtonType.CANCEL, ButtonType.OK);
        confirm.setHeaderText("Delete playlist");
        confirm.showAndWait()
                .filter(button -> button == ButtonType.OK)
                .ifPresent(button -> new Thread(() -> {
                    try {
                        libraryService.deletePlaylist(playlist.getId());
                        Platform.runLater(() -> {
                            activePlaylistId = null;
                            playlistSongsTable.getChildren().removeIf(node -> GridPane.getRowIndex(node) != null && GridPane.getRowIndex(node) > 0);
                            refreshPlaylists("Playlist deleted");
                        });
                    } catch (Exception e) {
                        Platform.runLater(() -> showError("Delete playlist failed", e.getMessage()));
                    }
                }).start());
    }

    private void choosePlaylistForSong(Songs song) {
        new Thread(() -> {
            try {
                List<Playlists> playlists = libraryService.getUserPlaylists();
                Platform.runLater(() -> {
                    if (playlists.isEmpty()) {
                        showInfo("No playlists", "Create a playlist first.");
                        return;
                    }
                    Map<String, Playlists> playlistsByTitle = new LinkedHashMap<>();
                    for (Playlists playlist : playlists) {
                        playlistsByTitle.put(playlist.getTitle(), playlist);
                    }
                    ChoiceDialog<String> dialog = new ChoiceDialog<>(playlists.get(0).getTitle(), playlistsByTitle.keySet());
                    dialog.setTitle("Add to playlist");
                    dialog.setHeaderText("Choose playlist for \"" + song.getTitle() + "\"");
                    dialog.setContentText("Playlist:");
                    dialog.showAndWait()
                            .map(playlistsByTitle::get)
                            .ifPresent(playlist -> addSongToPlaylist(playlist, song));
                });
            } catch (Exception e) {
                Platform.runLater(() -> showError("Load playlists failed", e.getMessage()));
            }
        }).start();
    }

    private void addSongToPlaylist(Playlists playlist, Songs song) {
        new Thread(() -> {
            try {
                libraryService.addSongToPlaylist(playlist.getId(), song.getId());
                Platform.runLater(() -> showInfo("Added", "Song added to \"" + playlist.getTitle() + "\"."));
            } catch (Exception e) {
                Platform.runLater(() -> showError("Add song failed", e.getMessage()));
            }
        }).start();
    }

    private void removeSongFromActivePlaylist(Songs song) {
        if (activePlaylistId == null) return;
        int playlistId = activePlaylistId;
        new Thread(() -> {
            try {
                libraryService.removeSongFromPlaylist(playlistId, song.getId());
                Platform.runLater(() -> {
                    loadPlaylistSongsIntoTable(playlistId);
                    showInfo("Removed", "Song removed from playlist.");
                });
            } catch (Exception e) {
                Platform.runLater(() -> showError("Remove song failed", e.getMessage()));
            }
        }).start();
    }

    private void likeSong(Songs song) {
        new Thread(() -> {
            try {
                libraryService.likeSong(song.getId());
                Platform.runLater(() -> {
                    loadFavouritesSongs();
                    showInfo("Liked", "Song added to Liked Songs.");
                });
            } catch (Exception e) {
                Platform.runLater(() -> showError("Like song failed", e.getMessage()));
            }
        }).start();
    }

    private void unlikeSong(Songs song) {
        new Thread(() -> {
            try {
                libraryService.unlikeSong(song.getId());
                Platform.runLater(() -> {
                    playlistSongsTable.getChildren().removeIf(node -> GridPane.getRowIndex(node) != null && GridPane.getRowIndex(node) > 0);
                    loadFavouritesSongs();
                    showInfo("Removed", "Song removed from Liked Songs.");
                });
            } catch (Exception e) {
                Platform.runLater(() -> showError("Unlike song failed", e.getMessage()));
            }
        }).start();
    }

    private void downloadSong(Songs song) {
        new Thread(() -> {
            try {
                var path = libraryService.downloadSong(song);
                Platform.runLater(() -> showInfo("Downloaded", "Saved to " + path));
            } catch (Exception e) {
                Platform.runLater(() -> showError("Download failed", e.getMessage()));
            }
        }).start();
    }

    private void loadPlaylistSongsIntoTable(int playlistId) {
        try {
            playlistSongsTable.getChildren().removeIf(node -> GridPane.getRowIndex(node) != null && GridPane.getRowIndex(node) > 0);
            List<Songs> songs = libraryService.getPlaylistSongs(playlistId);
            int rowIdx = 0;
            for (Songs s : songs) {
                rowIdx++;
                String artistName = usersMap.getOrDefault(s.getArtistId(), "Unknown");
                String albumName = albumsMap.getOrDefault(s.getAlbumId(), "Unknown");
                addSongToTable(playlistSongsTable, rowIdx, s, artistName, albumName, "", false);
            }
        } catch (Exception e) {
            showError("Load playlist songs failed", e.getMessage());
        }
    }

    private void refreshPlaylists(String message) {
        playlistCardsBox.getChildren().clear();
        loadPlaylists();
        if (message != null) showInfo("Done", message);
    }

    private void showInfo(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, message, ButtonType.OK);
        alert.setHeaderText(title);
        alert.show();
    }

    private void showError(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR, message, ButtonType.OK);
        alert.setHeaderText(title);
        alert.show();
    }
}
