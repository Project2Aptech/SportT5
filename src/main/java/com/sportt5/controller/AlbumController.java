package com.sportt5.controller;

import com.sportt5.model.Album;
import com.sportt5.model.Song;
import com.sportt5.service.AlbumService;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

import java.util.List;

/**
 * Controller for album.fxml.
 *
 * Data comes from {@link AlbumService} whose methods map directly to the DB:
 * <ul>
 *   <li>{@code albums}   — title, cover_url, release_date, total_tracks, artist_id</li>
 *   <li>{@code artists}  — joined to resolve artist display name via users.display_name</li>
 *   <li>{@code songs}    — title, duration_seconds, play_count, track_number,
 *                          cover_url, status (only 'live' songs are shown)</li>
 * </ul>
 *
 * Singles are songs where {@code album_id IS NULL}.
 */
public class AlbumController {

    // ── FXML containers ───────────────────────────────────────────────────────
    @FXML private HBox topAlbumsRow;
    @FXML private VBox latestAlbumsList;
    @FXML private VBox latestSinglesList;

    // ── Service ───────────────────────────────────────────────────────────────
    private final AlbumService albumService = new AlbumService();

    // ── Initialize ────────────────────────────────────────────────────────────
    @FXML
    public void initialize() {
        List<Album> topAlbums    = albumService.getTopAlbums(5);
        List<Album> latestAlbums = albumService.getLatestAlbums(5);
        List<Song>  latestSingles = albumService.getLatestSingles(5);

        buildTopAlbums(topAlbums);
        buildAlbumList(latestAlbumsList, latestAlbums);
        buildSinglesList(latestSinglesList, latestSingles);
    }

    // ── Top Albums row ────────────────────────────────────────────────────────

    private void buildTopAlbums(List<Album> albums) {
        topAlbumsRow.getChildren().clear();
        for (Album album : albums) {
            topAlbumsRow.getChildren().add(createAlbumCard(album));
        }
    }

    /**
     * Card shows: cover art (or placeholder), album title, artist display name.
     * Data source: albums.title, albums.cover_url, users.display_name (via artists join).
     */
    private VBox createAlbumCard(Album album) {
        StackPane thumb = buildAlbumThumbnail(album.getCoverUrl(), 150, 150, 36);

        Label nameLabel = new Label(album.getTitle());
        nameLabel.getStyleClass().add("album-name");
        nameLabel.setWrapText(true);
        nameLabel.setMaxWidth(150);

        // artistName is resolved from albums.artist_id → artists.user_id → users.display_name
        Label artistLabel = new Label(album.getArtistName());
        artistLabel.getStyleClass().add("album-artist");

        // Optional: show release year from albums.release_date
        String year = album.getReleaseDate() != null
                ? String.valueOf(album.getReleaseDate().getYear())
                : "";
        Label yearLabel = new Label(year);
        yearLabel.getStyleClass().add("album-year");

        VBox card = new VBox(8, thumb, nameLabel, artistLabel, yearLabel);
        card.getStyleClass().add("album-card");
        card.setMinWidth(174);
        card.setMaxWidth(174);

        return card;
    }

    // ── Latest Albums list ────────────────────────────────────────────────────

    private void buildAlbumList(VBox container, List<Album> albums) {
        container.getChildren().clear();
        for (int i = 0; i < albums.size(); i++) {
            container.getChildren().add(createAlbumRow(i + 1, albums.get(i)));
        }
    }

    /**
     * Row shows: rank, cover art, album title + artist name, total_tracks count.
     * Data source: albums.total_tracks, albums.title, users.display_name.
     */
    private HBox createAlbumRow(int rank, Album album) {
        Label rankLabel = new Label(String.format("%02d", rank));
        rankLabel.getStyleClass().add("track-rank");
        rankLabel.setMinWidth(24);
        rankLabel.setAlignment(Pos.CENTER);

        StackPane thumb = buildAlbumThumbnail(album.getCoverUrl(), 44, 44, 18);

        Label titleLabel = new Label(album.getTitle());
        titleLabel.getStyleClass().add("track-title");

        // artistName resolved via artists → users join
        Label artistLabel = new Label(album.getArtistName());
        artistLabel.getStyleClass().add("track-artist");

        VBox info = new VBox(3, titleLabel, artistLabel);
        HBox.setHgrow(info, Priority.ALWAYS);

        // albums.total_tracks
        Label tracksLabel = new Label(album.getTotalTracks() + " tracks");
        tracksLabel.getStyleClass().add("track-duration");

        Button saveBtn = new Button("♡");
        saveBtn.getStyleClass().add("icon-btn");
        saveBtn.setOnAction(e -> onSaveAlbum(album));

        Button moreBtn = new Button("•••");
        moreBtn.getStyleClass().add("icon-btn");

        HBox actions = new HBox(8, tracksLabel, saveBtn, moreBtn);
        actions.setAlignment(Pos.CENTER_RIGHT);

        HBox row = new HBox(12, rankLabel, thumb, info, actions);
        row.getStyleClass().add("track-row");
        row.setAlignment(Pos.CENTER_LEFT);

        return row;
    }

    // ── Latest Singles list ───────────────────────────────────────────────────

    private void buildSinglesList(VBox container, List<Song> songs) {
        container.getChildren().clear();
        for (int i = 0; i < songs.size(); i++) {
            container.getChildren().add(createSongRow(i + 1, songs.get(i)));
        }
    }

    /**
     * Row shows: rank, cover art, song title + artist name,
     *            formatted duration (from songs.duration_seconds), like button.
     *
     * Only songs with status = 'live' and album_id IS NULL are passed here
     * (filtering is done in {@link AlbumService#getLatestSingles}).
     */
    private HBox createSongRow(int rank, Song song) {
        Label rankLabel = new Label(String.format("%02d", rank));
        rankLabel.getStyleClass().add("track-rank");
        rankLabel.setMinWidth(24);
        rankLabel.setAlignment(Pos.CENTER);

        StackPane thumb = buildAlbumThumbnail(song.getCoverUrl(), 44, 44, 18);

        Label titleLabel = new Label(song.getTitle());
        titleLabel.getStyleClass().add("track-title");

        // artistName resolved via songs.artist_id → artists.user_id → users.display_name
        Label artistLabel = new Label(song.getArtistName());
        artistLabel.getStyleClass().add("track-artist");

        VBox info = new VBox(3, titleLabel, artistLabel);
        HBox.setHgrow(info, Priority.ALWAYS);

        // songs.duration_seconds formatted as mm:ss
        Label durationLabel = new Label(song.getFormattedDuration());
        durationLabel.getStyleClass().add("track-duration");

        Button likeBtn = new Button("♡");
        likeBtn.getStyleClass().add("icon-btn");
        likeBtn.setOnAction(e -> onLikeSong(song));

        Button moreBtn = new Button("•••");
        moreBtn.getStyleClass().add("icon-btn");

        HBox actions = new HBox(8, durationLabel, likeBtn, moreBtn);
        actions.setAlignment(Pos.CENTER_RIGHT);

        HBox row = new HBox(12, rankLabel, thumb, info, actions);
        row.getStyleClass().add("track-row");
        row.setAlignment(Pos.CENTER_LEFT);

        return row;
    }

    // ── Shared thumbnail builder ──────────────────────────────────────────────

    /**
     * Builds a StackPane thumbnail.
     * If {@code coverUrl} is non-null, loads the image (swap to DB-backed URL service).
     * Otherwise shows a musical note emoji placeholder.
     *
     * @param coverUrl    value of albums.cover_url or songs.cover_url (may be null)
     * @param w           thumbnail width in px
     * @param h           thumbnail height in px
     * @param emojiFontSz emoji font size when no image is available
     */
    private StackPane buildAlbumThumbnail(String coverUrl, double w, double h, int emojiFontSz) {
        StackPane pane = new StackPane();
        pane.setMinSize(w, h);
        pane.setMaxSize(w, h);
        pane.getStyleClass().add(w > 80 ? "album-thumb" : "track-thumb");

        if (coverUrl != null && !coverUrl.isBlank()) {
            try {
                ImageView iv = new ImageView(new Image(coverUrl, w, h, true, true));
                iv.setFitWidth(w);
                iv.setFitHeight(h);
                pane.getChildren().add(iv);
                return pane;
            } catch (Exception ignored) {
                // fall through to placeholder
            }
        }

        // Placeholder: coloured region + emoji
        Region placeholder = new Region();
        placeholder.setStyle("-fx-background-color: #2a2a3a; -fx-background-radius: 6;");
        placeholder.setMinSize(w, h);
        placeholder.setMaxSize(w, h);

        Label emoji = new Label("🎵");
        emoji.setStyle("-fx-font-size: " + emojiFontSz + "px;");

        pane.getChildren().addAll(placeholder, emoji);
        return pane;
    }

    // ── Action handlers ───────────────────────────────────────────────────────

    /**
     * Called when the user saves an album to their library.
     * Would insert into {@code user_library_albums (user_id, album_id)}.
     */
    private void onSaveAlbum(Album album) {
        System.out.println("Save album: " + album.getId() + " – " + album.getTitle());
        // TODO: call UserLibraryService.saveAlbum(currentUserId, album.getId())
    }

    /**
     * Called when the user likes a song.
     * Would insert into {@code liked_songs (user_id, song_id)}.
     */
    private void onLikeSong(Song song) {
        System.out.println("Like song: " + song.getId() + " – " + song.getTitle());
        // TODO: call LikedSongsService.likeSong(currentUserId, song.getId())
    }
}