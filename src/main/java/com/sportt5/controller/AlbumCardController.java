package com.sportt5.controller;

import com.sportt5.model.Albums;
import com.sportt5.model.Playlists;
import com.sportt5.util.ApiClient;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class AlbumCardController {

    @FXML private ImageView imgCover;
    @FXML private Label lblTitle;
    @FXML private Label lblTrackCount;

    public void setAlbum(Albums album) {
        lblTitle.setText(album.getTitle());
        lblTrackCount.setText(album.getReleaseDate() != null
                ? String.valueOf(album.getReleaseDate().getYear()) : "");
        String url = ApiClient.resolveUrl(album.getCoverUrl());
        if (url != null) imgCover.setImage(new Image(url, true));
    }

    public void setPlaylist(Playlists playlist) {
        lblTitle.setText(playlist.getTitle());
        lblTrackCount.setText(playlist.getDescription() != null ? playlist.getDescription() : "");
        String url = ApiClient.resolveUrl(playlist.getCoverUrl());
        if (url != null) imgCover.setImage(new Image(url, true));
    }
}
