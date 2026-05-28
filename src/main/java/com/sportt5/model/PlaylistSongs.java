package com.sportt5.model;

import java.time.LocalDateTime;

public class PlaylistSongs {
    private int playlistId;
    private int songId;
    private LocalDateTime addedAt;

    public PlaylistSongs() {
    }

    public PlaylistSongs(LocalDateTime addedAt, int playlistId, int songId) {
        this.addedAt = addedAt;
        this.playlistId = playlistId;
        this.songId = songId;
    }

    public LocalDateTime getAddedAt() { return addedAt; }
    public void setAddedAt(LocalDateTime addedAt) { this.addedAt = addedAt; }

    public int getPlaylistId() { return playlistId; }
    public void setPlaylistId(int playlistId) { this.playlistId = playlistId; }

    public int getSongId() { return songId; }
    public void setSongId(int songId) { this.songId = songId; }
}
