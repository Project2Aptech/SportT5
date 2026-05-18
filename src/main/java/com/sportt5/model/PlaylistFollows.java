package com.sportt5.model;

import java.time.LocalDateTime;

public class PlaylistFollows {
    private int user_id;
    private int playlist_id;
    private LocalDateTime followed_at;
    public PlaylistFollows() {
    }

    public PlaylistFollows(LocalDateTime followed_at, int playlist_id, int user_id) {
        this.followed_at = followed_at;
        this.playlist_id = playlist_id;
        this.user_id = user_id;
    }

    public LocalDateTime getFollowed_at() {
        return followed_at;
    }

    public void setFollowed_at(LocalDateTime followed_at) {
        this.followed_at = followed_at;
    }

    public int getPlaylist_id() {
        return playlist_id;
    }

    public void setPlaylist_id(int playlist_id) {
        this.playlist_id = playlist_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }
}
