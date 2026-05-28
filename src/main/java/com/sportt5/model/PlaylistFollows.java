package com.sportt5.model;

import java.time.LocalDateTime;

public class PlaylistFollows {
    private int userId;
    private int playlistId;
    private LocalDateTime followedAt;

    public PlaylistFollows() {
    }

    public PlaylistFollows(LocalDateTime followedAt, int playlistId, int userId) {
        this.followedAt = followedAt;
        this.playlistId = playlistId;
        this.userId = userId;
    }

    public LocalDateTime getFollowedAt() { return followedAt; }
    public void setFollowedAt(LocalDateTime followedAt) { this.followedAt = followedAt; }

    public int getPlaylistId() { return playlistId; }
    public void setPlaylistId(int playlistId) { this.playlistId = playlistId; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }
}
