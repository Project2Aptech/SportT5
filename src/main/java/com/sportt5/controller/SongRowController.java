package com.sportt5.controller;

import com.sportt5.model.Songs;
import com.sportt5.model.Users;
import com.sportt5.model.enums.AccountType;
import com.sportt5.model.enums.RequiredAccountType;
import com.sportt5.session.UserSession;
import com.sportt5.util.ApiClient;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

public class SongRowController {

    @FXML private HBox rowRoot;
    @FXML private Label lblIndex;
    @FXML private Label lblTrackTitle;
    @FXML private Label lblArtist;
    @FXML private Label lblAlbum;
    @FXML private Label lblDateAdded;
    @FXML private Label lblDuration;
    @FXML private Label lockBadge;
    @FXML private ImageView imgTrackCover;

    public void setSong(int index, Songs song, String albumTitle) {
        lblArtist.setText("");
        lblAlbum.setText(albumTitle);
        lblDateAdded.setText("");

        int secs = song.getDurationSeconds();
        lblDuration.setText(String.format("%d:%02d", secs / 60, secs % 60));

        String url = ApiClient.resolveUrl(song.getCoverUrl());
        if (url != null) imgTrackCover.setImage(new Image(url, true));

        boolean locked = !canAccess(song);
        if (locked) {
            applyLockedState(index, song);
        } else {
            applyUnlockedState(index, song);
        }
    }

    private void applyLockedState(int index, Songs song) {
        lblIndex.setText("🔒");
        lblTrackTitle.setText(song.getTitle());

        RequiredAccountType req = song.getRequiredAccountType() != null
                ? song.getRequiredAccountType() : RequiredAccountType.NORMAL;

        lockBadge.setText(req.name());
        lockBadge.getStyleClass().setAll(
                "lock-badge",
                req == RequiredAccountType.PREMIUM ? "lock-badge-premium" : "lock-badge-pro"
        );
        lockBadge.setVisible(true);
        lockBadge.setManaged(true);

        if (!rowRoot.getStyleClass().contains("song-row-locked")) {
            rowRoot.getStyleClass().add("song-row-locked");
        }
    }

    private void applyUnlockedState(int index, Songs song) {
        lblIndex.setText(String.format("%02d", index));
        lblTrackTitle.setText(song.getTitle());

        lockBadge.setVisible(false);
        lockBadge.setManaged(false);
        rowRoot.getStyleClass().remove("song-row-locked");
    }

    // NORMAL(0) < PRO(1) < PREMIUM(2) — so sánh theo ordinal
    private boolean canAccess(Songs song) {
        UserSession session = UserSession.getInstance();
        if (session == null) return true;
        Users user = session.getCurrentUser();
        if (user == null) return true;

        AccountType userType = user.getAccountType() != null
                ? user.getAccountType() : AccountType.NORMAL;
        RequiredAccountType required = song.getRequiredAccountType() != null
                ? song.getRequiredAccountType() : RequiredAccountType.NORMAL;

        return userType.ordinal() >= required.ordinal();
    }
}
