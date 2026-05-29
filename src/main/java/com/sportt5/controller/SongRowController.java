package com.sportt5.controller;

import com.sportt5.model.Songs;
import com.sportt5.util.ApiClient;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class SongRowController {

    @FXML private Label lblIndex;
    @FXML private Label lblTrackTitle;
    @FXML private Label lblArtist;
    @FXML private Label lblAlbum;
    @FXML private Label lblDateAdded;
    @FXML private Label lblDuration;
    @FXML private ImageView imgTrackCover;

    public void setSong(int index, Songs song, String albumTitle) {
        lblIndex.setText(String.format("%02d", index));
        lblTrackTitle.setText(song.getTitle());
        lblArtist.setText("");
        lblAlbum.setText(albumTitle);
        lblDateAdded.setText("");

        int secs = song.getDurationSeconds();
        lblDuration.setText(String.format("%d:%02d", secs / 60, secs % 60));

        String url = ApiClient.resolveUrl(song.getCoverUrl());
        if (url != null) imgTrackCover.setImage(new Image(url, true));
    }
}
