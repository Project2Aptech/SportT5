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
    private VBox userBrowseNav;
    @FXML
    private VBox adminBrowseNav;
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
            adminMode = false;
            openHome();
            return;
        }
        if ("user@sportt5.local".equals(email) && "user123".equals(password)) {
            adminAccess = false;
            adminMode = false;
            openHome();
            return;
        }

        loginStatusLabel.setText("Mock accounts: user@sportt5.local or admin@sportt5.local.");
    }

    @FXML
    private void signUp() {
        if (isBlank(signupNameField.getText()) || isBlank(signupEmailField.getText()) || isBlank(signupPasswordField.getText())) {
            signupStatusLabel.setText("Complete all fields to continue.");
            return;
        }

        adminMode = false;
        adminAccess = false;
        openHome();
    }

    @FXML
    private void forgotPassword() {
        loginStatusLabel.setText("Password reset is mocked in this frontend build.");
    }

    @FXML
    private void showHomePage() {
        if (adminMode) {
            showAdminPage();
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
        profileNameLabel.setText("Aura Studio");
        profileTierLabel.setText("PRO ARTIST");
        configureSidebarForRole();
        showAdminPage();
    }

    @FXML
    private void exitAdminPanel() {
        adminMode = false;
        profileNameLabel.setText("Alex Studio");
        profileTierLabel.setText("PREMIUM");
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
        show(page);
        setAdminActive(activeNav);
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
        profileNameLabel.setText(adminMode ? "Aura Studio" : "Alex Studio");
        profileTierLabel.setText(adminMode ? "PRO ARTIST" : "PREMIUM");
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
            show(adminSettingsNavItem);
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
        hide(adminBrowseNav);
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

    private void setUserActive(HBox activeItem) {
        setActive(homeNavItem, activeItem == homeNavItem);
        setActive(genreNavItem, activeItem == genreNavItem);
        setActive(favouritesNavItem, activeItem == favouritesNavItem);
        setActive(playlistNavItem, activeItem == playlistNavItem);
        setActive(accountNavItem, activeItem == accountNavItem);
        setActive(adminPanelNavItem, activeItem == adminPanelNavItem);
    }

    private void setAdminActive(HBox activeItem) {
        setActive(adminDashboardNavItem, activeItem == adminDashboardNavItem);
        setActive(adminMusicNavItem, activeItem == adminMusicNavItem);
        setActive(adminUploadNavItem, activeItem == adminUploadNavItem);
        setActive(adminAnalyticsNavItem, activeItem == adminAnalyticsNavItem);
        setActive(adminFansNavItem, activeItem == adminFansNavItem);
        setActive(adminSettingsNavItem, activeItem == adminSettingsNavItem);
        setActive(exitAdminNavItem, activeItem == exitAdminNavItem);
    }

    private void setActive(HBox item, boolean active) {
        item.getStyleClass().removeAll("nav-active", "nav-item");
        item.getStyleClass().add(active ? "nav-active" : "nav-item");
    }

    private boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }
}
