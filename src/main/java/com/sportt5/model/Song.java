package com.sportt5.model;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Maps to the `songs` table.
 * SongStatus mirrors the `song_status` enum: live | pending | deleted
 */
public class Song {

    // ── song_status enum ──────────────────────────────────────────────────────
    public enum SongStatus { live, pending, deleted }

    // ── Columns ───────────────────────────────────────────────────────────────
    private int id;
    private Integer albumId;
    private int artistId;
    private String title;
    private int durationSeconds;
    private String fileUrl;
    private String coverUrl;
    private Integer trackNumber;
    private long playCount;
    private SongStatus status;
    private Integer createdBy;
    private Integer updatedBy;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // ── Join / convenience fields (not stored in songs table) ─────────────────
    /** Main artist display name – populated via JOIN artists → users. */
    private String artistName;

    /**
     * Featured / additional artists from the song_artists join table.
     * Each entry carries the artist name and their role (featured, producer, remixer).
     */
    private List<SongArtistEntry> additionalArtists;

    // ── Constructors ──────────────────────────────────────────────────────────

    public Song() {}

    public Song(int id, Integer albumId, int artistId, String title,
                int durationSeconds, String fileUrl, String coverUrl,
                Integer trackNumber, long playCount, SongStatus status,
                String artistName) {
        this.id              = id;
        this.albumId         = albumId;
        this.artistId        = artistId;
        this.title           = title;
        this.durationSeconds = durationSeconds;
        this.fileUrl         = fileUrl;
        this.coverUrl        = coverUrl;
        this.trackNumber     = trackNumber;
        this.playCount       = playCount;
        this.status          = status;
        this.artistName      = artistName;
    }

    // ── Helper: format duration as mm:ss ─────────────────────────────────────
    public String getFormattedDuration() {
        int mins = durationSeconds / 60;
        int secs = durationSeconds % 60;
        return String.format("%d:%02d", mins, secs);
    }

    // ── Getters & Setters ─────────────────────────────────────────────────────

    public int getId()                                  { return id; }
    public void setId(int id)                           { this.id = id; }

    public Integer getAlbumId()                         { return albumId; }
    public void setAlbumId(Integer albumId)             { this.albumId = albumId; }

    public int getArtistId()                            { return artistId; }
    public void setArtistId(int artistId)               { this.artistId = artistId; }

    public String getTitle()                            { return title; }
    public void setTitle(String title)                  { this.title = title; }

    public int getDurationSeconds()                     { return durationSeconds; }
    public void setDurationSeconds(int durationSeconds) { this.durationSeconds = durationSeconds; }

    public String getFileUrl()                          { return fileUrl; }
    public void setFileUrl(String fileUrl)              { this.fileUrl = fileUrl; }

    public String getCoverUrl()                         { return coverUrl; }
    public void setCoverUrl(String coverUrl)            { this.coverUrl = coverUrl; }

    public Integer getTrackNumber()                     { return trackNumber; }
    public void setTrackNumber(Integer trackNumber)     { this.trackNumber = trackNumber; }

    public long getPlayCount()                          { return playCount; }
    public void setPlayCount(long playCount)            { this.playCount = playCount; }

    public SongStatus getStatus()                       { return status; }
    public void setStatus(SongStatus status)            { this.status = status; }

    public Integer getCreatedBy()                       { return createdBy; }
    public void setCreatedBy(Integer createdBy)         { this.createdBy = createdBy; }

    public Integer getUpdatedBy()                       { return updatedBy; }
    public void setUpdatedBy(Integer updatedBy)         { this.updatedBy = updatedBy; }

    public LocalDateTime getCreatedAt()                 { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt)   { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt()                 { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt)   { this.updatedAt = updatedAt; }

    public String getArtistName()                       { return artistName; }
    public void setArtistName(String artistName)        { this.artistName = artistName; }

    public List<SongArtistEntry> getAdditionalArtists()                         { return additionalArtists; }
    public void setAdditionalArtists(List<SongArtistEntry> additionalArtists)   { this.additionalArtists = additionalArtists; }

    // ── Inner record: song_artists row ────────────────────────────────────────

    /**
     * Represents one row from `song_artists` joined with artist/user display name.
     * role mirrors the `song_artist_role` enum: main | featured | producer | remixer
     */
    public static class SongArtistEntry {

        public enum Role { main, featured, producer, remixer }

        private int artistId;
        private String artistName;
        private Role role;

        public SongArtistEntry() {}

        public SongArtistEntry(int artistId, String artistName, Role role) {
            this.artistId   = artistId;
            this.artistName = artistName;
            this.role       = role;
        }

        public int getArtistId()                    { return artistId; }
        public void setArtistId(int artistId)       { this.artistId = artistId; }

        public String getArtistName()               { return artistName; }
        public void setArtistName(String name)      { this.artistName = name; }

        public Role getRole()                       { return role; }
        public void setRole(Role role)              { this.role = role; }
    }

    @Override
    public String toString() {
        return "Song{id=" + id + ", title='" + title + "', artist='" + artistName
                + "', track=" + trackNumber + ", status=" + status + "}";
    }
}