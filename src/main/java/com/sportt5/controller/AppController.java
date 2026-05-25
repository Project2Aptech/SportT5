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
    @FXML private ScrollPane accountPage;
    @FXML private ScrollPane homePage;
    @FXML private ScrollPane libraryPage;
    @FXML private ScrollPane albumPage;
    @FXML private ScrollPane artistPage;


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
    public void showAccountPage() { showPage(accountPage, topBarController.getAccountTopBar(), sidebarController.getAccountNavItem()); }
    @FXML
    public void showHomePage() { showPage(homePage, topBarController.getHomeHero(), sidebarController.getHomeNavItem()); }
    @FXML
    public void showLibraryPage() { showPage(libraryPage, topBarController.getLibraryTopBar(), sidebarController.getLibraryItem()); }
    @FXML
    public void showAlbumPage() { showPage(albumPage, topBarController.getAlbumTopBar(), sidebarController.getAlbumItem()); }
    @FXML
    public void showArtistPage() { showPage(artistPage, topBarController.getArtistTopBar(), sidebarController.getArtistItem()); }

    public void showPage(ScrollPane page, Node topBar, HBox navItem) {
        setVisible(accountPage, false);
        setVisible(homePage, false);
        setVisible(libraryPage, false);
        setVisible(albumPage, false);
        setVisible(artistPage, false);

        setVisible(topBarController.getAccountTopBar(), false);
        setVisible(topBarController.getHomeHero(), false);
        setVisible(topBarController.getLibraryTopBar(), false);
        setVisible(topBarController.getAlbumTopBar(), false);
        setVisible(topBarController.getArtistTopBar(), false);

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
