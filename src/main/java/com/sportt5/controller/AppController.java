package com.sportt5.controller;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;

public class AppController {

    // Home shell
    @FXML private HBox homeView;

    // Sidebar
    @FXML private Label profileNameLabel;
    @FXML private Label profileTierLabel;
    @FXML private HBox homeNavItem;
    @FXML private HBox genreNavItem;
    @FXML private HBox favouritesNavItem;
    @FXML private HBox playlistNavItem;
    @FXML private HBox accountNavItem;

    // Top bars
    @FXML private StackPane homeHero;
    @FXML private HBox genreTopBar;
    @FXML private HBox libraryTopBar;
    @FXML private HBox playlistTopBar;
    @FXML private HBox accountTopBar;
    @FXML private HBox adminTopBar;

    // Pages
    @FXML private ScrollPane homePage;
    @FXML private ScrollPane genrePage;
    @FXML private ScrollPane favouritesPage;
    @FXML private ScrollPane playlistPage;
    @FXML private ScrollPane accountPage;
    @FXML private ScrollPane adminPage;

    @FXML
    public void initialize() {}

    // --- Navigation ---

    @FXML
    private void showHomePage() {
        showPage(homePage, homeHero, homeNavItem);
    }

    @FXML
    private void showGenrePage() {
        showPage(genrePage, genreTopBar, genreNavItem);
    }

    @FXML
    private void showFavouritesPage() {
        showPage(favouritesPage, libraryTopBar, favouritesNavItem);
    }

    @FXML
    private void showPlaylistPage() {
        showPage(playlistPage, playlistTopBar, playlistNavItem);
    }

    @FXML
    private void showAccountPage() {
        showPage(accountPage, accountTopBar, accountNavItem);
    }

    private void showPage(ScrollPane page, Node topBar, HBox navItem) {
        setVisible(homePage, false);
        setVisible(genrePage, false);
        setVisible(favouritesPage, false);
        setVisible(playlistPage, false);
        setVisible(accountPage, false);
        setVisible(adminPage, false);

        setVisible(homeHero, false);
        setVisible(genreTopBar, false);
        setVisible(libraryTopBar, false);
        setVisible(playlistTopBar, false);
        setVisible(accountTopBar, false);
        setVisible(adminTopBar, false);

        homeNavItem.getStyleClass().setAll("nav-item");
        genreNavItem.getStyleClass().setAll("nav-item");
        favouritesNavItem.getStyleClass().setAll("nav-item");
        playlistNavItem.getStyleClass().setAll("nav-item");
        accountNavItem.getStyleClass().setAll("nav-item");

        setVisible(page, true);
        setVisible(topBar, true);
        navItem.getStyleClass().setAll("nav-active");
    }

    private void setVisible(Node node, boolean visible) {
        node.setVisible(visible);
        node.setManaged(visible);
    }
}
