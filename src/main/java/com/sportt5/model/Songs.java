package com.sportt5.model;

import com.sportt5.model.enums.RequiredAccountType;
import com.sportt5.model.enums.Status;

import java.time.LocalDateTime;

public class Songs {
    private int id;
    private int artist_id;
    private int album_id;
    private String title;
    private int duration_seconds;

    private String file_url;
    private String cover_url;

    private int track_number;
    private long play_count;

    private Status status = Status.LIVE;
    private RequiredAccountType required_account_type = RequiredAccountType.NORMAL;

    private LocalDateTime created_at;
    private LocalDateTime updated_at;

    public Songs() {
    }

    public Songs(int album_id, int artist_id, String cover_url, int duration_seconds, String file_url, int id, long play_count, RequiredAccountType required_account_type, Status status, String title, int track_number) {
        this.album_id = album_id;
        this.artist_id = artist_id;
        this.cover_url = cover_url;
        this.duration_seconds = duration_seconds;
        this.file_url = file_url;
        this.id = id;
        this.play_count = play_count;
        this.required_account_type = required_account_type;
        this.status = status;
        this.title = title;
        this.track_number = track_number;
    }

    public int getAlbum_id() {
        return album_id;
    }

    public void setAlbum_id(int album_id) {
        this.album_id = album_id;
    }

    public int getArtist_id() {
        return artist_id;
    }

    public void setArtist_id(int artist_id) {
        this.artist_id = artist_id;
    }

    public String getCover_url() {
        return cover_url;
    }

    public void setCover_url(String cover_url) {
        this.cover_url = cover_url;
    }

    public int getDuration_seconds() {
        return duration_seconds;
    }

    public void setDuration_seconds(int duration_seconds) {
        this.duration_seconds = duration_seconds;
    }

    public String getFile_url() {
        return file_url;
    }

    public void setFile_url(String file_url) {
        this.file_url = file_url;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getPlay_count() {
        return play_count;
    }

    public void setPlay_count(long play_count) {
        this.play_count = play_count;
    }

    public RequiredAccountType getRequired_account_type() {
        return required_account_type;
    }

    public void setRequired_account_type(RequiredAccountType required_account_type) {
        this.required_account_type = required_account_type;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getTrack_number() {
        return track_number;
    }

    public void setTrack_number(int track_number) {
        this.track_number = track_number;
    }
}
