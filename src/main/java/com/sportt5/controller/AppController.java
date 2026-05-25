package com.sportt5.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class AppController {
    @FXML
    private StackPane authView;
    @FXML
    private HBox homeView;
    @FXML
    private StackPane homeHero;
    @FXML
    private HBox genreTopBar;
    @FXML
    private HBox libraryTopBar;
    @FXML
    private HBox playlistTopBar;
    @FXML
    private HBox accountTopBar;
    @FXML
    private HBox adminTopBar;
    @FXML
    private javafx.scene.control.ScrollPane homePage;
    @FXML
    private javafx.scene.control.ScrollPane genrePage;
    @FXML
    private javafx.scene.control.ScrollPane favouritesPage;
    @FXML
    private javafx.scene.control.ScrollPane playlistPage;
    @FXML
    private javafx.scene.control.ScrollPane accountPage;
    @FXML
    private javafx.scene.control.ScrollPane adminPage;
    @FXML
    private javafx.scene.control.ScrollPane adminMusicPage;
    @FXML
    private javafx.scene.control.ScrollPane adminUploadPage;
    @FXML
    private javafx.scene.control.ScrollPane adminAnalyticsPage;
    @FXML
    private javafx.scene.control.ScrollPane adminFansPage;
    @FXML
    private javafx.scene.control.ScrollPane adminSettingsPage;
    @FXML
    private javafx.scene.control.ScrollPane artistPage;
    @FXML
    private javafx.scene.control.ScrollPane artistMusicPage;
    @FXML
    private javafx.scene.control.ScrollPane artistUploadPage;
    @FXML
    private javafx.scene.control.ScrollPane artistAnalyticsPage;
    @FXML
    private javafx.scene.control.ScrollPane artistFansPage;
    @FXML
    private VBox userBrowseNav;
    @FXML
    private VBox adminBrowseNav;
    @FXML
    private VBox artistBrowseNav;
    @FXML
    private Label libraryTitleLabel;
    @FXML
    private VBox userLibraryNav;
    @FXML
    private HBox homeNavItem;
    @FXML
    private HBox genreNavItem;
    @FXML
    private HBox favouritesNavItem;
    @FXML
    private HBox playlistNavItem;
    @FXML
    private HBox accountNavItem;
    @FXML
    private HBox adminPanelNavItem;
    @FXML
    private HBox artistPanelNavItem;
    @FXML
    private HBox adminDashboardNavItem;
    @FXML
    private HBox adminMusicNavItem;
    @FXML
    private HBox adminUploadNavItem;
    @FXML
    private HBox adminAnalyticsNavItem;
    @FXML
    private HBox adminFansNavItem;
    @FXML
    private HBox adminSettingsNavItem;
    @FXML
    private HBox exitAdminNavItem;
    @FXML
    private HBox artistDashboardNavItem;
    @FXML
    private HBox artistMusicNavItem;
    @FXML
    private HBox artistUploadNavItem;
    @FXML
    private HBox artistAnalyticsNavItem;
    @FXML
    private HBox artistFansNavItem;
    @FXML
    private HBox exitArtistNavItem;
    @FXML
    private HBox logoutNavItem;
    @FXML
    private VBox loginCard;
    @FXML
    private VBox signupCard;
    @FXML
    private TextField loginEmailField;
    @FXML
    private PasswordField loginPasswordField;
    @FXML
    private Label loginStatusLabel;
    @FXML
    private Label profileNameLabel;
    @FXML
    private Label profileTierLabel;
    @FXML
    private TextField signupNameField;
    @FXML
    private TextField signupEmailField;
    @FXML
    private PasswordField signupPasswordField;
    @FXML
    private Label signupStatusLabel;

    private boolean adminMode;
    private boolean adminAccess;
    private boolean artistMode;
    private boolean artistAccess;
    private String signedInName = "Alex Studio";
    private String signedInTier = "PREMIUM";

    @FXML
    private void showLogin() {
        show(loginCard);
        hide(signupCard);
        loginStatusLabel.setText("");
    }

    @FXML
    private void showSignup() {
        show(signupCard);
        hide(loginCard);
        signupStatusLabel.setText("");
    }

    @FXML
    private void signIn() {
        if (isBlank(loginEmailField.getText()) || isBlank(loginPasswordField.getText())) {
            loginStatusLabel.setText("Enter your email and password.");
            return;
        }

        String email = loginEmailField.getText().trim().toLowerCase();
        String password = loginPasswordField.getText();
        if ("admin@sportt5.local".equals(email) && "admin123".equals(password)) {
            adminAccess = true;
            artistAccess = false;
            adminMode = false;
            artistMode = false;
            signedInName = "Minh Admin";
            signedInTier = "ADMIN";
            openHome();
            return;
        }
        if ("artist@sportt5.local".equals(email) && "artist123".equals(password)) {
            adminAccess = false;
            artistAccess = true;
            adminMode = false;
            artistMode = false;
            signedInName = "Aura Studio";
            signedInTier = "ARTIST";
            openHome();
            return;
        }
        if ("user@sportt5.local".equals(email) && "user123".equals(password)) {
            adminAccess = false;
            artistAccess = false;
            adminMode = false;
            artistMode = false;
            signedInName = "Alex Studio";
            signedInTier = "PREMIUM";
            openHome();
            return;
        }

        loginStatusLabel.setText("Use user, artist, or admin mock account.");
    }

    @FXML
    private void signUp() {
        if (isBlank(signupNameField.getText()) || isBlank(signupEmailField.getText()) || isBlank(signupPasswordField.getText())) {
            signupStatusLabel.setText("Complete all fields to continue.");
            return;
        }

        adminMode = false;
        adminAccess = false;
        artistMode = false;
        artistAccess = false;
        signedInName = "Alex Studio";
        signedInTier = "PREMIUM";
        openHome();
    }

    @FXML
    private void forgotPassword() {
        loginStatusLabel.setText("Password reset is mocked in this frontend build.");
    }

    @FXML
    private void logout() {
        adminMode = false;
        adminAccess = false;
        artistMode = false;
        artistAccess = false;
        signedInName = "Alex Studio";
        signedInTier = "PREMIUM";

        loginEmailField.clear();
        loginPasswordField.clear();
        signupNameField.clear();
        signupEmailField.clear();
        signupPasswordField.clear();
        loginStatusLabel.setText("");
        signupStatusLabel.setText("");

        profileNameLabel.setText(signedInName);
        profileTierLabel.setText(signedInTier);
        configureSidebarForRole();
        hide(homeView);
        show(authView);
        showLogin();
    }

    @FXML
    private void showHomePage() {
        if (adminMode) {
            showAdminPage();
            return;
        }
        if (artistMode) {
            showArtistPage();
            return;
        }

        show(homeHero);
        show(homePage);
        hide(genreTopBar);
        hide(libraryTopBar);
        hide(playlistTopBar);
        hide(accountTopBar);
        hide(adminTopBar);
        hide(genrePage);
        hide(favouritesPage);
        hide(playlistPage);
        hide(accountPage);
        hideAdminPages();
        hideArtistPages();
        setUserActive(homeNavItem);
    }

    @FXML
    private void showGenrePage() {
        hide(homeHero);
        hide(homePage);
        hide(libraryTopBar);
        hide(playlistTopBar);
        hide(accountTopBar);
        hide(adminTopBar);
        hide(favouritesPage);
        hide(playlistPage);
        hide(accountPage);
        hideAdminPages();
        hideArtistPages();
        show(genreTopBar);
        show(genrePage);
        setUserActive(genreNavItem);
    }

    @FXML
    private void showFavouritesPage() {
        hide(homeHero);
        hide(homePage);
        hide(genreTopBar);
        hide(genrePage);
        hide(playlistTopBar);
        hide(playlistPage);
        hide(accountTopBar);
        hide(accountPage);
        hide(adminTopBar);
        hideAdminPages();
        hideArtistPages();
        show(libraryTopBar);
        show(favouritesPage);
        setUserActive(favouritesNavItem);
    }

    @FXML
    private void showPlaylistPage() {
        hide(homeHero);
        hide(homePage);
        hide(genreTopBar);
        hide(genrePage);
        hide(libraryTopBar);
        hide(favouritesPage);
        hide(accountTopBar);
        hide(accountPage);
        hide(adminTopBar);
        hideAdminPages();
        hideArtistPages();
        show(playlistTopBar);
        show(playlistPage);
        setUserActive(playlistNavItem);
    }

    @FXML
    private void showAccountPage() {
        hide(homeHero);
        hide(homePage);
        hide(genreTopBar);
        hide(genrePage);
        hide(libraryTopBar);
        hide(favouritesPage);
        hide(playlistTopBar);
        hide(playlistPage);
        hide(adminTopBar);
        hideAdminPages();
        hideArtistPages();
        show(accountTopBar);
        show(accountPage);
        setUserActive(accountNavItem);
    }

    @FXML
    private void enterAdminPanel() {
        if (!adminAccess) {
            return;
        }

        adminMode = true;
        artistMode = false;
        profileNameLabel.setText("System Admin");
        profileTierLabel.setText("ADMIN");
        configureSidebarForRole();
        showAdminPage();
    }

    @FXML
    private void exitAdminPanel() {
        adminMode = false;
        profileNameLabel.setText(signedInName);
        profileTierLabel.setText(signedInTier);
        configureSidebarForRole();
        showHomePage();
    }

    @FXML
    private void enterArtistPanel() {
        if (!artistAccess) {
            return;
        }

        artistMode = true;
        adminMode = false;
        profileNameLabel.setText("Aura Studio");
        profileTierLabel.setText("ARTIST");
        configureSidebarForRole();
        showArtistPage();
    }

    @FXML
    private void exitArtistPanel() {
        artistMode = false;
        profileNameLabel.setText(signedInName);
        profileTierLabel.setText(signedInTier);
        configureSidebarForRole();
        showHomePage();
    }

    private void showAdminPage() {
        showAdminContent(adminPage, adminDashboardNavItem);
    }

    @FXML
    private void showAdminMusicPage() {
        showAdminContent(adminMusicPage, adminMusicNavItem);
    }

    @FXML
    private void showAdminUploadPage() {
        showAdminContent(adminUploadPage, adminUploadNavItem);
    }

    @FXML
    private void showAdminAnalyticsPage() {
        showAdminContent(adminAnalyticsPage, adminAnalyticsNavItem);
    }

    @FXML
    private void showAdminFansPage() {
        showAdminContent(adminFansPage, adminFansNavItem);
    }

    @FXML
    private void showAdminSettingsPage() {
        showAdminContent(adminSettingsPage, adminSettingsNavItem);
    }

    private void showArtistPage() {
        showArtistContent(artistPage, artistDashboardNavItem);
    }

    @FXML
    private void showArtistMusicPage() {
        showArtistContent(artistMusicPage, artistMusicNavItem);
    }

    @FXML
    private void showArtistUploadPage() {
        showArtistContent(artistUploadPage, artistUploadNavItem);
    }

    @FXML
    private void showArtistAnalyticsPage() {
        showArtistContent(artistAnalyticsPage, artistAnalyticsNavItem);
    }

    @FXML
    private void showArtistFansPage() {
        showArtistContent(artistFansPage, artistFansNavItem);
    }

    private void showAdminContent(javafx.scene.control.ScrollPane page, HBox activeNav) {
        hide(homeHero);
        hide(homePage);
        hide(genreTopBar);
        hide(genrePage);
        hide(libraryTopBar);
        hide(favouritesPage);
        hide(playlistTopBar);
        hide(playlistPage);
        hide(accountTopBar);
        hide(accountPage);
        show(adminTopBar);
        hideAdminPages();
        hideArtistPages();
        show(page);
        setAdminActive(activeNav);
    }

    private void showArtistContent(javafx.scene.control.ScrollPane page, HBox activeNav) {
        hide(homeHero);
        hide(homePage);
        hide(genreTopBar);
        hide(genrePage);
        hide(libraryTopBar);
        hide(favouritesPage);
        hide(playlistTopBar);
        hide(playlistPage);
        hide(accountTopBar);
        hide(accountPage);
        show(adminTopBar);
        hideAdminPages();
        hideArtistPages();
        show(page);
        setArtistActive(activeNav);
    }

    private void show(javafx.scene.Node node) {
        node.setVisible(true);
        node.setManaged(true);
    }

    private void hide(javafx.scene.Node node) {
        node.setVisible(false);
        node.setManaged(false);
    }

    private void openHome() {
        profileNameLabel.setText(signedInName);
        profileTierLabel.setText(signedInTier);
        configureSidebarForRole();
        hide(authView);
        show(homeView);
        showHomePage();
    }

    private void configureSidebarForRole() {
        if (adminMode) {
            hide(userBrowseNav);
            hide(libraryTitleLabel);
            hide(userLibraryNav);
            hide(accountNavItem);
            show(adminBrowseNav);
            hide(artistBrowseNav);
            show(adminSettingsNavItem);
            return;
        }
        if (artistMode) {
            hide(userBrowseNav);
            hide(libraryTitleLabel);
            hide(userLibraryNav);
            hide(accountNavItem);
            hide(adminBrowseNav);
            hide(adminSettingsNavItem);
            show(artistBrowseNav);
            return;
        }

        show(userBrowseNav);
        show(libraryTitleLabel);
        show(userLibraryNav);
        show(accountNavItem);
        if (adminAccess) {
            show(adminPanelNavItem);
        } else {
            hide(adminPanelNavItem);
        }
        if (artistAccess) {
            show(artistPanelNavItem);
        } else {
            hide(artistPanelNavItem);
        }
        hide(adminBrowseNav);
        hide(artistBrowseNav);
        hide(adminSettingsNavItem);
    }

    private void hideAdminPages() {
        hide(adminPage);
        hide(adminMusicPage);
        hide(adminUploadPage);
        hide(adminAnalyticsPage);
        hide(adminFansPage);
        hide(adminSettingsPage);
    }

    private void hideArtistPages() {
        hide(artistPage);
        hide(artistMusicPage);
        hide(artistUploadPage);
        hide(artistAnalyticsPage);
        hide(artistFansPage);
    }

    private void setUserActive(HBox activeItem) {
        setActive(homeNavItem, activeItem == homeNavItem);
        setActive(genreNavItem, activeItem == genreNavItem);
        setActive(favouritesNavItem, activeItem == favouritesNavItem);
        setActive(playlistNavItem, activeItem == playlistNavItem);
        setActive(accountNavItem, activeItem == accountNavItem);
        setActive(adminPanelNavItem, activeItem == adminPanelNavItem);
        setActive(artistPanelNavItem, activeItem == artistPanelNavItem);
    }

    private void setAdminActive(HBox activeItem) {
        setActive(adminDashboardNavItem, activeItem == adminDashboardNavItem);
        setActive(adminMusicNavItem, activeItem == adminMusicNavItem);
        setActive(adminUploadNavItem, activeItem == adminUploadNavItem);
        setActive(adminAnalyticsNavItem, activeItem == adminAnalyticsNavItem);
        setActive(adminSettingsNavItem, activeItem == adminSettingsNavItem);
        setActive(exitAdminNavItem, activeItem == exitAdminNavItem);
    }

    private void setArtistActive(HBox activeItem) {
        setActive(artistDashboardNavItem, activeItem == artistDashboardNavItem);
        setActive(artistMusicNavItem, activeItem == artistMusicNavItem);
        setActive(artistUploadNavItem, activeItem == artistUploadNavItem);
        setActive(artistAnalyticsNavItem, activeItem == artistAnalyticsNavItem);
        setActive(artistFansNavItem, activeItem == artistFansNavItem);
        setActive(exitArtistNavItem, activeItem == exitArtistNavItem);
    }

    private void setActive(HBox item, boolean active) {
        item.getStyleClass().removeAll("nav-active", "nav-item");
        item.getStyleClass().add(active ? "nav-active" : "nav-item");
    }

    private boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }
}
