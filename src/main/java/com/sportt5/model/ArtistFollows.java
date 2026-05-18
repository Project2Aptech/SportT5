package com.sportt5.model;

import java.time.LocalDateTime;

public class ArtistFollows {
    private int user_id;
    private int artist_id;
    private LocalDateTime followed_at;
    public ArtistFollows() {
    }

    public ArtistFollows(int artist_id, LocalDateTime followed_at, int user_id) {
        this.artist_id = artist_id;
        this.followed_at = followed_at;
        this.user_id = user_id;
    }

    public int getArtist_id() {
        return artist_id;
    }

    public void setArtist_id(int artist_id) {
        this.artist_id = artist_id;
    }

    public LocalDateTime getFollowed_at() {
        return followed_at;
    }

    public void setFollowed_at(LocalDateTime followed_at) {
        this.followed_at = followed_at;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }
}
