package com.sportt5.controller;

import javafx.fxml.FXML;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;

public class TopBarController {
    //Topbar
    @FXML private StackPane homeHero;
    @FXML private HBox accountTopBar, libraryTopBar, albumTopBar, artistTopBar;
    //App controller
    private AppController appController;

    public void setAppController(AppController appController) { this.appController = appController; }

    public HBox getAccountTopBar() {return accountTopBar;}
    public StackPane getHomeHero() {return homeHero;}
    public HBox getLibraryTopBar() {return libraryTopBar;}
    public HBox getAlbumTopBar() {return albumTopBar;}
    public HBox getArtistTopBar() {return artistTopBar; }


    @FXML
    public void initialize() {
    }
}
