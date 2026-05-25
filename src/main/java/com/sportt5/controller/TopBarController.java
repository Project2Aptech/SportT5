package com.sportt5.controller;

import com.sportt5.App;
import com.sportt5.session.UserSession;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class TopBarController {
    //Topbar
    @FXML private StackPane homeHero;
    @FXML private HBox accountTopBar, libraryTopBar, albumTopBar, artistTopBar;
    @FXML private HBox adminTopBar;
    //App controller
    private AppController appController;

    public void setAppController(AppController appController) { this.appController = appController; }

    public HBox getAccountTopBar() {return accountTopBar;}
    public StackPane getHomeHero() {return homeHero;}
    public HBox getLibraryTopBar() {return libraryTopBar;}
    public HBox getAlbumTopBar() {return albumTopBar;}
    public HBox getArtistTopBar() {return artistTopBar; }
    public HBox getAdminTopBar() { return adminTopBar; }

    @FXML
    public void initialize() {
    }

}
