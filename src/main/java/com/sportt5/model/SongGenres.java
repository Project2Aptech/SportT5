package com.sportt5.model;

public class SongGenres {
    private int songId;
    private int genreId;

    public SongGenres() {
    }

    public SongGenres(int genreId, int songId) {
        this.genreId = genreId;
        this.songId = songId;
    }

    public int getSongId() { return songId; }
    public void setSongId(int songId) { this.songId = songId; }

    public int getGenreId() { return genreId; }
    public void setGenreId(int genreId) { this.genreId = genreId; }
}
