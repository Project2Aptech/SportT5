package com.sportt5.model;

import java.time.LocalDateTime;

public class SongComments {
    private long id;
    private int songId;
    private int userId;
    private long parentCommentId;
    private String content;
    private boolean isDeleted;
    private LocalDateTime createdAt;

    public SongComments() {
    }

    public SongComments(String content, long id, boolean isDeleted, long parentCommentId, int songId, int userId) {
        this.content = content;
        this.id = id;
        this.isDeleted = isDeleted;
        this.parentCommentId = parentCommentId;
        this.songId = songId;
        this.userId = userId;
    }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public boolean isDeleted() { return isDeleted; }
    public void setDeleted(boolean isDeleted) { this.isDeleted = isDeleted; }

    public long getParentCommentId() { return parentCommentId; }
    public void setParentCommentId(long parentCommentId) { this.parentCommentId = parentCommentId; }

    public int getSongId() { return songId; }
    public void setSongId(int songId) { this.songId = songId; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }
}
