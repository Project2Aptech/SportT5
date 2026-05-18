package com.sportt5.model;

public class SongGenres {
    private int song_id;
    private int genre_id;

    public SongGenres() {
    }

    public SongGenres(int genre_id, int song_id) {
        this.genre_id = genre_id;
        this.song_id = song_id;
    }

    public int getSong_id() {
        return song_id;
    }

    public void setSong_id(int song_id) {
        this.song_id = song_id;
    }

    public int getGenre_id() {
        return genre_id;
    }

    public void setGenre_id(int genre_id) {
        this.genre_id = genre_id;
    }
}
