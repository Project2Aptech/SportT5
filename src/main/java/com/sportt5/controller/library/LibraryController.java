package com.sportt5.controller.library;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class LibraryController {
    @FXML private VBox songListContainer;

    private Stage stage;

    public void setStage(Stage s) { this.stage = s; }

    @FXML
    public void initialize() {

    }
}
