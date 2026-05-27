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
                       passwordHash VARCHAR(255) NOT NULL,

                       role ENUM('USER', 'ARTIST', 'ADMIN')
        NOT NULL DEFAULT 'USER',

                       accountType ENUM('NORMAL', 'PRO', 'PREMIUM')
        NOT NULL DEFAULT 'NORMAL',

                       displayName VARCHAR(255),
                       avatarUrl VARCHAR(500),
                       bio TEXT,

                       birthDate DATE,

                       isActive BOOLEAN NOT NULL DEFAULT TRUE,

                       createdAt DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
                       updatedAt DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
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

                        artistId INT NOT NULL,

                        title VARCHAR(255) NOT NULL,
                        coverUrl VARCHAR(500),

                        releaseDate DATE,

                        createdAt DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
                        updatedAt DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
                            ON UPDATE CURRENT_TIMESTAMP,

                        FOREIGN KEY (artistId)
                            REFERENCES users(id)
                            ON DELETE CASCADE
);

/* =====================================================
   SONGS
===================================================== */

CREATE TABLE songs (
                       id INT AUTO_INCREMENT PRIMARY KEY,

                       artistId INT NOT NULL,
                       albumId INT,

                       title VARCHAR(255) NOT NULL,

                       durationSeconds SMALLINT NOT NULL DEFAULT 0,

                       fileUrl VARCHAR(500) NOT NULL,
                       coverUrl VARCHAR(500),

                       trackNumber SMALLINT,

                       playCount BIGINT NOT NULL DEFAULT 0,

                       status ENUM('LIVE', 'PENDING', 'DELETED')
        NOT NULL DEFAULT 'LIVE',

                       requiredAccountType ENUM('NORMAL', 'PRO', 'PREMIUM')
        NOT NULL DEFAULT 'NORMAL',

                       createdAt DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
                       updatedAt DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
                           ON UPDATE CURRENT_TIMESTAMP,

                       FOREIGN KEY (artistId)
                           REFERENCES users(id)
                           ON DELETE CASCADE,

                       FOREIGN KEY (albumId)
                           REFERENCES albums(id)
                           ON DELETE SET NULL
);

/* =====================================================
   SONG GENRES
===================================================== */

CREATE TABLE songGenres (
                             songId INT NOT NULL,
                             genreId INT NOT NULL,

                             PRIMARY KEY (songId, genreId),

                             FOREIGN KEY (songId)
                                 REFERENCES songs(id)
                                 ON DELETE CASCADE,

                             FOREIGN KEY (genreId)
                                 REFERENCES genres(id)
                                 ON DELETE CASCADE
);

/* =====================================================
   PLAYLISTS
===================================================== */

CREATE TABLE playlists (
                           id INT AUTO_INCREMENT PRIMARY KEY,

                           userId INT NOT NULL,

                           title VARCHAR(255) NOT NULL,
                           description TEXT,

                           coverUrl VARCHAR(500),

                           isPublic BOOLEAN NOT NULL DEFAULT TRUE,

                           createdAt DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
                           updatedAt DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
                               ON UPDATE CURRENT_TIMESTAMP,

                           FOREIGN KEY (userId)
                               REFERENCES users(id)
                               ON DELETE CASCADE
);

/* =====================================================
   PLAYLIST SONGS
===================================================== */

CREATE TABLE playlistSongs (
                                playlistId INT NOT NULL,
                                songId INT NOT NULL,

                                addedAt DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,

                                PRIMARY KEY (playlistId, songId),

                                FOREIGN KEY (playlistId)
                                    REFERENCES playlists(id)
                                    ON DELETE CASCADE,

                                FOREIGN KEY (songId)
                                    REFERENCES songs(id)
                                    ON DELETE CASCADE
);

/* =====================================================
   LIKED SONGS
===================================================== */

CREATE TABLE likedSongs (
                             userId INT NOT NULL,
                             songId INT NOT NULL,

                             likedAt DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,

                             PRIMARY KEY (userId, songId),

                             FOREIGN KEY (userId)
                                 REFERENCES users(id)
                                 ON DELETE CASCADE,

                             FOREIGN KEY (songId)
                                 REFERENCES songs(id)
                                 ON DELETE CASCADE
);

/* =====================================================
   PLAY HISTORY
===================================================== */

CREATE TABLE playHistory (
                              id BIGINT AUTO_INCREMENT PRIMARY KEY,

                              userId INT NOT NULL,
                              songId INT NOT NULL,

                              playedAt DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,

                              secondsPlayed SMALLINT NOT NULL DEFAULT 0,

                              deviceType ENUM('DESKTOP', 'MOBILE', 'WEB')
        DEFAULT 'DESKTOP',

                              FOREIGN KEY (userId)
                                  REFERENCES users(id)
                                  ON DELETE CASCADE,

                              FOREIGN KEY (songId)
                                  REFERENCES songs(id)
                                  ON DELETE CASCADE
);

/* =====================================================
   ARTIST FOLLOWS
===================================================== */

CREATE TABLE artistFollows (
                                userId INT NOT NULL,
                                artistId INT NOT NULL,

                                followedAt DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,

                                PRIMARY KEY (userId, artistId),

                                FOREIGN KEY (userId)
                                    REFERENCES users(id)
                                    ON DELETE CASCADE,

                                FOREIGN KEY (artistId)
                                    REFERENCES users(id)
                                    ON DELETE CASCADE
);

/* =====================================================
   PLAYLIST FOLLOWS
===================================================== */

CREATE TABLE playlistFollows (
                                  userId INT NOT NULL,
                                  playlistId INT NOT NULL,

                                  followedAt DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,

                                  PRIMARY KEY (userId, playlistId),

                                  FOREIGN KEY (userId)
                                      REFERENCES users(id)
                                      ON DELETE CASCADE,

                                  FOREIGN KEY (playlistId)
                                      REFERENCES playlists(id)
                                      ON DELETE CASCADE
);

/* =====================================================
   SUBSCRIPTIONS
===================================================== */

CREATE TABLE subscriptions (
    id         INT AUTO_INCREMENT PRIMARY KEY,

    userId     INT    NOT NULL,

    planType   ENUM('PRO', 'PREMIUM') NOT NULL,

    amount     DECIMAL(10,2) NOT NULL,

    startedAt  DATETIME NOT NULL,
    expiresAt  DATETIME NOT NULL,

    status     ENUM('ACTIVE', 'EXPIRED', 'CANCELLED')
               NOT NULL DEFAULT 'ACTIVE',

    createdAt  DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,

    FOREIGN KEY (userId)
        REFERENCES users(id)
        ON DELETE CASCADE
);

/* =====================================================
   ARTIST EARNINGS
===================================================== */

CREATE TABLE artistEarnings (
    id           INT AUTO_INCREMENT PRIMARY KEY,

    artistId     INT  NOT NULL,

    periodStart  DATE NOT NULL,
    periodEnd    DATE NOT NULL,

    streamCount  INT          NOT NULL DEFAULT 0,
    amount       DECIMAL(10,2) NOT NULL DEFAULT 0.00,

    createdAt    DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,

    UNIQUE KEY uqArtistPeriod (artistId, periodStart),

    FOREIGN KEY (artistId)
        REFERENCES users(id)
        ON DELETE CASCADE
);

/* =====================================================
   SONG COMMENTS
===================================================== */

CREATE TABLE songComments (
                               id BIGINT AUTO_INCREMENT PRIMARY KEY,

                               songId INT NOT NULL,
                               userId INT NOT NULL,

                               parentCommentId BIGINT,

                               content TEXT NOT NULL,

                               isDeleted BOOLEAN NOT NULL DEFAULT FALSE,

                               createdAt DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,

                               FOREIGN KEY (songId)
                                   REFERENCES songs(id)
                                   ON DELETE CASCADE,

                               FOREIGN KEY (userId)
                                   REFERENCES users(id)
                                   ON DELETE CASCADE,

                               FOREIGN KEY (parentCommentId)
                                   REFERENCES songComments(id)
                                   ON DELETE SET NULL
);
