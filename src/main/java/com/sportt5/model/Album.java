package com.sportt5.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Maps to the `albums` table.
 */
public class Album {

    private int id;
    private int artistId;
    private String title;
    private String coverUrl;
    private LocalDate releaseDate;
    private int totalTracks;
    private Integer createdBy;
    private Integer updatedBy;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // ── Convenience / join fields (not stored in table) ───────────────────────
    /** Display name of the owning artist – populated via JOIN on artists → users. */
    private String artistName;

    // ── Constructors ──────────────────────────────────────────────────────────

    public Album() {}

    public Album(int id, int artistId, String title, String coverUrl,
                 LocalDate releaseDate, int totalTracks, String artistName) {
        this.id          = id;
        this.artistId    = artistId;
        this.title       = title;
        this.coverUrl    = coverUrl;
        this.releaseDate = releaseDate;
        this.totalTracks = totalTracks;
        this.artistName  = artistName;
    }

    // ── Getters & Setters ─────────────────────────────────────────────────────

    public int getId()                          { return id; }
    public void setId(int id)                   { this.id = id; }

    public int getArtistId()                    { return artistId; }
    public void setArtistId(int artistId)       { this.artistId = artistId; }

    public String getTitle()                    { return title; }
    public void setTitle(String title)          { this.title = title; }

    public String getCoverUrl()                 { return coverUrl; }
    public void setCoverUrl(String coverUrl)    { this.coverUrl = coverUrl; }

    public LocalDate getReleaseDate()                       { return releaseDate; }
    public void setReleaseDate(LocalDate releaseDate)       { this.releaseDate = releaseDate; }

    public int getTotalTracks()                             { return totalTracks; }
    public void setTotalTracks(int totalTracks)             { this.totalTracks = totalTracks; }

    public Integer getCreatedBy()                           { return createdBy; }
    public void setCreatedBy(Integer createdBy)             { this.createdBy = createdBy; }

    public Integer getUpdatedBy()                           { return updatedBy; }
    public void setUpdatedBy(Integer updatedBy)             { this.updatedBy = updatedBy; }

    public LocalDateTime getCreatedAt()                     { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt)       { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt()                     { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt)       { this.updatedAt = updatedAt; }

    public String getArtistName()                           { return artistName; }
    public void setArtistName(String artistName)            { this.artistName = artistName; }

    @Override
    public String toString() {
        return "Album{id=" + id + ", title='" + title + "', artist='" + artistName + "'}";
    }
}