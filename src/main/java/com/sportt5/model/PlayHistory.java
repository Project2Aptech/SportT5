package com.sportt5.model;

import com.sportt5.model.enums.DeviceType;

import java.time.LocalDateTime;

public class PlayHistory {
    private long id;
    private int user_id;
    private int song_id;
    private LocalDateTime played_at;
    private int seconds_played;
    private DeviceType device_type = DeviceType.DESKTOP;

    public PlayHistory() {
    }

    public PlayHistory(DeviceType device_type, long id, LocalDateTime played_at,
                       int seconds_played, int song_id, int user_id) {
        this.device_type = device_type;
        this.id = id;
        this.played_at = played_at;
        this.seconds_played = seconds_played;
        this.song_id = song_id;
        this.user_id = user_id;
    }

    public DeviceType getDevice_type() { return device_type; }
    public void setDevice_type(DeviceType device_type) { this.device_type = device_type; }

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public LocalDateTime getPlayed_at() { return played_at; }
    public void setPlayed_at(LocalDateTime played_at) { this.played_at = played_at; }

    public int getSeconds_played() { return seconds_played; }
    public void setSeconds_played(int seconds_played) { this.seconds_played = seconds_played; }

    public int getSong_id() { return song_id; }
    public void setSong_id(int song_id) { this.song_id = song_id; }

    public int getUser_id() { return user_id; }
    public void setUser_id(int user_id) { this.user_id = user_id; }
}
