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
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.*;

import java.util.List;
import java.util.Map;

public class LibraryController {
    // Tabs
    @FXML private Label tabPlaylists, tabGenres, tabArtists, tabAlbums, tabDownloaded;
    // Views
    @FXML private VBox playlistsView, genresView;
    // Playlists view
    @FXML private StackPane likedBtn;
    @FXML private Label playlistSongCount;
    @FXML private Label playlistDuration;
    @FXML private GridPane songListPlaylistTable;
    @FXML private Label dateAdded;
    @FXML private Label playlistEyebrow;
    // Genres view
    @FXML private FlowPane genreChipsBox;
    @FXML private GridPane songListTable;
    @FXML private Label songCountLabel;

    private final LibraryService libraryService = new LibraryService();
    private final ObjectMapper mapper = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);


    @FXML
    public void initialize() {
        if (tabPlaylists != null) {
            tabPlaylists.setOnMouseClicked(e -> showTab(tabPlaylists, playlistsView));
            tabGenres.setOnMouseClicked(e -> showTab(tabGenres, genresView));
            tabArtists.setOnMouseClicked(e -> showTab(tabArtists, null));
            tabAlbums.setOnMouseClicked(e -> showTab(tabAlbums, null));
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
                Map<Integer, String> users = libraryService.getAllUsers();
                JsonNode favSongContent = libraryService.getLikedSongs();

                Platform.runLater(() -> {
                    if (favSongContent.isArray()) {
                        likedBtn.setOnMouseClicked(e -> {
                            dateAdded.setText("LIKED AT");
                            songListPlaylistTable.getChildren().removeIf(node -> GridPane.getRowIndex(node) != null && GridPane.getRowIndex(node) > 0);

                            int rowIdx = 0;
                            for (JsonNode node : favSongContent) {
                                try {
                                    Songs song = mapper.treeToValue(node, Songs.class);
                                    rowIdx ++;
                                    String artistName = users.getOrDefault(song.getArtistId(), "Unknown");
                                    String[] likedAt = node.get("likedAt").asText().split("T");

                                    addSongToTable(songListPlaylistTable, rowIdx, song, artistName, likedAt[0]);
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
                        playlistEyebrow.setOnMouseClicked(e -> {
                            dateAdded.setText("DATE ADDED");
                            songListPlaylistTable.getChildren().removeIf(node -> GridPane.getRowIndex(node) != null && GridPane.getRowIndex(node) > 0);

                        });
                    }
                });

            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }



    private void addSongToTable(GridPane table, int index, Songs s, String artistName, String dateAdded) {
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
        //Date added /liked at
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
        table.add(lblDate, 3, index);
        table.add(lblDuration, 4, index);
        table.add(lblAction, 5, index);
    }
}
