package com.sportt5.service;

import com.sportt5.model.Album;
import com.sportt5.model.Song;
import com.sportt5.model.Song.SongArtistEntry;
import com.sportt5.model.Song.SongArtistEntry.Role;
import com.sportt5.model.Song.SongStatus;

import java.time.LocalDate;
import java.util.List;

/**
 * Service layer for album & song data.
 *
 * Each method is a stub that returns mock data matching the DB schema.
 * Replace the method bodies with real JDBC / repository calls when ready.
 *
 * SQL hints are included as comments above each method so the query
 * structure is immediately obvious when wiring up a real data source.
 */
public class AlbumService {

    // ── Top Albums ────────────────────────────────────────────────────────────
    /**
     * Returns the top N albums ordered by aggregate play_count of their songs.
     *
     * SQL (example):
     *   SELECT a.id, a.artist_id, a.title, a.cover_url, a.release_date,
     *          a.total_tracks, u.display_name AS artist_name,
     *          SUM(s.play_count) AS total_plays
     *   FROM albums a
     *   JOIN artists ar ON ar.id = a.artist_id
     *   JOIN users  u  ON u.id  = ar.user_id
     *   LEFT JOIN songs s ON s.album_id = a.id AND s.status = 'live'
     *   GROUP BY a.id
     *   ORDER BY total_plays DESC
     *   LIMIT :limit
     */
    public List<Album> getTopAlbums(int limit) {
        return List.of(
                album(1, 1, "Electric Dreams",     "Neon Circuit",     null, LocalDate.of(2024, 3, 15), 10),
                album(2, 2, "Midnight City",       "Echo Wave",        null, LocalDate.of(2024, 1, 22), 8),
                album(3, 3, "Pulse Velocity",      "Vibe Tribe",       null, LocalDate.of(2023, 11, 5), 12),
                album(4, 4, "Zero Gravity",        "Orbit Collective", null, LocalDate.of(2024, 6, 1),  9),
                album(5, 5, "Distorted Realities", "Fragment",         null, LocalDate.of(2023, 8, 19), 7)
        );
    }

    // ── Latest Albums ─────────────────────────────────────────────────────────
    /**
     * Returns the N most recently released albums (excludes singles: total_tracks > 1).
     *
     * SQL (example):
     *   SELECT a.id, a.artist_id, a.title, a.cover_url, a.release_date,
     *          a.total_tracks, u.display_name AS artist_name
     *   FROM albums a
     *   JOIN artists ar ON ar.id = a.artist_id
     *   JOIN users  u  ON u.id  = ar.user_id
     *   WHERE a.total_tracks > 1
     *   ORDER BY a.release_date DESC
     *   LIMIT :limit
     */
    public List<Album> getLatestAlbums(int limit) {
        return List.of(
                album(6,  6,  "Random Access Memories", "Daft Punk",       null, LocalDate.of(2013, 5, 17), 13),
                album(7,  7,  "Glow On",                "Turnstile",       null, LocalDate.of(2021, 8, 27), 10),
                album(8,  8,  "Dopethrone",             "Electric Wizard", null, LocalDate.of(2000, 10, 1), 9),
                album(9,  6,  "Discovery",              "Daft Punk",       null, LocalDate.of(2001, 3, 12), 14),
                album(10, 9,  "Demon Days",             "Gorillaz",        null, LocalDate.of(2005, 5, 23), 15)
        );
    }

    // ── Latest Singles ────────────────────────────────────────────────────────
    /**
     * Returns live songs that are NOT linked to an album (album_id IS NULL),
     * ordered by created_at DESC – these are standalone singles.
     *
     * SQL (example):
     *   SELECT s.id, s.album_id, s.artist_id, s.title, s.duration_seconds,
     *          s.file_url, s.cover_url, s.track_number, s.play_count,
     *          s.status, u.display_name AS artist_name
     *   FROM songs s
     *   JOIN artists ar ON ar.id = s.artist_id
     *   JOIN users  u  ON u.id  = ar.user_id
     *   WHERE s.album_id IS NULL
     *     AND s.status = 'live'
     *   ORDER BY s.created_at DESC
     *   LIMIT :limit
     */
    public List<Song> getLatestSingles(int limit) {
        return List.of(
                song(100, null, 9,  "Feel Good Inc.",  "Gorillaz",   null, 225, 9_800_000L),
                song(101, null, 6,  "Get Lucky",       "Daft Punk",  null, 248, 14_200_000L),
                song(102, null, 10, "Ace of Spades",   "Motörhead",  null, 170, 7_500_000L),
                song(103, null, 11, "Blinding Lights", "The Weeknd", null, 200, 22_000_000L),
                song(104, null, 12, "Levitating",      "Dua Lipa",   null, 203, 18_300_000L)
        );
    }

    // ── Songs for a specific album ────────────────────────────────────────────
    /**
     * Returns all live songs belonging to the given album, ordered by track_number.
     *
     * SQL (example):
     *   SELECT s.id, s.album_id, s.artist_id, s.title, s.duration_seconds,
     *          s.file_url, s.cover_url, s.track_number, s.play_count,
     *          s.status, u.display_name AS artist_name
     *   FROM songs s
     *   JOIN artists ar ON ar.id = s.artist_id
     *   JOIN users  u  ON u.id  = ar.user_id
     *   WHERE s.album_id = :albumId
     *     AND s.status = 'live'
     *   ORDER BY s.track_number ASC
     */
    public List<Song> getSongsForAlbum(int albumId) {
        // Stub – return empty; replace with real query
        return List.of();
    }

    // ── Private helpers ───────────────────────────────────────────────────────

    private Album album(int id, int artistId, String title, String artistName,
                        String coverUrl, LocalDate releaseDate, int totalTracks) {
        return new Album(id, artistId, title, coverUrl, releaseDate, totalTracks, artistName);
    }

    private Song song(int id, Integer albumId, int artistId, String title,
                      String artistName, String coverUrl,
                      int durationSeconds, long playCount) {
        return new Song(id, albumId, artistId, title,
                durationSeconds, "", coverUrl,
                null, playCount, SongStatus.live, artistName);
    }
}