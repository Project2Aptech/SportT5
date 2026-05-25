package com.sportt5.model;

import java.time.LocalDateTime;

public class PlaylistSongs {
    private int playlist_id;
    private int song_id;
    private LocalDateTime added_at;

    public PlaylistSongs() {
    }

    public PlaylistSongs(LocalDateTime added_at, int playlist_id, int song_id) {
        this.added_at = added_at;
        this.playlist_id = playlist_id;
        this.song_id = song_id;
    }

    public LocalDateTime getAdded_at() {
        return added_at;
    }

    public void setAdded_at(LocalDateTime added_at) {
        this.added_at = added_at;
    }

    public int getPlaylist_id() {
        return playlist_id;
    }

    public void setPlaylist_id(int playlist_id) {
        this.playlist_id = playlist_id;
    }

    public int getSong_id() {
        return song_id;
    }

    public void setSong_id(int song_id) {
        this.song_id = song_id;
    }
}
