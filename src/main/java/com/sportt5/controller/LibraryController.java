package com.sportt5.controller;

import com.sportt5.model.Songs;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.*;

import java.sql.SQLException;
import java.util.ArrayList;

public class LibraryController {

    @FXML private Label favouritesCount;
    @FXML private VBox trackListContainer;
    @FXML private Label tabSaved;
    @FXML private Label tabGenres;
    @FXML private Label tabArtists;

    // Placeholder until UserSession is implemented
    private static final int CURRENT_USER_ID = 1;

    private static final String[] THUMB_STYLES = {"thumb-city", "thumb-orange", "thumb-blue", "thumb-gold"};

    @FXML
    public void initialize() {
    }
}
