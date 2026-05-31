package com.sportt5.controller.components;

import com.sportt5.model.Albums;
import com.sportt5.util.ApiClient;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class HomeAlbumRowController {
    @FXML private Label lblIndex;
    @FXML private ImageView imgTrackCover;
    @FXML private Label lblTitle;

    public void setAlbum(int index, Albums album) {
        lblIndex.setText(String.format("%02d", index));
        lblTitle.setText(album.getTitle());

        String url = ApiClient.resolveUrl(album.getCoverUrl());
        if (url != null) imgTrackCover.setImage(new Image(url, true));
    }
}
