package com.sportt5.model;

import java.time.LocalDateTime;

public class LikedSongs {
    private int user_id;
    private int song_id;
    private LocalDateTime liked_at;
    public LikedSongs() {
    }

    public LikedSongs(LocalDateTime liked_at, int song_id, int user_id) {
        this.liked_at = liked_at;
        this.song_id = song_id;
        this.user_id = user_id;
    }

    public LocalDateTime getLiked_at() {
        return liked_at;
    }

    public void setLiked_at(LocalDateTime liked_at) {
        this.liked_at = liked_at;
    }

    public int getSong_id() {
        return song_id;
    }

    public void setSong_id(int song_id) {
        this.song_id = song_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }
}
