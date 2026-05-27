package com.sportt5.model;

import java.time.LocalDateTime;

public class LikedSongs {
    private int userId;
    private int songId;
    private LocalDateTime likedAt;

    public LikedSongs() {
    }

    public LikedSongs(LocalDateTime likedAt, int songId, int userId) {
        this.likedAt = likedAt;
        this.songId = songId;
        this.userId = userId;
    }

    public LocalDateTime getLikedAt() { return likedAt; }
    public void setLikedAt(LocalDateTime likedAt) { this.likedAt = likedAt; }

    public int getSongId() { return songId; }
    public void setSongId(int songId) { this.songId = songId; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }
}
