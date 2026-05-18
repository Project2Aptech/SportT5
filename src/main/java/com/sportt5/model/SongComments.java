package com.sportt5.model;

import java.time.LocalDateTime;

public class SongComments {
    private long id;
    private int song_id;
    private int user_id;
    private long parent_comment_id;
    private String content;
    private boolean is_deleted;
    private LocalDateTime created_at;

    public SongComments() {
    }

    public SongComments(String content, long id, boolean is_deleted, long parent_comment_id, int song_id, int user_id) {
        this.content = content;
        this.id = id;
        this.is_deleted = is_deleted;
        this.parent_comment_id = parent_comment_id;
        this.song_id = song_id;
        this.user_id = user_id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean isIs_deleted() {
        return is_deleted;
    }

    public void setIs_deleted(boolean is_deleted) {
        this.is_deleted = is_deleted;
    }

    public long getParent_comment_id() {
        return parent_comment_id;
    }

    public void setParent_comment_id(long parent_comment_id) {
        this.parent_comment_id = parent_comment_id;
    }

    public int getSong_id() {
        return song_id;
    }

    public void setSong_id(int song_id) {
        this.song_id = song_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }
}
