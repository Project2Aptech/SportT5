package com.sportt5.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sportt5.model.enums.RequiredAccountType;
import com.sportt5.model.enums.Status;

import java.time.LocalDateTime;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Songs {
    private int id;
    private int artistId;
    private int albumId;
    private String title;
    private int durationSeconds;

    private String fileUrl;
    private String coverUrl;

    private int trackNumber;
    private long playCount;

    private Status status = Status.LIVE;
    private RequiredAccountType requiredAccountType = RequiredAccountType.NORMAL;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Songs() {
    }

    public Songs(int albumId, int artistId, String coverUrl, int durationSeconds, String fileUrl, int id, long playCount, RequiredAccountType requiredAccountType, Status status, String title, int trackNumber) {
        this.albumId = albumId;
        this.artistId = artistId;
        this.coverUrl = coverUrl;
        this.durationSeconds = durationSeconds;
        this.fileUrl = fileUrl;
        this.id = id;
        this.playCount = playCount;
        this.requiredAccountType = requiredAccountType;
        this.status = status;
        this.title = title;
        this.trackNumber = trackNumber;
    }

    public int getAlbumId() { return albumId; }
    public void setAlbumId(int albumId) { this.albumId = albumId; }

    public int getArtistId() { return artistId; }
    public void setArtistId(int artistId) { this.artistId = artistId; }

    public String getCoverUrl() { return coverUrl; }
    public void setCoverUrl(String coverUrl) { this.coverUrl = coverUrl; }

    public int getDurationSeconds() { return durationSeconds; }
    public void setDurationSeconds(int durationSeconds) { this.durationSeconds = durationSeconds; }

    public String getFileUrl() { return fileUrl; }
    public void setFileUrl(String fileUrl) { this.fileUrl = fileUrl; }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public long getPlayCount() { return playCount; }
    public void setPlayCount(long playCount) { this.playCount = playCount; }

    public RequiredAccountType getRequiredAccountType() { return requiredAccountType; }
    public void setRequiredAccountType(RequiredAccountType requiredAccountType) { this.requiredAccountType = requiredAccountType; }

    public Status getStatus() { return status; }
    public void setStatus(Status status) { this.status = status; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public int getTrackNumber() { return trackNumber; }
    public void setTrackNumber(int trackNumber) { this.trackNumber = trackNumber; }
}
