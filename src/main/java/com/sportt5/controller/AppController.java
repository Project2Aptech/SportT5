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
    // Pages
    @FXML private ScrollPane homePage;
    @FXML private ScrollPane genrePage;
    @FXML private ScrollPane favouritesPage;
    @FXML private ScrollPane playlistPage;
    @FXML private ScrollPane accountPage;

    //Controllers
    @FXML private SidebarController sidebarController;
    @FXML private TopBarController topBarController;

    @FXML
    public void initialize() {
        sidebarController.setAppController(this);
        topBarController.setAppController(this);
        showHomePage();
    }

    // --- Navigation ---

    @FXML
    public void showHomePage() {
        showPage(homePage, topBarController.getHomeHero(), sidebarController.getHomeNavItem());
    }

    @FXML
    public void showGenrePage() {
        showPage(genrePage, topBarController.getGenreTopBar(), sidebarController.getGenreNavItem());
    }

    @FXML
    public void showFavouritesPage() {
        showPage(favouritesPage, topBarController.getLibraryTopBar(), sidebarController.getFavouritesNavItem());
    }

    @FXML
    public void showPlaylistPage() {
        showPage(playlistPage, topBarController.getPlaylistTopBar(), sidebarController.getPlaylistNavItem());
    }

    @FXML
    public void showAccountPage() {
        showPage(accountPage, topBarController.getAccountTopBar(), sidebarController.getAccountNavItem());
    }

    public void showPage(ScrollPane page, Node topBar, HBox navItem) {
        setVisible(homePage, false);
        setVisible(genrePage, false);
        setVisible(favouritesPage, false);
        setVisible(playlistPage, false);
        setVisible(accountPage, false);

        setVisible(topBarController.getHomeHero(), false);
        setVisible(topBarController.getGenreTopBar(), false);
        setVisible(topBarController.getLibraryTopBar(), false);
        setVisible(topBarController.getPlaylistTopBar(), false);
        setVisible(topBarController.getAccountTopBar(), false);

        sidebarController.resetNavStyles();

        setVisible(page, true);
        setVisible(topBar, true);
        if (navItem != null) navItem.getStyleClass().setAll("nav-active");
    }

    private void setVisible(Node node, boolean visible) {
        node.setVisible(visible);
        node.setManaged(visible);
    }
}
