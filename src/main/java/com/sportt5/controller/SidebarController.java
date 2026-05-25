package com.sportt5.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class SidebarController {
    //Sidebar
    @FXML private Label profileNameLabel, brandLabel;
    @FXML private HBox homeNavItem,libraryItem, albumItem, artistItem, accountNavItem;
    //App controller
    private AppController appController;

    public void setAppController(AppController appController) { this.appController = appController; }

    public HBox getAccountNavItem() { return accountNavItem; }
    public HBox getHomeNavItem() { return homeNavItem; }
    public HBox getLibraryItem() { return libraryItem; }
    public HBox getAlbumItem() { return albumItem; }
    public HBox getArtistItem() { return artistItem; }

    public void resetNavStyles() {
        accountNavItem.getStyleClass().setAll("nav-item");
        homeNavItem.getStyleClass().setAll("nav-item");
        libraryItem.getStyleClass().setAll("nav-item");
        albumItem.getStyleClass().setAll("nav-item");
        artistItem.getStyleClass().setAll("nav-item");
    }

    @FXML
    public void initialize() {
        brandLabel.setOnMouseClicked(e -> { if (appController != null) appController.showHomePage(); });
        profileNameLabel.setOnMouseClicked(e -> { if (appController != null) appController.showAccountPage(); });
        homeNavItem.setOnMouseClicked(e -> { if (appController != null) appController.showHomePage(); });
        libraryItem.setOnMouseClicked(e -> { if (appController != null) appController.showLibraryPage(); });
        albumItem.setOnMouseClicked(e -> { if (appController != null) appController.showAlbumPage(); });
        artistItem.setOnMouseClicked(e -> { if (appController != null) appController.showArtistPage(); });
        accountNavItem.setOnMouseClicked(e -> { if (appController != null) appController.showAccountPage(); });
    }
}
