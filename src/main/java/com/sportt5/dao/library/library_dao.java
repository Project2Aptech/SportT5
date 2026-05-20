package com.sportt5.dao.library;

import com.sportt5.database.DataSourceManagement;
import com.sportt5.model.Songs;
import com.sportt5.model.enums.RequiredAccountType;
import com.sportt5.model.enums.Status;

import java.sql.*;
import java.util.ArrayList;

public class library_dao {
    private ArrayList<Songs> songlist = new ArrayList<>();
    private final Connection c = DataSourceManagement.getConnection();

    public library_dao() throws SQLException {}

    public ArrayList<Songs> getSonglist() { return songlist; }


    //Xóa hết dữ liệu từ songlist cũ
    private void dataDelete() { songlist.clear(); }

    //Thêm dữ liệu vào songlist mới
    private boolean dataAdd(PreparedStatement ps) throws SQLException {
        boolean hasData = false;
        ResultSet rs = ps.executeQuery();

        dataDelete();
        while (rs.next()) {
            hasData = true;
            //Normal data
            int album_id = rs.getInt("album_id");
            int artist_id = rs.getInt("artist_id");
            String cover_url = rs.getString("cover_url");
            int duration_seconds = rs.getInt("duration_seconds");
            String file_url = rs.getString("file_url");
            int id = rs.getInt("id");
            long play_count = rs.getLong("play_count");
            String title = rs.getString("title");
            int track_number = rs.getInt("track_number");

            //Enum data
            String required_account_type = rs.getString("required_account_type");
            RequiredAccountType t = RequiredAccountType.valueOf(required_account_type);
            String status = rs.getString("status");
            Status s = Status.valueOf(status);

            Songs song = new Songs(
                    album_id,
                    artist_id,
                    cover_url,
                    duration_seconds,
                    file_url,
                    id,
                    play_count,
                    t,
                    s,
                    title,
                    track_number
            );
            songlist.add(song);
        }
        return hasData;
    }

    //Hiện hết toàn bộ songs trước khi ấn các nút filter
    public boolean viewAllSongs() throws SQLException {
        String sql = "SELECT * FROM songs WHERE status = ?";
        PreparedStatement ps = c.prepareStatement(sql);
        ps.setString(1, "LIVE");
        return dataAdd(ps);
    }

    //Sort theo play_count
    public boolean sortByPlayCount() throws SQLException {
        String sql = "SELECT * FROM songs WHERE status = ? ORDER BY play_count DESC";
        PreparedStatement ps = c.prepareStatement(sql);
        ps.setString(1, "LIVE");
        return dataAdd(ps);
    }

    //Filter theo artists
    public boolean filterByArtists(int artist_id) throws SQLException {
        String sql = "SELECT * FROM songs WHERE artist_id = ?";
        PreparedStatement ps = c.prepareStatement(sql);
        ps.setInt(1, artist_id);
        return dataAdd(ps);
    }

    //Filter theo liked
    public boolean filterByLiked(int user_id) throws SQLException {
        String sql = "SELECT s.id, s.artist_id, s.album_id, s.title, s.duration_seconds, s.file_url, s.cover_url, s.track_number, s.play_count, s.status, s.required_account_type FROM liked_songs ls INNER JOIN songs s ON ls.song_id = s.id WHERE ls.user_id = ? AND s.status = ?";
        PreparedStatement ps = c.prepareStatement(sql);
        ps.setInt(1, user_id);
        ps.setString(2, "LIVE");
        return dataAdd(ps);
    }
}
