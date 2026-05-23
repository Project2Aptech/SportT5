package com.sportt5.dao.playlist;

import com.sportt5.database.DataSourceManagement;
import com.sportt5.model.Playlists;
import com.sportt5.model.Songs;
import com.sportt5.model.enums.RequiredAccountType;
import com.sportt5.model.enums.Status;

import java.sql.*;
import java.util.ArrayList;

public class playlist_dao {
    private final ArrayList<Playlists> playlists = new ArrayList<>();
    private final ArrayList<Songs> songs = new ArrayList<>();
    private final ArrayList<String> albumTitles = new ArrayList<>();
    private final Connection c = DataSourceManagement.getConnection();

    public playlist_dao() throws SQLException {}

    public ArrayList<Playlists> getPlaylists() { return playlists; }
    public ArrayList<Songs> getSongs() { return songs; }
    public ArrayList<String> getAlbumTitles() { return albumTitles; }

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

    public boolean loadAllPlaylists() throws SQLException {
        String sql = "SELECT * FROM playlists WHERE is_public = 1 ORDER BY created_at DESC";
        PreparedStatement ps = c.prepareStatement(sql);
        playlists.clear();
        boolean hasData = false;
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            hasData = true;
            Playlists pl = new Playlists();
            pl.setId(rs.getInt("id"));
            pl.setUser_id(rs.getInt("user_id"));
            pl.setTitle(rs.getString("title"));
            pl.setDescription(rs.getString("description"));
            pl.setCover_url(rs.getString("cover_url"));
            pl.setIs_public(rs.getBoolean("is_public"));
            playlists.add(pl);
        }
        return hasData;
    }

    public boolean loadSongsByPlaylist(int playlistId) throws SQLException {
        String sql = "SELECT s.*, COALESCE(a.title, '') AS album_title " +
                "FROM songs s " +
                "LEFT JOIN albums a ON a.id = s.album_id " +
                "INNER JOIN playlist_songs ps ON ps.song_id = s.id " +
                "WHERE ps.playlist_id = ? AND s.status = ? " +
                "ORDER BY ps.added_at ASC";
        PreparedStatement ps = c.prepareStatement(sql);
        ps.setInt(1, playlistId);
        ps.setString(2, "LIVE");
        songs.clear();
        albumTitles.clear();
        boolean hasData = false;
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            hasData = true;
            songs.add(mapSong(rs));
            albumTitles.add(rs.getString("album_title"));
        }
        return hasData;
    }
}
