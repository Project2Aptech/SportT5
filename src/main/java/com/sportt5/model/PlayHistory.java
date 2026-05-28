package com.sportt5.model;

import com.sportt5.model.enums.DeviceType;

import java.time.LocalDateTime;

public class PlayHistory {
    private long id;
    private int userId;
    private int songId;
    private LocalDateTime playedAt;
    private int secondsPlayed;
    private DeviceType deviceType = DeviceType.DESKTOP;

    public PlayHistory() {
    }

    public PlayHistory(DeviceType deviceType, long id, LocalDateTime playedAt,
                       int secondsPlayed, int songId, int userId) {
        this.deviceType = deviceType;
        this.id = id;
        this.playedAt = playedAt;
        this.secondsPlayed = secondsPlayed;
        this.songId = songId;
        this.userId = userId;
    }

    public DeviceType getDeviceType() { return deviceType; }
    public void setDeviceType(DeviceType deviceType) { this.deviceType = deviceType; }

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public LocalDateTime getPlayedAt() { return playedAt; }
    public void setPlayedAt(LocalDateTime playedAt) { this.playedAt = playedAt; }

    public int getSecondsPlayed() { return secondsPlayed; }
    public void setSecondsPlayed(int secondsPlayed) { this.secondsPlayed = secondsPlayed; }

    public int getSongId() { return songId; }
    public void setSongId(int songId) { this.songId = songId; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }
}
