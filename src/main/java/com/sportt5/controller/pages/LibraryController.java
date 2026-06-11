package com.sportt5.controller.pages;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.sportt5.controller.components.PlayerBarController;
import com.sportt5.model.Albums;
import com.sportt5.model.Playlists;
import com.sportt5.model.Songs;
import com.sportt5.model.Users;
import com.sportt5.model.enums.AccountType;
import com.sportt5.model.enums.RequiredAccountType;
import com.sportt5.service.LibraryService;
import com.sportt5.session.UserSession;
import com.sportt5.util.ApiClient;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.util.StringConverter;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

import static javafx.geometry.Pos.BOTTOM_LEFT;

public class LibraryController {
    // Tabs
    @FXML private Label tabPlaylists, tabSongs, tabArtists, tabAlbums;
    // Views
    @FXML private VBox playlistsView, songsView, artistsView, albumsView;
    // Playlists view
    @FXML private Button addPlaylistBtn;
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

    private List<Users> allArtistCache = new ArrayList<>();
    private List<Albums> allAlbumsCache = new ArrayList<>();


    public LibraryController() throws IOException, InterruptedException {}

    @FXML
    public void initialize() {
        if (tabPlaylists != null) {
            tabPlaylists.setOnMouseClicked(e -> showTab(tabPlaylists, playlistsView));
            tabSongs.setOnMouseClicked(e -> showTab(tabSongs, songsView));
            tabArtists.setOnMouseClicked(e -> showTab(tabArtists, artistsView));
            tabAlbums.setOnMouseClicked(e -> showTab(tabAlbums, albumsView));
        }
        if (addPlaylistBtn != null) {
            addPlaylistBtn.setOnAction(e -> showPlaylistAddDialog());
        }
    }

    private void showTab(Label activeTab, VBox view) {
        for (Label t : new Label[]{tabPlaylists, tabSongs, tabArtists, tabAlbums}) {
            t.getStyleClass().setAll("library-tab");
        }
        activeTab.getStyleClass().setAll("library-tab-active");
        setVisible(playlistsView, false);
        setVisible(songsView, false);
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
                    if (favSongContent.isEmpty() || !favSongContent.isArray()) {
                        emptyList(playlistSongsTable);
                    } else if (favSongContent.isArray()) {
                        //Set count of liked songs
                        if (favSongContent.isEmpty()) favouritesCount.setText("0 songs");
                        else favouritesCount.setText(favSongContent.size() == 1 ? "01 song" : String.format("%02d songs", favSongContent.size()));
                        //Liked btn
                        showFavouriteSongs(favSongContent);
                        likedBtn.setOnMouseClicked(e -> showFavouriteSongs(favSongContent));
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
                    if (!playlists.isEmpty()) {
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
                            imgView.setFitWidth(130);
                            imgView.setFitHeight(130);
                            imgView.setPreserveRatio(false);
                            //Title box
                            VBox titleBox = new VBox();
                            titleBox.setAlignment(BOTTOM_LEFT);
                            titleBox.getStyleClass().add("lib-playlist-name-overlay");

                            Label title = new Label(p.getTitle());
                            title.getStyleClass().add("lib-playlist-name");
                            title.setWrapText(true);
                            titleBox.getChildren().add(title);
                            //Actions overlay (edit / delete) — shown on hover
                            HBox actionsOverlay = new HBox(4);
                            actionsOverlay.setAlignment(javafx.geometry.Pos.TOP_RIGHT);
                            StackPane.setAlignment(actionsOverlay, javafx.geometry.Pos.TOP_RIGHT);
                            actionsOverlay.setStyle("-fx-padding: 6 6 0 0;");
                            actionsOverlay.setVisible(false);
                            actionsOverlay.setPickOnBounds(false);

                            Label editBtn = new Label("✏");
                            editBtn.getStyleClass().add("playlist-action-btn");
                            editBtn.setOnMouseClicked(e -> {
                                e.consume();
                                showPlaylistEditDialog();
                            });

                            Label deleteBtn = new Label("🗑");
                            deleteBtn.getStyleClass().addAll("playlist-action-btn", "playlist-delete-btn");
                            deleteBtn.setOnMouseClicked(e -> {
                                e.consume();
                                showPlaylistDeleteConfirm(p.getTitle());
                            });

                            actionsOverlay.getChildren().addAll(editBtn, deleteBtn);

                            //Put nodes into card
                            card.getChildren().addAll(imgView, titleBox, actionsOverlay);
                            card.setOnMouseEntered(e -> actionsOverlay.setVisible(true));
                            card.setOnMouseExited(e -> actionsOverlay.setVisible(false));
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

                                        addSongToTable(playlistSongsTable, rowIdx, s, artistName, albumName, "", p.getId());
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
    //════════════════════Songs view════════════════════
    public void filterSongs() {
        if (session == null || session.getCurrentUserId() == -1) return;

        new Thread(() -> {
            try {
                List<Integer> genres = new ArrayList<>(genresMap.keySet());

                Platform.runLater(() -> {
                    Label allChip = new Label("All");
                    allChip.getStyleClass().addAll("genre-chip", "genre-chip-active");
                    allChip.setOnMouseClicked(e -> {
                        selectedGenreIds.clear();
                        genreChipsBox.getChildren().forEach(n -> {
                            if (n instanceof Label lbl) lbl.getStyleClass().remove("genre-chip-active");
                        });
                        allChip.getStyleClass().add("genre-chip-active");
                        new Thread(() -> {
                            try {
                                final List<Songs> result = libraryService.getAllSongs();
                                Platform.runLater(() -> {
                                    songCountLabel.setText(result.isEmpty() ? "0 songs" : result.size() == 1 ? "01 song" : String.format("%02d songs", result.size()));
                                    if (result.isEmpty()) { emptyList(songListTable); return; }
                                    notEmptyList(songListTable);
                                    songListTable.getChildren().removeIf(nd -> GridPane.getRowIndex(nd) != null && GridPane.getRowIndex(nd) > 0);
                                    int rowIdx = 0;
                                    for (Songs s : result) {
                                        rowIdx++;
                                        addSongToTable(songListTable, rowIdx, s, usersMap.getOrDefault(s.getArtistId(), "Unknown"), albumsMap.getOrDefault(s.getAlbumId(), "Unknown"), "");
                                    }
                                });
                            } catch (Exception ex) { ex.printStackTrace(); }
                        }).start();
                    });
                    genreChipsBox.getChildren().add(allChip);

                    // Tự động load tất cả bài hát khi mở tab genres
                    new Thread(() -> {
                        try {
                            final List<Songs> result = libraryService.getAllSongs();
                            Platform.runLater(() -> {
                                songCountLabel.setText(result.isEmpty() ? "0 songs" : result.size() == 1 ? "01 song" : String.format("%02d songs", result.size()));
                                if (result.isEmpty()) { emptyList(songListTable); return; }
                                notEmptyList(songListTable);
                                songListTable.getChildren().removeIf(nd -> GridPane.getRowIndex(nd) != null && GridPane.getRowIndex(nd) > 0);
                                int rowIdx = 0;
                                for (Songs s : result) {
                                    rowIdx++;
                                    addSongToTable(songListTable, rowIdx, s, usersMap.getOrDefault(s.getArtistId(), "Unknown"), albumsMap.getOrDefault(s.getAlbumId(), "Unknown"), "");
                                }
                            });
                        } catch (Exception ex) { ex.printStackTrace(); }
                    }).start();

                    for (Integer i : genres) {
                        Label genreName = new Label(genresMap.get(i));
                        genreName.getStyleClass().add("genre-chip");
                        final int currId = i;
                        genreName.setOnMouseClicked(e -> {
                            try {
                                allChip.getStyleClass().remove("genre-chip-active");
                                if(selectedGenreIds.contains(currId)) {
                                    selectedGenreIds.remove(currId);
                                    genreName.getStyleClass().remove("genre-chip-active");
                                } else {
                                    selectedGenreIds.add(currId);
                                    genreName.getStyleClass().add("genre-chip-active");
                                }

                                List<Songs> songs = libraryService.getSongByGenre(selectedGenreIds, false);

                                if (songs.isEmpty()) {
                                    songCountLabel.setText("0 songs");
                                    emptyList(songListTable);
                                }
                                else {
                                    songCountLabel.setText(songs.size() == 1 ? "01 song" : String.format("%02d songs", songs.size()));
                                    notEmptyList(songListTable);
                                }

                                songListTable.getChildren().removeIf(node -> GridPane.getRowIndex(node) != null && GridPane.getRowIndex(node) > 0);
                                int rowIdx = 0;
                                for (Songs s : songs) {
                                    rowIdx++;
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
                                        addSongToTable(artistSongTable, rowIdx, s, "", albumName, "");
                                    }

                                } catch (Exception ex) {
                                    ex.printStackTrace();
                                }
                            }
                        });
                    } else {
                        emptyList(artistSongTable);
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
                        allAlbumsCache = new ArrayList<>(albums);
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
                                        addSongToTable(albumSongTable, rowIdx, s, "", selectedDetails.getArtistName(), "");
                                    }

                                } catch (Exception ex) {
                                    ex.printStackTrace();
                                }
                            }
                        });
                    } else {
                        emptyList(albumSongTable);
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    public void searchByKeyword(String keyword, String type) {
        String kw = keyword.toLowerCase();

        //Songs
        new Thread (() -> {
            try {
                List<Songs> rs = libraryService.searchSongs(keyword);
                Platform.runLater(() -> {
                    notEmptyList(songListTable);
                    songCountLabel.setText(rs.size() == 1 ? "01 song" : String.format(("%02d songs"), rs.size()));
                    songListTable.getChildren().removeIf(n -> GridPane.getRowIndex(n) != null && GridPane.getRowIndex(n) > 0);
                    int row = 0;
                    for (Songs s : rs) {
                        row++;
                        addSongToTable(songListTable, row, s,
                                usersMap.getOrDefault(s.getArtistId(), "Unknown"),
                                albumsMap.getOrDefault(s.getAlbumId(), "Unknown"), "");
                    }
                    if (!"artist".equals(type) && !"album".equals(type)) showTab(tabSongs, songsView);
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();

        //Artists
        Platform.runLater(() -> {
            if (allArtistCache.isEmpty()) allArtistCache = new ArrayList<>(artistComboBox.getItems());
            artistComboBox.getItems().setAll(allArtistCache);
            allArtistCache.stream()
                    .filter(u -> u.getDisplayName() != null && u.getDisplayName().toLowerCase().contains(kw))
                    .findFirst()
                    .ifPresent(u -> artistComboBox.getSelectionModel().select(u));
            if ("artist".equals(type)) showTab(tabArtists, artistsView);
        });

        //Albums
        Platform.runLater(() -> {
            if (allAlbumsCache.isEmpty()) allAlbumsCache = new ArrayList<>(albumComboBox.getItems());
            albumComboBox.getItems().setAll(allAlbumsCache);
            allAlbumsCache.stream()
                    .filter(a -> a.getTitle() != null && a.getTitle().toLowerCase().contains(kw))
                    .findFirst()
                    .ifPresent(a -> albumComboBox.getSelectionModel().select(a));
            if ("album".equals(type)) showTab(tabAlbums, albumsView);
        });
    }

    //════════════════════Private methods════════════════════
    private void addSongToTable(GridPane table, int index, Songs s, String artistName, String albumName, String dateAdded) {
        addSongToTable(table, index, s, artistName, albumName, dateAdded, 0);
    }

    private void addSongToTable(GridPane table, int index, Songs s, String artistName, String albumName, String dateAdded, int playlistId) {
        //Index
        Label lblIndex = new Label(String.format("%02d", index));
        lblIndex.getStyleClass().add("table-text");
        //Title & Artist
        if (artistName.isEmpty()) {
            Label lblTitle = new Label(s.getTitle());
            lblTitle.getStyleClass().add("table-title");
            lblTitle.setOnMouseClicked(e -> { if (PlayerBarController.getInstance() != null) PlayerBarController.getInstance().playSong(s); });
            table.add(lblTitle, 1, index);
        } else {
            VBox titleBox = new VBox(2.0);
            Label lblTitle = new Label(s.getTitle());
            Label lblArtist = new Label(artistName);
            lblTitle.getStyleClass().add("table-title");
            lblArtist.getStyleClass().add("table-artist");
            titleBox.getChildren().addAll(lblTitle, lblArtist);
            titleBox.setOnMouseClicked(e -> { if (PlayerBarController.getInstance() != null) PlayerBarController.getInstance().playSong(s); });
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
        //Actions cell
        HBox actionsBox = new HBox(4);
        actionsBox.setAlignment(javafx.geometry.Pos.CENTER_LEFT);

        Label lblLike = new Label("♡");
        lblLike.getStyleClass().addAll("row-action", "like-btn");

        Label lblAddToPlaylist = new Label("+");
        lblAddToPlaylist.getStyleClass().addAll("row-action", "add-to-playlist-btn");
        lblAddToPlaylist.setOnMouseClicked(e -> showAddToPlaylistDialog());

        Label lblDownload = new Label("↓");
        lblDownload.getStyleClass().addAll("row-action", "download-btn");
        lblDownload.setOnMouseClicked(e -> new Thread(() -> {
            try {
                HttpResponse<String> resp = ApiClient.get("songs/" + s.getId());
                if (resp.statusCode() != 200) return;
                Songs full = mapper.readValue(resp.body(), Songs.class);

                AccountType userType = session.getCurrentUser() != null && session.getCurrentUser().getAccountType() != null
                        ? session.getCurrentUser().getAccountType() : AccountType.NORMAL;
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
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }).start());

        actionsBox.getChildren().addAll(lblLike, lblAddToPlaylist, lblDownload);

        if (playlistId > 0) {
            Label lblRemove = new Label("✕");
            lblRemove.getStyleClass().addAll("row-action", "playlist-remove-song-btn");
            actionsBox.getChildren().add(lblRemove);
        }

        table.add(lblIndex, 0, index);
        table.add(lblAlbum, 2, index);
        table.add(lblDate, 3, index);
        table.add(lblDuration, 4, index);
        table.add(actionsBox, 5, index);
    }

    private void showFavouriteSongs(JsonNode content) {
        playlistSongsTitle.setText("Liked songs");
        dateAdded.setText("LIKED AT");

        playlistSongsTable.getChildren().removeIf(node -> GridPane.getRowIndex(node) != null && GridPane.getRowIndex(node) > 0);

        int rowIdx = 0;
        for (JsonNode node : content) {
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
    }

    private void emptyList(GridPane table) {
        table.getChildren().clear();
        table.getColumnConstraints().clear();
        ColumnConstraints c = new ColumnConstraints();
        c.setPercentWidth(100);
        table.getColumnConstraints().add(c);
        Label noSong = new Label("NO SONG");
        noSong.getStyleClass().add("table-title");
        table.add(noSong, 0, 0);
    }

    private void notEmptyList(GridPane table) {
        table.getChildren().clear();
        table.getColumnConstraints().clear();
        ColumnConstraints c0 = new ColumnConstraints();
        c0.setPercentWidth(8);
        ColumnConstraints c1 = new ColumnConstraints();
        c1.setPercentWidth(26);
        ColumnConstraints c2 = new ColumnConstraints();
        c2.setPercentWidth(26);
        ColumnConstraints c3 = new ColumnConstraints();
        c3.setPercentWidth(22);
        ColumnConstraints c4 = new ColumnConstraints();
        c4.setPercentWidth(8);
        ColumnConstraints c5 = new ColumnConstraints();
        c5.setPercentWidth(10);
        table.getColumnConstraints().addAll(c0, c1, c2, c3, c4, c5);
        Label lblIdx = new Label("#");
        lblIdx.getStyleClass().add("table-head");
        Label lblTitle = new Label("TITLE");
        lblTitle.getStyleClass().add("table-head");
        Label lblAlbum = new Label("ALBUM");
        lblAlbum.getStyleClass().add("table-head");
        Label lblDate = new Label("DATE ADDED");
        lblDate.getStyleClass().add("table-head");
        Label lblDuration = new Label("DURATION");
        lblDuration.getStyleClass().add("table-head");
        Label lblActions = new Label("ACTIONS");
        lblActions.getStyleClass().add("table-head");
        table.add(lblIdx, 0, 0);
        table.add(lblTitle, 1, 0);
        table.add(lblAlbum, 2, 0);
        table.add(lblDate, 3, 0);
        table.add(lblDuration, 4, 0);
        table.add(lblActions, 5, 0);
    }

    //════════════════════Playlist dialogs════════════════════
    private void showPlaylistAddDialog() {
        openDialog("/com.sportt5/view/users/playlist-add-dialog.fxml", "#cancelBtn", "#createBtn");
    }

    private void showAddToPlaylistDialog() {
        openDialog("/com.sportt5/view/users/playlist-select-dialog.fxml", "#cancelBtn", "#addBtn");
    }

    private void showPlaylistEditDialog() {
        openDialog("/com.sportt5/view/users/playlist-edit-dialog.fxml", "#cancelBtn", "#saveBtn");
    }

    private void openDialog(String fxmlPath, String... closeButtonIds) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            DialogPane pane = loader.load();
            pane.getButtonTypes().add(ButtonType.CLOSE);
            Node closeNode = pane.lookupButton(ButtonType.CLOSE);
            closeNode.setVisible(false);
            closeNode.setManaged(false);

            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setDialogPane(pane);
            dialog.initModality(Modality.APPLICATION_MODAL);
            dialog.setOnShown(e -> pane.getScene().getStylesheets()
                .add(getClass().getResource("/com.sportt5/css/style.css").toExternalForm()));

            for (String id : closeButtonIds) {
                Button btn = (Button) pane.lookup(id);
                if (btn != null) btn.setOnAction(e -> dialog.close());
            }

            dialog.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showPlaylistDeleteConfirm(String playlistTitle) {
        try {
            FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/com.sportt5/view/users/playlist-delete-confirm.fxml"));
            DialogPane pane = loader.load();
            pane.getButtonTypes().add(ButtonType.CLOSE);
            Node closeNode = pane.lookupButton(ButtonType.CLOSE);
            closeNode.setVisible(false);
            closeNode.setManaged(false);

            Label msg = (Label) pane.lookup("#confirmMessage");
            if (msg != null) msg.setText("Bạn có chắc muốn xóa playlist \"" + playlistTitle + "\"? Hành động này không thể hoàn tác.");

            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setDialogPane(pane);
            dialog.initModality(Modality.APPLICATION_MODAL);
            dialog.setOnShown(e -> pane.getScene().getStylesheets()
                .add(getClass().getResource("/com.sportt5/css/style.css").toExternalForm()));

            Button cancelBtn = (Button) pane.lookup("#cancelBtn");
            Button deleteBtn = (Button) pane.lookup("#deleteBtn");
            if (cancelBtn != null) cancelBtn.setOnAction(e -> dialog.close());
            if (deleteBtn != null) deleteBtn.setOnAction(e -> dialog.close());

            dialog.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
