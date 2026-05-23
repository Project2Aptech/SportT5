package com.sportt5.dao.genre;

import com.sportt5.database.DataSourceManagement;
import com.sportt5.model.Genres;
import com.sportt5.model.Songs;
import com.sportt5.model.enums.RequiredAccountType;
import com.sportt5.model.enums.Status;

import java.sql.*;
import java.util.ArrayList;

public class genre_dao {
    private final ArrayList<Genres> genres = new ArrayList<>();
    private final ArrayList<Songs> songs = new ArrayList<>();
    private final Connection c = DataSourceManagement.getConnection();

    public genre_dao() throws SQLException {}

    public ArrayList<Genres> getGenres() { return genres; }
    public ArrayList<Songs> getSongs() { return songs; }

    private Songs mapSong(ResultSet rs) throws SQLException {
        return new Songs(
                rs.getInt("album_id"),
                rs.getInt("artist_id"),
                rs.getString("cover_url"),
                rs.getInt("duration_seconds"),
                rs.getString("file_url"),
                rs.getInt("id"),
                rs.getLong("play_count"),
                RequiredAccountType.valueOf(rs.getString("required_account_type")),
                Status.valueOf(rs.getString("status")),
                rs.getString("title"),
                rs.getInt("track_number")
        );
    }

    public boolean loadAllGenres() throws SQLException {
        String sql = "SELECT * FROM genres ORDER BY name";
        PreparedStatement ps = c.prepareStatement(sql);
        genres.clear();
        boolean hasData = false;
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            hasData = true;
            genres.add(new Genres(rs.getInt("id"), rs.getString("name"), rs.getString("slug")));
        }
        return hasData;
    }

    public boolean loadAllSongs() throws SQLException {
        String sql = "SELECT * FROM songs WHERE status = ? ORDER BY play_count DESC";
        PreparedStatement ps = c.prepareStatement(sql);
        ps.setString(1, "LIVE");
        songs.clear();
        boolean hasData = false;
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            hasData = true;
            songs.add(mapSong(rs));
        }
        return hasData;
    }

    public boolean loadSongsByGenre(int genreId) throws SQLException {
        String sql = "SELECT s.* FROM songs s " +
                "INNER JOIN song_genres sg ON sg.song_id = s.id " +
                "WHERE sg.genre_id = ? AND s.status = ? " +
                "ORDER BY s.play_count DESC";
        PreparedStatement ps = c.prepareStatement(sql);
        ps.setInt(1, genreId);
        ps.setString(2, "LIVE");
        songs.clear();
        boolean hasData = false;
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            hasData = true;
            songs.add(mapSong(rs));
        }
        return hasData;
    }
}
