/* =====================================================
   SportT5 Database Schema
   JavaFX + JDBC + MySQL
===================================================== */

DROP DATABASE IF EXISTS sportt5;

CREATE DATABASE sportt5
CHARACTER SET utf8mb4
COLLATE utf8mb4_unicode_ci;

USE sportt5;

/* =====================================================
   USERS
===================================================== */

CREATE TABLE users (
                       id INT AUTO_INCREMENT PRIMARY KEY,

                       username VARCHAR(50) NOT NULL UNIQUE,
                       email VARCHAR(255) NOT NULL UNIQUE,
                       password_hash VARCHAR(255) NOT NULL,

                       role ENUM('USER', 'ARTIST', 'ADMIN')
        NOT NULL DEFAULT 'USER',

                       account_type ENUM('NORMAL', 'PRO', 'PREMIUM')
        NOT NULL DEFAULT 'NORMAL',

                       display_name VARCHAR(255),
                       avatar_url VARCHAR(500),
                       bio TEXT,

                       birth_date DATE,

                       is_active BOOLEAN NOT NULL DEFAULT TRUE,

                       created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
                       updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
                           ON UPDATE CURRENT_TIMESTAMP
);

/* =====================================================
   GENRES
===================================================== */

CREATE TABLE genres (
                        id INT AUTO_INCREMENT PRIMARY KEY,

                        name VARCHAR(100) NOT NULL UNIQUE,
                        slug VARCHAR(100) NOT NULL UNIQUE
);

/* =====================================================
   ALBUMS
===================================================== */

CREATE TABLE albums (
                        id INT AUTO_INCREMENT PRIMARY KEY,

                        artist_id INT NOT NULL,

                        title VARCHAR(255) NOT NULL,
                        cover_url VARCHAR(500),

                        release_date DATE,

                        created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
                        updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
                            ON UPDATE CURRENT_TIMESTAMP,

                        FOREIGN KEY (artist_id)
                            REFERENCES users(id)
                            ON DELETE CASCADE
);

/* =====================================================
   SONGS
===================================================== */

CREATE TABLE songs (
                       id INT AUTO_INCREMENT PRIMARY KEY,

                       artist_id INT NOT NULL,
                       album_id INT,

                       title VARCHAR(255) NOT NULL,

                       duration_seconds SMALLINT NOT NULL DEFAULT 0,

                       file_url VARCHAR(500) NOT NULL,
                       cover_url VARCHAR(500),

                       track_number SMALLINT,

                       play_count BIGINT NOT NULL DEFAULT 0,

                       status ENUM('LIVE', 'PENDING', 'DELETED')
        NOT NULL DEFAULT 'LIVE',

                       required_account_type ENUM('NORMAL', 'PRO', 'PREMIUM')
        NOT NULL DEFAULT 'NORMAL',

                       created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
                       updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
                           ON UPDATE CURRENT_TIMESTAMP,

                       FOREIGN KEY (artist_id)
                           REFERENCES users(id)
                           ON DELETE CASCADE,

                       FOREIGN KEY (album_id)
                           REFERENCES albums(id)
                           ON DELETE SET NULL
);

/* =====================================================
   SONG GENRES
===================================================== */

CREATE TABLE song_genres (
                             song_id INT NOT NULL,
                             genre_id INT NOT NULL,

                             PRIMARY KEY (song_id, genre_id),

                             FOREIGN KEY (song_id)
                                 REFERENCES songs(id)
                                 ON DELETE CASCADE,

                             FOREIGN KEY (genre_id)
                                 REFERENCES genres(id)
                                 ON DELETE CASCADE
);

/* =====================================================
   PLAYLISTS
===================================================== */

CREATE TABLE playlists (
                           id INT AUTO_INCREMENT PRIMARY KEY,

                           user_id INT NOT NULL,

                           title VARCHAR(255) NOT NULL,
                           description TEXT,

                           cover_url VARCHAR(500),

                           is_public BOOLEAN NOT NULL DEFAULT TRUE,

                           created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
                           updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
                               ON UPDATE CURRENT_TIMESTAMP,

                           FOREIGN KEY (user_id)
                               REFERENCES users(id)
                               ON DELETE CASCADE
);

/* =====================================================
   PLAYLIST SONGS
===================================================== */

CREATE TABLE playlist_songs (
                                playlist_id INT NOT NULL,
                                song_id INT NOT NULL,

                                added_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,

                                PRIMARY KEY (playlist_id, song_id),

                                FOREIGN KEY (playlist_id)
                                    REFERENCES playlists(id)
                                    ON DELETE CASCADE,

                                FOREIGN KEY (song_id)
                                    REFERENCES songs(id)
                                    ON DELETE CASCADE
);

/* =====================================================
   LIKED SONGS
===================================================== */

CREATE TABLE liked_songs (
                             user_id INT NOT NULL,
                             song_id INT NOT NULL,

                             liked_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,

                             PRIMARY KEY (user_id, song_id),

                             FOREIGN KEY (user_id)
                                 REFERENCES users(id)
                                 ON DELETE CASCADE,

                             FOREIGN KEY (song_id)
                                 REFERENCES songs(id)
                                 ON DELETE CASCADE
);

/* =====================================================
   PLAY HISTORY
===================================================== */

CREATE TABLE play_history (
                              id BIGINT AUTO_INCREMENT PRIMARY KEY,

                              user_id INT NOT NULL,
                              song_id INT NOT NULL,

                              played_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,

                              seconds_played SMALLINT NOT NULL DEFAULT 0,

                              device_type ENUM('DESKTOP', 'MOBILE', 'WEB')
        DEFAULT 'DESKTOP',

                              FOREIGN KEY (user_id)
                                  REFERENCES users(id)
                                  ON DELETE CASCADE,

                              FOREIGN KEY (song_id)
                                  REFERENCES songs(id)
                                  ON DELETE CASCADE
);

/* =====================================================
   ARTIST FOLLOWS
===================================================== */

CREATE TABLE artist_follows (
                                user_id INT NOT NULL,
                                artist_id INT NOT NULL,

                                followed_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,

                                PRIMARY KEY (user_id, artist_id),

                                FOREIGN KEY (user_id)
                                    REFERENCES users(id)
                                    ON DELETE CASCADE,

                                FOREIGN KEY (artist_id)
                                    REFERENCES users(id)
                                    ON DELETE CASCADE
);

/* =====================================================
   PLAYLIST FOLLOWS
===================================================== */

CREATE TABLE playlist_follows (
                                  user_id INT NOT NULL,
                                  playlist_id INT NOT NULL,

                                  followed_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,

                                  PRIMARY KEY (user_id, playlist_id),

                                  FOREIGN KEY (user_id)
                                      REFERENCES users(id)
                                      ON DELETE CASCADE,

                                  FOREIGN KEY (playlist_id)
                                      REFERENCES playlists(id)
                                      ON DELETE CASCADE
);

/* =====================================================
   SONG COMMENTS
===================================================== */

CREATE TABLE song_comments (
                               id BIGINT AUTO_INCREMENT PRIMARY KEY,

                               song_id INT NOT NULL,
                               user_id INT NOT NULL,

                               parent_comment_id BIGINT,

                               content TEXT NOT NULL,

                               is_deleted BOOLEAN NOT NULL DEFAULT FALSE,

                               created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,

                               FOREIGN KEY (song_id)
                                   REFERENCES songs(id)
                                   ON DELETE CASCADE,

                               FOREIGN KEY (user_id)
                                   REFERENCES users(id)
                                   ON DELETE CASCADE,

                               FOREIGN KEY (parent_comment_id)
                                   REFERENCES song_comments(id)
                                   ON DELETE SET NULL
);