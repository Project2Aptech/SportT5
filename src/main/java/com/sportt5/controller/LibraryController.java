package com.sportt5.controller;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;

public class LibraryController {

    // Tabs
    @FXML private Label tabPlaylists, tabGenres, tabArtists, tabAlbums, tabDownloaded;

    // Views
    @FXML private VBox playlistsView, genresView;

    // Playlists view fields
    @FXML private Label favouritesCount;

    // Genres view fields
    @FXML private FlowPane genreChipsBox;
    @FXML private VBox songListVBox;
    @FXML private Label songCountLabel;

    @FXML
    public void initialize() {
        tabPlaylists.setOnMouseClicked(e -> showTab(tabPlaylists, playlistsView));
        tabGenres.setOnMouseClicked(e -> showTab(tabGenres, genresView));
        tabArtists.setOnMouseClicked(e -> showTab(tabArtists, null));
        tabAlbums.setOnMouseClicked(e -> showTab(tabAlbums, null));
        tabDownloaded.setOnMouseClicked(e -> showTab(tabDownloaded, null));
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
        node.setVisible(visible);
        node.setManaged(visible);
    }
}
