package com.sportt5.controller;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class AppController {
    // Home shell
    @FXML private HBox homeView;
    // Home Pages
    @FXML private ScrollPane accountPage;
    @FXML private ScrollPane homePage;
    @FXML private ScrollPane libraryPage;
    @FXML private ScrollPane albumPage;
    // Artist Pages
    @FXML private ScrollPane artistDashBoardPage;
    @FXML private ScrollPane artistMusicPage;
    @FXML private ScrollPane artistUploadPage;
    @FXML private ScrollPane artistAnalyticsPage;
    @FXML private ScrollPane artistFanPage;
    // Admin Pages
    @FXML private ScrollPane adminDashBoardPage;
    @FXML private ScrollPane adminUserPage;
    @FXML private ScrollPane adminReviewPage;
    @FXML private ScrollPane adminAnalyticsPage;
    //Sidebars
    @FXML private VBox sidebar;
    @FXML private VBox artistSidebar;
    @FXML private VBox adminSidebar;
    //Topbars
    @FXML private TopBarController topBarController;
    @FXML private TopBarController adminTopBarController;
    //Controllers
    @FXML private SidebarController sidebarController;
    @FXML private SidebarController artistSidebarController;
    @FXML private SidebarController adminSidebarController;

    @FXML
    public void initialize() {
        if (sidebarController != null) sidebarController.setAppController(this);
        if (artistSidebarController != null) artistSidebarController.setAppController(this);
        if (adminSidebarController != null) adminSidebarController.setAppController(this);

        if (topBarController != null) topBarController.setAppController(this);
        if (adminTopBarController != null) adminTopBarController.setAppController(this);
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
    public void showArtistDashBoardPage() { showArtistPage(artistDashBoardPage, artistSidebarController.getArtistDashboardNavItem()); }
    @FXML
    public void showArtistMusicPage() { showArtistPage(artistMusicPage, artistSidebarController.getArtistMusicNavItem()); }
    @FXML
    public void showArtistUploadPage() { showArtistPage(artistUploadPage, artistSidebarController.getArtistUploadNavItem()); }
    @FXML
    public void showArtistAnalyticsPage() { showArtistPage(artistAnalyticsPage, artistSidebarController.getArtistAnalyticsNavItem()); }
    @FXML
    public void showArtistFanPage() { showArtistPage(artistFanPage, artistSidebarController.getArtistFansNavItem()); }
    @FXML
    public void showAdminDashBoardPage() { showPage(adminDashBoardPage, adminTopBarController.getAdminTopBar(), adminSidebarController.getAdminDashboardNavItem()); }
    @FXML
    public void showAdminUserPage() { showPage(adminUserPage, adminTopBarController.getAdminTopBar(), adminSidebarController.getAdminUserNavItem()); }
    @FXML
    public void showAdminReviewPage() { showPage(adminReviewPage, adminTopBarController.getAdminTopBar(), adminSidebarController.getAdminReviewNavItem()); }
    @FXML
    public void showAdminAnalyticsPage() { showPage(adminAnalyticsPage, adminTopBarController.getAdminTopBar(), adminSidebarController.getAdminAnalyticsNavItem()); }
    // --- Sidebars ---
    public void showHomeSideBar() {
        closeAllSidebar();
        setVisible(sidebar, true);
        showHomePage();
    }

    public void showArtistSideBar() {
        closeAllSidebar();
        setVisible(artistSidebar, true);
        showArtistDashBoardPage();
    }

    public void showAdminSideBar() {
        closeAllSidebar();
        setVisible(adminSidebar, true);
        showAdminDashBoardPage();
    }

    // --- Show pages ---
    public void showPage(ScrollPane page, Node topBar, HBox navItem) {
        closeAllPages();
        sidebarController.resetNavStyles();
        adminSidebarController.resetNavStyles();

        setVisible(page, true);
        setVisible(topBar, true);
        if (navItem != null) navItem.getStyleClass().setAll("nav-active");
    }

    public void showArtistPage(ScrollPane page, HBox navItem) {
        closeAllPages();
        artistSidebarController.resetNavStyles();

        setVisible(page, true);
        if (navItem != null) navItem.getStyleClass().setAll("nav-active");
    }

    private void closeAllPages() {
        //Home
        setVisible(accountPage, false);
        setVisible(homePage, false);
        setVisible(libraryPage, false);
        setVisible(albumPage, false);
        //Artist
        setVisible(artistDashBoardPage, false);
        setVisible(artistMusicPage, false);
        setVisible(artistUploadPage, false);
        setVisible(artistAnalyticsPage, false);
        setVisible(artistFanPage, false);
        //Admin
        setVisible(adminDashBoardPage, false);
        setVisible(adminUserPage, false);
        setVisible(adminReviewPage, false);
        setVisible(adminAnalyticsPage, false);
        //Home topbar
        setVisible(topBarController.getAccountTopBar(), false);
        setVisible(topBarController.getHomeHero(), false);
        setVisible(topBarController.getLibraryTopBar(), false);
        setVisible(topBarController.getAlbumTopBar(), false);
        setVisible(topBarController.getArtistTopBar(), false);
        //Admin topbar
        setVisible(adminTopBarController.getAdminTopBar(), false);
    }

    private void closeAllSidebar() {
        setVisible(sidebar, false);
        setVisible(artistSidebar, false);
        setVisible(adminSidebar, false);
    }

    private void setVisible(Node node, boolean visible) {
        node.setVisible(visible);
        node.setManaged(visible);
    }
}
