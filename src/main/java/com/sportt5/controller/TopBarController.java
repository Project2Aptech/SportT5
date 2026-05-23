package com.sportt5.controller;

import javafx.fxml.FXML;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;

public class TopBarController {
    //Topbar
    @FXML private StackPane homeHero;
    @FXML private HBox genreTopBar, libraryTopBar, playlistTopBar, accountTopBar;
    //App controller
    private AppController appController;

    public void setAppController(AppController appController) { this.appController = appController; }

    public StackPane getHomeHero() {return homeHero;}
    public HBox getGenreTopBar() {return genreTopBar;}
    public HBox getLibraryTopBar() {return libraryTopBar;}
    public HBox getPlaylistTopBar() {return playlistTopBar;}
    public HBox getAccountTopBar() {return accountTopBar;}

    @FXML
    public void initialize() {
    }
}
