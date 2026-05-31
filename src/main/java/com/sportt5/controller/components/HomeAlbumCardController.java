package com.sportt5.controller.components;

import com.sportt5.model.Albums;
import com.sportt5.util.ApiClient;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class HomeAlbumCardController {
    @FXML private ImageView imgCover;
    @FXML private Label lblTitle;
    @FXML private Label lblArtist;

    public void setAlbum(Albums album) {
        lblTitle.setText(album.getTitle());
        lblArtist.setText(album.getArtistName());
        String url = ApiClient.resolveUrl(album.getCoverUrl());
        if (url != null) imgCover.setImage(new Image(url, true));
    }
}
