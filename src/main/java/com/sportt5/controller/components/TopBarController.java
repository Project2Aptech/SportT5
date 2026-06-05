package com.sportt5.controller.components;

import com.sportt5.controller.AppController;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;

public class TopBarController {

    @FXML private StackPane homeHero;
    @FXML private TextField homeSearchField;
    @FXML private TextField librarySearchField;
    @FXML private HBox libraryTopBar;
    @FXML private HBox accountTopBar;
    @FXML private HBox artistTopBar;
    @FXML private HBox adminTopBar;

    private AppController appController;

    public void setAppController(AppController appController) {
        this.appController = appController;
        if (homeSearchField != null) {
            homeSearchField.setOnAction(e -> appController.searchLibrary(homeSearchField.getText()));
        }
        if (librarySearchField != null) {
            librarySearchField.textProperty().addListener((obs, oldValue, newValue) ->
                    appController.searchLibrary(newValue));
        }
    }

    @FXML
    public void initialize() {}

    public StackPane getHomeHero()  { return homeHero; }
    public HBox getLibraryTopBar()  { return libraryTopBar; }
    public HBox getAccountTopBar()  { return accountTopBar; }
    public HBox getArtistTopBar()   { return artistTopBar; }
    public HBox getAdminTopBar()    { return adminTopBar; }
}
