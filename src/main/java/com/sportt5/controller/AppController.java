package com.sportt5.controller;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class AppController {

    // Auth
    @FXML private StackPane authView;
    @FXML private VBox loginCard;
    @FXML private TextField loginEmailField;
    @FXML private PasswordField loginPasswordField;
    @FXML private Label loginStatusLabel;
    @FXML private VBox signupCard;
    @FXML private TextField signupNameField;
    @FXML private TextField signupEmailField;
    @FXML private PasswordField signupPasswordField;
    @FXML private Label signupStatusLabel;

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

    // --- Auth ---

    @FXML
    private void showLogin() {
        loginCard.setVisible(true);
        loginCard.setManaged(true);
        signupCard.setVisible(false);
        signupCard.setManaged(false);
    }

    @FXML
    private void showSignup() {
        signupCard.setVisible(true);
        signupCard.setManaged(true);
        loginCard.setVisible(false);
        loginCard.setManaged(false);
    }

    @FXML
    private void forgotPassword() {}

    @FXML
    private void signIn() {
        // TODO: validate credentials
        setVisible(authView, false);
        setVisible(homeView, true);
    }

    @FXML
    private void signUp() {
        // TODO: register user
    }

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
