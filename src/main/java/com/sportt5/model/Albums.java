package com.sportt5.model;

import java.time.LocalDateTime;
import java.time.LocalDate;

public class Albums {
    private int id;
    private int artistId;
    private String title;
    private String coverUrl;
    private LocalDate releaseDate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Albums() {
    }

    public Albums(String title, int id, String coverUrl, int artistId, LocalDate releaseDate) {
        this.title = title;
        this.id = id;
        this.coverUrl = coverUrl;
        this.artistId = artistId;
        this.releaseDate = releaseDate;
    }

    public int getArtistId() { return artistId; }
    public void setArtistId(int artistId) { this.artistId = artistId; }

    public String getCoverUrl() { return coverUrl; }
    public void setCoverUrl(String coverUrl) { this.coverUrl = coverUrl; }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public LocalDate getReleaseDate() { return releaseDate; }
    public void setReleaseDate(LocalDate releaseDate) { this.releaseDate = releaseDate; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
}
