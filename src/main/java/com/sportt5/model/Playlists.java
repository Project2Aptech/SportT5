package com.sportt5.model;

import java.time.LocalDateTime;

public class Playlists {
    private int id;
    private int userId;
    private String title;
    private String description;
    private String coverUrl;
    private boolean isPublic;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Playlists() {
    }

    public Playlists(String coverUrl, String description, int id, boolean isPublic, String title, int userId) {
        this.coverUrl = coverUrl;
        this.description = description;
        this.id = id;
        this.isPublic = isPublic;
        this.title = title;
        this.userId = userId;
    }

    public String getCoverUrl() { return coverUrl; }
    public void setCoverUrl(String coverUrl) { this.coverUrl = coverUrl; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public boolean isPublic() { return isPublic; }
    @com.fasterxml.jackson.annotation.JsonProperty("public")
    public void setPublic(boolean isPublic) { this.isPublic = isPublic; }
    @com.fasterxml.jackson.annotation.JsonProperty("isPublic")
    public void setIsPublic(boolean isPublic) { this.isPublic = isPublic; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }
}
