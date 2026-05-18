package com.sportt5.model;

import java.time.LocalDateTime;

public class Playlists {
    private int id;
    private int user_id;
    private String title;
    private String description;
    private String cover_url;
    private boolean is_public;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;

    public Playlists() {
    }

    public Playlists(String cover_url, String description, int id, boolean is_public, String title, int user_id) {
        this.cover_url = cover_url;
        this.description = description;
        this.id = id;
        this.is_public = is_public;
        this.title = title;
        this.user_id = user_id;
    }

    public String getCover_url() {
        return cover_url;
    }

    public void setCover_url(String cover_url) {
        this.cover_url = cover_url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isIs_public() {
        return is_public;
    }

    public void setIs_public(boolean is_public) {
        this.is_public = is_public;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }
}
