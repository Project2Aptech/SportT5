package com.sportt5.model;

import java.time.LocalDateTime;
import java.time.LocalDate;

public class Albums {
    private int id;
    private int artist_id;
    private String title;
    private String cover_url;
    private LocalDate release_date;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;
    public Albums() {
    }

    public Albums(String title, int id, String cover_url, int artist_id, LocalDate release_date) {
        this.title = title;
        this.id = id;
        this.cover_url = cover_url;
        this.artist_id = artist_id;
        this.release_date = release_date;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getRelease_date() {
        return release_date;
    }

    public void setRelease_date(LocalDate release_date) {
        this.release_date = release_date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
