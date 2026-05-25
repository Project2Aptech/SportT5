package com.sportt5.dao.library;

import com.sportt5.database.DataSourceManagement;
import com.sportt5.model.Songs;
import com.sportt5.model.enums.RequiredAccountType;
import com.sportt5.model.enums.Status;

import java.sql.*;
import java.util.ArrayList;

public class library_dao {
    private final ArrayList<Songs> songlist = new ArrayList<>();
    private final ArrayList<String> albumTitles = new ArrayList<>();
    private final ArrayList<String> datesAdded = new ArrayList<>();
    private final Connection c = DataSourceManagement.getConnection();

    public library_dao() throws SQLException {}

    public ArrayList<Songs> getSonglist() { return songlist; }
    public ArrayList<String> getAlbumTitles() { return albumTitles; }
    public ArrayList<String> getDatesAdded() { return datesAdded; }

    private void clear() {
        songlist.clear();
        albumTitles.clear();
        datesAdded.clear();
    }

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

    private boolean dataFetch(PreparedStatement ps, boolean fetchDate) throws SQLException {
        clear();
        boolean hasData = false;
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            hasData = true;
            songlist.add(mapSong(rs));
            albumTitles.add(rs.getString("album_title"));
            if (fetchDate) {
                Timestamp ts = rs.getTimestamp("liked_at");
                datesAdded.add(ts != null ? ts.toLocalDateTime().toLocalDate().toString() : "");
            } else {
                datesAdded.add("");
            }
        }
        return hasData;
    }

    public boolean viewAllSongs() throws SQLException {
        String sql = "SELECT s.*, COALESCE(a.title, '') AS album_title " +
                     "FROM songs s LEFT JOIN albums a ON a.id = s.album_id " +
                     "WHERE s.status = ?";
        PreparedStatement ps = c.prepareStatement(sql);
        ps.setString(1, "LIVE");
        return dataFetch(ps, false);
    }

    public boolean sortByPlayCount() throws SQLException {
        String sql = "SELECT s.*, COALESCE(a.title, '') AS album_title " +
                     "FROM songs s LEFT JOIN albums a ON a.id = s.album_id " +
                     "WHERE s.status = ? ORDER BY s.play_count DESC";
        PreparedStatement ps = c.prepareStatement(sql);
        ps.setString(1, "LIVE");
        return dataFetch(ps, false);
    }

    public boolean filterByArtists(int artist_id) throws SQLException {
        String sql = "SELECT s.*, COALESCE(a.title, '') AS album_title " +
                     "FROM songs s LEFT JOIN albums a ON a.id = s.album_id " +
                     "WHERE s.artist_id = ?";
        PreparedStatement ps = c.prepareStatement(sql);
        ps.setInt(1, artist_id);
        return dataFetch(ps, false);
    }

    public boolean filterByLiked(int user_id) throws SQLException {
        String sql = "SELECT s.id, s.artist_id, s.album_id, s.title, s.duration_seconds, " +
                     "s.file_url, s.cover_url, s.track_number, s.play_count, s.status, " +
                     "s.required_account_type, COALESCE(a.title, '') AS album_title, ls.liked_at " +
                     "FROM liked_songs ls " +
                     "INNER JOIN songs s ON ls.song_id = s.id " +
                     "LEFT JOIN albums a ON a.id = s.album_id " +
                     "WHERE ls.user_id = ? AND s.status = ? " +
                     "ORDER BY ls.liked_at DESC";
        PreparedStatement ps = c.prepareStatement(sql);
        ps.setInt(1, user_id);
        ps.setString(2, "LIVE");
        return dataFetch(ps, true);
    }

    public int getLikedCount(int user_id) throws SQLException {
        String sql = "SELECT COUNT(*) FROM liked_songs ls " +
                     "INNER JOIN songs s ON ls.song_id = s.id " +
                     "WHERE ls.user_id = ? AND s.status = ?";
        PreparedStatement ps = c.prepareStatement(sql);
        ps.setInt(1, user_id);
        ps.setString(2, "LIVE");
        ResultSet rs = ps.executeQuery();
        return rs.next() ? rs.getInt(1) : 0;
    }
}
