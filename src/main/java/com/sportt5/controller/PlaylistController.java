package com.sportt5.controller;

import com.sportt5.model.Playlists;
import com.sportt5.model.Songs;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PlaylistController {

    @FXML private HBox playlistCardsBox;
    @FXML private Label playlistEyebrow;
    @FXML private Label playlistHeading;
    @FXML private Label playlistSongCount;
    @FXML private Label playlistDuration;
    @FXML private VBox songListVBox;


    private static final String[] ART_STYLES = {"art-green", "art-city", "art-gold"};
    private static final String[] THUMB_STYLES = {"thumb-city", "thumb-orange", "thumb-blue", "thumb-gold"};

    @FXML
    public void initialize() {
    }
}