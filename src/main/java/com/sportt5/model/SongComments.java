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


}
