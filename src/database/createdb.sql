/* =====================================================
   Spotify-like App Database Schema
   MySQL CREATE DATABASE + CREATE TABLE
   Run this file BEFORE seed data.
===================================================== */

DROP DATABASE IF EXISTS spotify_app;
CREATE DATABASE spotify_app
  CHARACTER SET utf8mb4
  COLLATE utf8mb4_unicode_ci;

USE spotify_app;

/* =========================
   ROLES
========================= */

CREATE TABLE roles (
  id INT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(50) NOT NULL UNIQUE
) ENGINE=InnoDB;

/* =========================
   USERS
========================= */

CREATE TABLE users (
  id INT AUTO_INCREMENT PRIMARY KEY,
  role_id INT NOT NULL,

  username VARCHAR(50) NOT NULL UNIQUE,
  email VARCHAR(255) NOT NULL UNIQUE,
  password_hash VARCHAR(255) NOT NULL,

  display_name VARCHAR(255),
  avatar_url VARCHAR(500),
  birth_date DATE,

  is_active BOOLEAN NOT NULL DEFAULT TRUE,

  created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

  INDEX idx_email (email),
  INDEX idx_username (username),
  INDEX idx_role_id (role_id),

  CONSTRAINT fk_users_role
    FOREIGN KEY (role_id) REFERENCES roles(id)
    ON DELETE RESTRICT
) ENGINE=InnoDB;

/* =========================
   ARTISTS
========================= */

CREATE TABLE artists (
  id INT AUTO_INCREMENT PRIMARY KEY,
  user_id INT NOT NULL UNIQUE,

  bio TEXT,
  is_verified BOOLEAN NOT NULL DEFAULT FALSE,

  created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

  INDEX idx_user_id (user_id),

  CONSTRAINT fk_artists_user
    FOREIGN KEY (user_id) REFERENCES users(id)
    ON DELETE CASCADE
) ENGINE=InnoDB;

/* =========================
   ARTIST DASHBOARD STATS
========================= */

CREATE TABLE artist_dashboard_stats (
  id INT AUTO_INCREMENT PRIMARY KEY,
  artist_id INT NOT NULL UNIQUE,

  total_songs INT NOT NULL DEFAULT 0,
  total_albums INT NOT NULL DEFAULT 0,
  total_plays BIGINT NOT NULL DEFAULT 0,
  total_followers INT NOT NULL DEFAULT 0,
  monthly_listeners INT NOT NULL DEFAULT 0,

  updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

  INDEX idx_artist_id (artist_id),

  CONSTRAINT fk_artist_dashboard_stats_artist
    FOREIGN KEY (artist_id) REFERENCES artists(id)
    ON DELETE CASCADE
) ENGINE=InnoDB;

/* =========================
   GENRES
========================= */

CREATE TABLE genres (
  id INT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(100) NOT NULL UNIQUE,
  slug VARCHAR(100) NOT NULL UNIQUE
) ENGINE=InnoDB;

/* =========================
   ALBUMS
========================= */

CREATE TABLE albums (
  id INT AUTO_INCREMENT PRIMARY KEY,
  artist_id INT NOT NULL,

  title VARCHAR(255) NOT NULL,
  cover_url VARCHAR(500),
  release_date DATE,
  total_tracks SMALLINT NOT NULL DEFAULT 0,

  created_by INT,
  updated_by INT,

  created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

  INDEX idx_artist_id (artist_id),
  INDEX idx_release_date (release_date),
  INDEX idx_created_by (created_by),
  INDEX idx_updated_by (updated_by),
  UNIQUE INDEX uq_artist_album_title (artist_id, title),

  CONSTRAINT fk_albums_artist
    FOREIGN KEY (artist_id) REFERENCES artists(id)
    ON DELETE CASCADE,

  CONSTRAINT fk_albums_created_by
    FOREIGN KEY (created_by) REFERENCES users(id)
    ON DELETE SET NULL,

  CONSTRAINT fk_albums_updated_by
    FOREIGN KEY (updated_by) REFERENCES users(id)
    ON DELETE SET NULL
) ENGINE=InnoDB;

/* =========================
   SONGS
========================= */

CREATE TABLE songs (
  id INT AUTO_INCREMENT PRIMARY KEY,
  album_id INT,
  artist_id INT NOT NULL,

  title VARCHAR(255) NOT NULL,
  duration_seconds SMALLINT NOT NULL DEFAULT 0,

  file_url VARCHAR(500) NOT NULL,
  cover_url VARCHAR(500),
  track_number SMALLINT,

  play_count BIGINT NOT NULL DEFAULT 0,

  status ENUM('live', 'pending', 'deleted') NOT NULL DEFAULT 'pending',

  created_by INT,
  updated_by INT,

  created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

  INDEX idx_album_id (album_id),
  INDEX idx_artist_id (artist_id),
  INDEX idx_play_count (play_count),
  INDEX idx_status (status),
  INDEX idx_created_by (created_by),
  INDEX idx_updated_by (updated_by),
  UNIQUE INDEX uq_album_track (album_id, track_number),

  CONSTRAINT fk_songs_album
    FOREIGN KEY (album_id) REFERENCES albums(id)
    ON DELETE SET NULL,

  CONSTRAINT fk_songs_artist
    FOREIGN KEY (artist_id) REFERENCES artists(id)
    ON DELETE CASCADE,

  CONSTRAINT fk_songs_created_by
    FOREIGN KEY (created_by) REFERENCES users(id)
    ON DELETE SET NULL,

  CONSTRAINT fk_songs_updated_by
    FOREIGN KEY (updated_by) REFERENCES users(id)
    ON DELETE SET NULL
) ENGINE=InnoDB;

/* =========================
   SONG ARTISTS
========================= */

CREATE TABLE song_artists (
  song_id INT NOT NULL,
  artist_id INT NOT NULL,
  role ENUM('main', 'featured', 'producer', 'remixer') NOT NULL DEFAULT 'featured',

  PRIMARY KEY (song_id, artist_id),
  INDEX idx_artist_id (artist_id),

  CONSTRAINT fk_song_artists_song
    FOREIGN KEY (song_id) REFERENCES songs(id)
    ON DELETE CASCADE,

  CONSTRAINT fk_song_artists_artist
    FOREIGN KEY (artist_id) REFERENCES artists(id)
    ON DELETE CASCADE
) ENGINE=InnoDB;

/* =========================
   SONG GENRES
========================= */

CREATE TABLE song_genres (
  song_id INT NOT NULL,
  genre_id INT NOT NULL,

  PRIMARY KEY (song_id, genre_id),
  INDEX idx_genre_id (genre_id),

  CONSTRAINT fk_song_genres_song
    FOREIGN KEY (song_id) REFERENCES songs(id)
    ON DELETE CASCADE,

  CONSTRAINT fk_song_genres_genre
    FOREIGN KEY (genre_id) REFERENCES genres(id)
    ON DELETE CASCADE
) ENGINE=InnoDB;

/* =========================
   PLAYLISTS
========================= */

CREATE TABLE playlists (
  id INT AUTO_INCREMENT PRIMARY KEY,
  user_id INT NOT NULL,

  title VARCHAR(255) NOT NULL,
  description TEXT,
  cover_url VARCHAR(500),

  is_public BOOLEAN NOT NULL DEFAULT TRUE,
  total_songs SMALLINT NOT NULL DEFAULT 0,

  created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

  INDEX idx_user_id (user_id),
  INDEX idx_is_public (is_public),

  CONSTRAINT fk_playlists_user
    FOREIGN KEY (user_id) REFERENCES users(id)
    ON DELETE CASCADE
) ENGINE=InnoDB;

/* =========================
   PLAYLIST SONGS
========================= */

CREATE TABLE playlist_songs (
  playlist_id INT NOT NULL,
  song_id INT NOT NULL,
  added_by INT,

  added_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,

  PRIMARY KEY (playlist_id, song_id),
  INDEX idx_song_id (song_id),
  INDEX idx_added_by (added_by),

  CONSTRAINT fk_playlist_songs_playlist
    FOREIGN KEY (playlist_id) REFERENCES playlists(id)
    ON DELETE CASCADE,

  CONSTRAINT fk_playlist_songs_song
    FOREIGN KEY (song_id) REFERENCES songs(id)
    ON DELETE CASCADE,

  CONSTRAINT fk_playlist_songs_added_by
    FOREIGN KEY (added_by) REFERENCES users(id)
    ON DELETE SET NULL
) ENGINE=InnoDB;

/* =========================
   LIKED SONGS
========================= */

CREATE TABLE liked_songs (
  user_id INT NOT NULL,
  song_id INT NOT NULL,

  liked_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,

  PRIMARY KEY (user_id, song_id),
  INDEX idx_song_id (song_id),

  CONSTRAINT fk_liked_songs_user
    FOREIGN KEY (user_id) REFERENCES users(id)
    ON DELETE CASCADE,

  CONSTRAINT fk_liked_songs_song
    FOREIGN KEY (song_id) REFERENCES songs(id)
    ON DELETE CASCADE
) ENGINE=InnoDB;

/* =========================
   PLAY HISTORY
========================= */

CREATE TABLE play_history (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  user_id INT NOT NULL,
  song_id INT NOT NULL,

  played_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  seconds_played SMALLINT NOT NULL DEFAULT 0,
  device_type ENUM('desktop', 'mobile', 'web', 'tv'),

  INDEX idx_user_played (user_id, played_at),
  INDEX idx_song_id (song_id),

  CONSTRAINT fk_play_history_user
    FOREIGN KEY (user_id) REFERENCES users(id)
    ON DELETE CASCADE,

  CONSTRAINT fk_play_history_song
    FOREIGN KEY (song_id) REFERENCES songs(id)
    ON DELETE CASCADE
) ENGINE=InnoDB;

/* =========================
   USER LIBRARY ALBUMS
========================= */

CREATE TABLE user_library_albums (
  user_id INT NOT NULL,
  album_id INT NOT NULL,

  saved_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,

  PRIMARY KEY (user_id, album_id),
  INDEX idx_album_id (album_id),

  CONSTRAINT fk_user_library_albums_user
    FOREIGN KEY (user_id) REFERENCES users(id)
    ON DELETE CASCADE,

  CONSTRAINT fk_user_library_albums_album
    FOREIGN KEY (album_id) REFERENCES albums(id)
    ON DELETE CASCADE
) ENGINE=InnoDB;

/* =========================
   USER LIBRARY PLAYLISTS
========================= */

CREATE TABLE user_library_playlists (
  user_id INT NOT NULL,
  playlist_id INT NOT NULL,

  saved_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,

  PRIMARY KEY (user_id, playlist_id),
  INDEX idx_playlist_id (playlist_id),

  CONSTRAINT fk_user_library_playlists_user
    FOREIGN KEY (user_id) REFERENCES users(id)
    ON DELETE CASCADE,

  CONSTRAINT fk_user_library_playlists_playlist
    FOREIGN KEY (playlist_id) REFERENCES playlists(id)
    ON DELETE CASCADE
) ENGINE=InnoDB;

/* =========================
   PLAYLIST FOLLOWS
========================= */

CREATE TABLE user_playlist_follows (
  user_id INT NOT NULL,
  playlist_id INT NOT NULL,

  followed_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,

  PRIMARY KEY (user_id, playlist_id),
  INDEX idx_playlist_id (playlist_id),

  CONSTRAINT fk_user_playlist_follows_user
    FOREIGN KEY (user_id) REFERENCES users(id)
    ON DELETE CASCADE,

  CONSTRAINT fk_user_playlist_follows_playlist
    FOREIGN KEY (playlist_id) REFERENCES playlists(id)
    ON DELETE CASCADE
) ENGINE=InnoDB;

/* =========================
   ARTIST FOLLOWS
========================= */

CREATE TABLE user_artist_follows (
  user_id INT NOT NULL,
  artist_id INT NOT NULL,

  followed_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,

  PRIMARY KEY (user_id, artist_id),
  INDEX idx_artist_id (artist_id),

  CONSTRAINT fk_user_artist_follows_user
    FOREIGN KEY (user_id) REFERENCES users(id)
    ON DELETE CASCADE,

  CONSTRAINT fk_user_artist_follows_artist
    FOREIGN KEY (artist_id) REFERENCES artists(id)
    ON DELETE CASCADE
) ENGINE=InnoDB;

/* =========================
   SUBSCRIPTIONS
========================= */

CREATE TABLE subscriptions (
  id INT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(100) NOT NULL UNIQUE,
  price DECIMAL(10,2) NOT NULL DEFAULT 0,
  duration_days INT NOT NULL,
  description TEXT
) ENGINE=InnoDB;

CREATE TABLE user_subscription_details (
  id INT AUTO_INCREMENT PRIMARY KEY,
  user_id INT NOT NULL,
  subscription_id INT NOT NULL,

  start_date DATETIME NOT NULL,
  end_date DATETIME,

  status VARCHAR(50) NOT NULL DEFAULT 'active',

  created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

  INDEX idx_user_id (user_id),
  INDEX idx_subscription_id (subscription_id),
  INDEX idx_status (status),

  CONSTRAINT fk_user_subscription_details_user
    FOREIGN KEY (user_id) REFERENCES users(id)
    ON DELETE CASCADE,

  CONSTRAINT fk_user_subscription_details_subscription
    FOREIGN KEY (subscription_id) REFERENCES subscriptions(id)
    ON DELETE RESTRICT
) ENGINE=InnoDB;

/* =========================
   SONG COMMENTS
========================= */

CREATE TABLE song_comments (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  song_id INT NOT NULL,
  user_id INT NOT NULL,
  parent_comment_id BIGINT,

  content TEXT NOT NULL,
  is_deleted BOOLEAN NOT NULL DEFAULT FALSE,

  created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

  INDEX idx_song_id (song_id),
  INDEX idx_user_id (user_id),
  INDEX idx_parent_comment_id (parent_comment_id),

  CONSTRAINT fk_song_comments_song
    FOREIGN KEY (song_id) REFERENCES songs(id)
    ON DELETE CASCADE,

  CONSTRAINT fk_song_comments_user
    FOREIGN KEY (user_id) REFERENCES users(id)
    ON DELETE CASCADE,

  CONSTRAINT fk_song_comments_parent
    FOREIGN KEY (parent_comment_id) REFERENCES song_comments(id)
    ON DELETE SET NULL
) ENGINE=InnoDB;

/* =========================
   NOTIFICATIONS
========================= */

CREATE TABLE notifications (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,

  type ENUM('new_song', 'new_album', 'comment_reply', 'playlist_follow', 'subscription', 'system') NOT NULL,

  title VARCHAR(255) NOT NULL,
  message TEXT,
  target_url VARCHAR(500),

  created_by INT,
  created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,

  INDEX idx_type (type),
  INDEX idx_created_by (created_by),

  CONSTRAINT fk_notifications_created_by
    FOREIGN KEY (created_by) REFERENCES users(id)
    ON DELETE SET NULL
) ENGINE=InnoDB;

CREATE TABLE user_notifications (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,

  user_id INT NOT NULL,
  notification_id BIGINT NOT NULL,

  status ENUM('unread', 'read') NOT NULL DEFAULT 'unread',

  read_at DATETIME,
  created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,

  INDEX idx_user_id (user_id),
  INDEX idx_notification_id (notification_id),
  INDEX idx_status (status),
  UNIQUE INDEX uq_user_notification (user_id, notification_id),

  CONSTRAINT fk_user_notifications_user
    FOREIGN KEY (user_id) REFERENCES users(id)
    ON DELETE CASCADE,

  CONSTRAINT fk_user_notifications_notification
    FOREIGN KEY (notification_id) REFERENCES notifications(id)
    ON DELETE CASCADE
) ENGINE=InnoDB;
