package com.sportt5.controller;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;

public class AppController {

    // ── Main shell ───────────────────────────────────────────────────────────
    @FXML private HBox homeView;

    // ── Sidebars (root nodes from fx:include) ────────────────────────────────
    @FXML private Node sidebar;
    @FXML private Node artistSidebar;
    @FXML private Node adminSidebar;

    // ── Pages – home ─────────────────────────────────────────────────────────
    @FXML private ScrollPane homePage;
    @FXML private ScrollPane libraryPage;
    @FXML private ScrollPane albumPage;
    @FXML private ScrollPane accountPage;

    // ── Pages – artist ───────────────────────────────────────────────────────
    @FXML private ScrollPane artistDashBoardPage;
    @FXML private ScrollPane artistMusicPage;
    @FXML private ScrollPane artistUploadPage;
    @FXML private ScrollPane artistAnalyticsPage;
    @FXML private ScrollPane artistFanPage;

    // ── Pages – admin ────────────────────────────────────────────────────────
    @FXML private ScrollPane adminDashBoardPage;
    @FXML private ScrollPane adminUserPage;
    @FXML private ScrollPane adminReviewPage;
    @FXML private ScrollPane adminAnalyticsPage;

    // ── Sub-controllers ──────────────────────────────────────────────────────
    @FXML private SidebarController sidebarController;
    @FXML private SidebarController artistSidebarController;
    @FXML private SidebarController adminSidebarController;
    @FXML private TopBarController  topBarController;

    @FXML
    public void initialize() {
        sidebarController.setAppController(this);
        if (artistSidebarController != null) artistSidebarController.setAppController(this);
        if (adminSidebarController  != null) adminSidebarController.setAppController(this);
        topBarController.setAppController(this);
        showHomePage();
    }

    // ════════════════════════════════════════════════════════════════════════
    // Home navigation
    // ════════════════════════════════════════════════════════════════════════

    @FXML public void showHomePage()    { showPage(homePage,    topBarController.getHomeHero(),     sidebarController.getHomeNavItem()); }
    @FXML public void showLibraryPage() { showPage(libraryPage, topBarController.getLibraryTopBar(),sidebarController.getLibraryItem()); }
    @FXML public void showAlbumPage()   { showPage(albumPage,   topBarController.getAlbumTopBar(),  sidebarController.getAlbumItem()); }
    @FXML public void showAccountPage() { showPage(accountPage, topBarController.getAccountTopBar(),sidebarController.getAccountNavItem()); }

    // ════════════════════════════════════════════════════════════════════════
    // Sidebar switching
    // ════════════════════════════════════════════════════════════════════════

    public void showArtistSideBar() {
        setVisible(sidebar, false);
        setVisible(adminSidebar, false);
        setVisible(artistSidebar, true);
        showArtistDashBoardPage();
    }

    public void showAdminSideBar() {
        setVisible(sidebar, false);
        setVisible(artistSidebar, false);
        setVisible(adminSidebar, true);
        showAdminDashBoardPage();
    }

    public void showHomeSideBar() {
        setVisible(artistSidebar, false);
        setVisible(adminSidebar, false);
        setVisible(sidebar, true);
        showHomePage();
    }

    // ════════════════════════════════════════════════════════════════════════
    // Artist pages
    // ════════════════════════════════════════════════════════════════════════

    public void showArtistDashBoardPage() {
        showPage(artistDashBoardPage, topBarController.getArtistTopBar(),
                artistSidebarController != null ? artistSidebarController.getArtistDashboardNavItem() : null);
    }

    public void showArtistMusicPage() {
        showPage(artistMusicPage, topBarController.getArtistTopBar(),
                artistSidebarController != null ? artistSidebarController.getArtistMusicNavItem() : null);
    }

    public void showArtistUploadPage() {
        showPage(artistUploadPage, topBarController.getArtistTopBar(),
                artistSidebarController != null ? artistSidebarController.getArtistUploadNavItem() : null);
    }

    public void showArtistAnalyticsPage() {
        showPage(artistAnalyticsPage, topBarController.getArtistTopBar(),
                artistSidebarController != null ? artistSidebarController.getArtistAnalyticsNavItem() : null);
    }

    public void showArtistFanPage() {
        showPage(artistFanPage, topBarController.getArtistTopBar(),
                artistSidebarController != null ? artistSidebarController.getArtistFansNavItem() : null);
    }

    // ════════════════════════════════════════════════════════════════════════
    // Admin pages
    // ════════════════════════════════════════════════════════════════════════

    public void showAdminDashBoardPage() {
        showPage(adminDashBoardPage, topBarController.getAdminTopBar(),
                adminSidebarController != null ? adminSidebarController.getAdminDashboardNavItem() : null);
    }

    public void showAdminUserPage() {
        showPage(adminUserPage, topBarController.getAdminTopBar(),
                adminSidebarController != null ? adminSidebarController.getAdminUserNavItem() : null);
    }

    public void showAdminReviewPage() {
        showPage(adminReviewPage, topBarController.getAdminTopBar(),
                adminSidebarController != null ? adminSidebarController.getAdminReviewNavItem() : null);
    }

    public void showAdminAnalyticsPage() {
        showPage(adminAnalyticsPage, topBarController.getAdminTopBar(),
                adminSidebarController != null ? adminSidebarController.getAdminAnalyticsNavItem() : null);
    }

    // ════════════════════════════════════════════════════════════════════════
    // Core show/hide helpers
    // ════════════════════════════════════════════════════════════════════════

    public void showPage(ScrollPane page, Node topBar, HBox navItem) {
        // Hide all pages
        for (ScrollPane p : new ScrollPane[]{
                homePage, libraryPage, albumPage, accountPage,
                artistDashBoardPage, artistMusicPage, artistUploadPage,
                artistAnalyticsPage, artistFanPage,
                adminDashBoardPage, adminUserPage, adminReviewPage, adminAnalyticsPage
        }) setVisible(p, false);

        // Hide all top bars
        setVisible(topBarController.getHomeHero(),      false);
        setVisible(topBarController.getAccountTopBar(), false);
        setVisible(topBarController.getLibraryTopBar(), false);
        setVisible(topBarController.getAlbumTopBar(),   false);
        setVisible(topBarController.getArtistTopBar(),  false);
        setVisible(topBarController.getAdminTopBar(),   false);

        sidebarController.resetNavStyles();
        if (artistSidebarController != null) artistSidebarController.resetNavStyles();
        if (adminSidebarController  != null) adminSidebarController.resetNavStyles();

        setVisible(page, true);
        setVisible(topBar, true);
        if (navItem != null) navItem.getStyleClass().setAll("nav-active");
    }

    private void setVisible(Node node, boolean visible) {
        if (node == null) return;
        node.setVisible(visible);
        node.setManaged(visible);
    }

    // kept for backward-compat if referenced elsewhere
    public static class AuthController {}
}
