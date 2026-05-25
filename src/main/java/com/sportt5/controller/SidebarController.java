package com.sportt5.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class SidebarController {
    //Sidebar
    @FXML private Label profileNameLabel, brandLabel;
    @FXML private HBox homeNavItem, genreNavItem, favouritesNavItem, playlistNavItem, libraryNavItem, accountNavItem;
    //App controller
    private AppController appController;

    public void setAppController(AppController appController) { this.appController = appController; }

    public HBox getHomeNavItem() { return homeNavItem; }
    public HBox getGenreNavItem() { return genreNavItem; }
    public HBox getFavouritesNavItem() { return favouritesNavItem; }
    public HBox getPlaylistNavItem() { return playlistNavItem; }
    public HBox getLibraryNavItem() { return libraryNavItem; }
    public HBox getAccountNavItem() { return accountNavItem; }

    public void resetNavStyles() {
        homeNavItem.getStyleClass().setAll("nav-item");
        genreNavItem.getStyleClass().setAll("nav-item");
        favouritesNavItem.getStyleClass().setAll("nav-item");
        playlistNavItem.getStyleClass().setAll("nav-item");
        libraryNavItem.getStyleClass().setAll("nav-item");
        accountNavItem.getStyleClass().setAll("nav-item");
    }

    @FXML
    public void initialize() {
        brandLabel.setOnMouseClicked(e -> { if (appController != null) appController.showHomePage(); });
        profileNameLabel.setOnMouseClicked(e -> { if (appController != null) appController.showAccountPage(); });
        homeNavItem.setOnMouseClicked(e -> { if (appController != null) appController.showHomePage(); });
        genreNavItem.setOnMouseClicked(e -> { if (appController != null) appController.showGenrePage(); });
        favouritesNavItem.setOnMouseClicked(e -> { if (appController != null) appController.showFavouritesPage(); });
        playlistNavItem.setOnMouseClicked(e -> { if (appController != null) appController.showPlaylistPage(); });
        libraryNavItem.setOnMouseClicked(e -> { if (appController != null) appController.showLibraryPage(); });
    }
}
