package com.sportt5.dao.home;

import com.sportt5.database.DataSourceManagement;
import com.sportt5.model.Albums;
import com.sportt5.model.Songs;
import com.sportt5.model.enums.RequiredAccountType;
import com.sportt5.model.enums.Status;

import java.sql.*;
import java.util.ArrayList;

public class home_dao {
    private ArrayList<Albums> topAlbums = new ArrayList<>();
    private ArrayList<Songs> latestAlbumSongs = new ArrayList<>();
    private ArrayList<Songs> latestSingles = new ArrayList<>();
    private final Connection c = DataSourceManagement.getConnection();

    public home_dao() throws SQLException {}

    public ArrayList<Albums> getTopAlbums() { return topAlbums; }
    public ArrayList<Songs> getLatestAlbumSongs() { return latestAlbumSongs; }
    public ArrayList<Songs> getLatestSingles() { return latestSingles; }

    private Songs mapSong(ResultSet rs) throws SQLException {
        RequiredAccountType t = RequiredAccountType.valueOf(rs.getString("required_account_type"));
        Status s = Status.valueOf(rs.getString("status"));
        return new Songs(
                rs.getInt("album_id"),
                rs.getInt("artist_id"),
                rs.getString("cover_url"),
                rs.getInt("duration_seconds"),
                rs.getString("file_url"),
                rs.getInt("id"),
                rs.getLong("play_count"),
                t,
                s,
                rs.getString("title"),
                rs.getInt("track_number")
        );
    }

    // Top albums xếp theo tổng play_count của các bài trong album
    public boolean loadTopAlbums() throws SQLException {
        String sql = "SELECT a.id, a.artist_id, a.title, a.cover_url, a.release_date " +
                     "FROM albums a " +
                     "INNER JOIN songs s ON s.album_id = a.id " +
                     "WHERE s.status = ? " +
                     "GROUP BY a.id, a.artist_id, a.title, a.cover_url, a.release_date " +
                     "ORDER BY SUM(s.play_count) DESC";
        PreparedStatement ps = c.prepareStatement(sql);
        ps.setString(1, "LIVE");

        topAlbums.clear();
        boolean hasData = false;
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            hasData = true;
            Albums album = new Albums(
                    rs.getString("title"),
                    rs.getInt("id"),
                    rs.getString("cover_url"),
                    rs.getInt("artist_id"),
                    rs.getDate("release_date").toLocalDate()
            );
            topAlbums.add(album);
        }
        return hasData;
    }

    // Latest songs thuộc album (album_id > 0), sắp xếp theo id mới nhất
    public boolean loadLatestAlbumSongs() throws SQLException {
        String sql = "SELECT * FROM songs WHERE status = ? AND album_id > 0 ORDER BY id DESC";
        PreparedStatement ps = c.prepareStatement(sql);
        ps.setString(1, "LIVE");

        latestAlbumSongs.clear();
        boolean hasData = false;
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            hasData = true;
            latestAlbumSongs.add(mapSong(rs));
        }
        return hasData;
    }

    // Latest singles (không thuộc album, album_id = 0), sắp xếp theo id mới nhất
    public boolean loadLatestSingles() throws SQLException {
        String sql = "SELECT * FROM songs WHERE status = ? AND album_id = 0 ORDER BY id DESC";
        PreparedStatement ps = c.prepareStatement(sql);
        ps.setString(1, "LIVE");

        latestSingles.clear();
        boolean hasData = false;
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            hasData = true;
            latestSingles.add(mapSong(rs));
        }
        return hasData;
    }
}
