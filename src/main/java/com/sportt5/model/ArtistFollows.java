package com.sportt5.model;

import java.time.LocalDateTime;

public class ArtistFollows {
    private int userId;
    private int artistId;
    private LocalDateTime followedAt;

    public ArtistFollows() {
    }

    public ArtistFollows(int artistId, LocalDateTime followedAt, int userId) {
        this.artistId = artistId;
        this.followedAt = followedAt;
        this.userId = userId;
    }

    public int getArtistId() { return artistId; }
    public void setArtistId(int artistId) { this.artistId = artistId; }

    public LocalDateTime getFollowedAt() { return followedAt; }
    public void setFollowedAt(LocalDateTime followedAt) { this.followedAt = followedAt; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }
}
