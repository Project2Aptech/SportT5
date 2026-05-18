/* =====================================================
   Spotify-like App Seed Data
   MySQL INSERT DATA - 100+ rows
   Note: Run this after creating all tables.
===================================================== */

/* =========================
   ROLES
========================= */

INSERT INTO roles (id, name) VALUES
                                 (1, 'admin'),
                                 (2, 'artist'),
                                 (3, 'listener'),
                                 (4, 'moderator');

/* =========================
   USERS
========================= */

INSERT INTO users (id, role_id, username, email, password_hash, display_name, avatar_url, birth_date, is_active) VALUES
                                                                                                                     (1, 1, 'admin_tuan', 'admin@sportt5.com', '$2y$10$adminhash', 'Tuan Admin', 'https://cdn.sportt5.com/avatar/admin.png', '1998-05-12', true),
                                                                                                                     (2, 2, 'minhbeats', 'minhbeats@sportt5.com', '$2y$10$hash001', 'Minh Beats', 'https://cdn.sportt5.com/avatar/minh.png', '1999-01-21', true),
                                                                                                                     (3, 2, 'linhvoice', 'linhvoice@sportt5.com', '$2y$10$hash002', 'Linh Voice', 'https://cdn.sportt5.com/avatar/linh.png', '2000-07-14', true),
                                                                                                                     (4, 2, 'kaiwave', 'kaiwave@sportt5.com', '$2y$10$hash003', 'Kai Wave', 'https://cdn.sportt5.com/avatar/kai.png', '1997-11-03', true),
                                                                                                                     (5, 2, 'soramusic', 'soramusic@sportt5.com', '$2y$10$hash004', 'Sora Music', 'https://cdn.sportt5.com/avatar/sora.png', '2001-04-25', true),
                                                                                                                     (6, 2, 'neonfox', 'neonfox@sportt5.com', '$2y$10$hash005', 'Neon Fox', 'https://cdn.sportt5.com/avatar/neon.png', '1996-09-08', true),
                                                                                                                     (7, 3, 'hieplistener', 'hiep@example.com', '$2y$10$hash006', 'Hiep Nguyen', 'https://cdn.sportt5.com/avatar/hiep.png', '2002-02-17', true),
                                                                                                                     (8, 3, 'annamusic', 'anna@example.com', '$2y$10$hash007', 'Anna Tran', 'https://cdn.sportt5.com/avatar/anna.png', '2003-12-09', true),
                                                                                                                     (9, 3, 'longplay', 'long@example.com', '$2y$10$hash008', 'Long Pham', 'https://cdn.sportt5.com/avatar/long.png', '2001-08-30', true),
                                                                                                                     (10, 3, 'maichill', 'mai@example.com', '$2y$10$hash009', 'Mai Chill', 'https://cdn.sportt5.com/avatar/mai.png', '2004-06-11', true),
                                                                                                                     (11, 3, 'duongbass', 'duong@example.com', '$2y$10$hash010', 'Duong Bass', 'https://cdn.sportt5.com/avatar/duong.png', '1999-10-19', true),
                                                                                                                     (12, 4, 'mod_khanh', 'moderator@sportt5.com', '$2y$10$modhash', 'Khanh Moderator', 'https://cdn.sportt5.com/avatar/mod.png', '1995-03-03', true);

/* =========================
   ARTISTS
========================= */

INSERT INTO artists (id, user_id, bio, is_verified) VALUES
                                                        (1, 2, 'Producer focused on chill hip-hop and lo-fi beats.', true),
                                                        (2, 3, 'Vietnamese pop vocalist with emotional acoustic songs.', true),
                                                        (3, 4, 'Electronic artist creating future bass and synthwave tracks.', false),
                                                        (4, 5, 'Indie songwriter mixing Japanese city pop and modern R&B.', false),
                                                        (5, 6, 'Experimental EDM producer with dark cinematic sound.', true);

/* =========================
   ARTIST DASHBOARD STATS
========================= */

INSERT INTO artist_dashboard_stats (id, artist_id, total_songs, total_albums, total_plays, total_followers, monthly_listeners) VALUES
                                                                                                                                   (1, 1, 6, 1, 24500, 1200, 4200),
                                                                                                                                   (2, 2, 6, 1, 38900, 2100, 6500),
                                                                                                                                   (3, 3, 6, 1, 17400, 800, 2600),
                                                                                                                                   (4, 4, 6, 1, 20100, 950, 3100),
                                                                                                                                   (5, 5, 6, 1, 45200, 2900, 7200);

/* =========================
   GENRES
========================= */

INSERT INTO genres (id, name, slug) VALUES
                                        (1, 'Pop', 'pop'),
                                        (2, 'Hip Hop', 'hip-hop'),
                                        (3, 'Lo-fi', 'lo-fi'),
                                        (4, 'EDM', 'edm'),
                                        (5, 'R&B', 'r-and-b'),
                                        (6, 'Rock', 'rock'),
                                        (7, 'Acoustic', 'acoustic'),
                                        (8, 'Synthwave', 'synthwave'),
                                        (9, 'City Pop', 'city-pop'),
                                        (10, 'Cinematic', 'cinematic');

/* =========================
   ALBUMS
========================= */

INSERT INTO albums (id, artist_id, title, cover_url, release_date, total_tracks, created_by, updated_by) VALUES
                                                                                                             (1, 1, 'Midnight Study Beats', 'https://cdn.sportt5.com/covers/midnight-study.jpg', '2025-01-10', 6, 2, 2),
                                                                                                             (2, 2, 'Rainy Letters', 'https://cdn.sportt5.com/covers/rainy-letters.jpg', '2025-02-14', 6, 3, 3),
                                                                                                             (3, 3, 'Future Neon City', 'https://cdn.sportt5.com/covers/future-neon.jpg', '2025-03-20', 6, 4, 4),
                                                                                                             (4, 4, 'Sakura After Dark', 'https://cdn.sportt5.com/covers/sakura-after-dark.jpg', '2025-04-05', 6, 5, 5),
                                                                                                             (5, 5, 'Black Thunder', 'https://cdn.sportt5.com/covers/black-thunder.jpg', '2025-05-01', 6, 6, 6);

/* =========================
   SONGS
========================= */

INSERT INTO songs (id, album_id, artist_id, title, duration_seconds, file_url, cover_url, track_number, play_count, status, created_by, updated_by) VALUES
                                                                                                                                                        (1, 1, 1, 'Night Desk', 162, 'https://cdn.sportt5.com/audio/night-desk.mp3', 'https://cdn.sportt5.com/covers/midnight-study.jpg', 1, 5200, 'live', 2, 2),
                                                                                                                                                        (2, 1, 1, 'Coffee Loop', 145, 'https://cdn.sportt5.com/audio/coffee-loop.mp3', 'https://cdn.sportt5.com/covers/midnight-study.jpg', 2, 4300, 'live', 2, 2),
                                                                                                                                                        (3, 1, 1, 'Silent Window', 188, 'https://cdn.sportt5.com/audio/silent-window.mp3', 'https://cdn.sportt5.com/covers/midnight-study.jpg', 3, 3900, 'live', 2, 2),
                                                                                                                                                        (4, 1, 1, 'Old Keyboard', 171, 'https://cdn.sportt5.com/audio/old-keyboard.mp3', 'https://cdn.sportt5.com/covers/midnight-study.jpg', 4, 2600, 'live', 2, 2),
                                                                                                                                                        (5, 1, 1, 'Blue Lamp', 155, 'https://cdn.sportt5.com/audio/blue-lamp.mp3', 'https://cdn.sportt5.com/covers/midnight-study.jpg', 5, 4700, 'live', 2, 2),
                                                                                                                                                        (6, 1, 1, 'Last Page', 201, 'https://cdn.sportt5.com/audio/last-page.mp3', 'https://cdn.sportt5.com/covers/midnight-study.jpg', 6, 3800, 'pending', 2, 2),

                                                                                                                                                        (7, 2, 2, 'Rain on My Phone', 214, 'https://cdn.sportt5.com/audio/rain-on-my-phone.mp3', 'https://cdn.sportt5.com/covers/rainy-letters.jpg', 1, 9800, 'live', 3, 3),
                                                                                                                                                        (8, 2, 2, 'Letter I Never Sent', 226, 'https://cdn.sportt5.com/audio/letter-never-sent.mp3', 'https://cdn.sportt5.com/covers/rainy-letters.jpg', 2, 8500, 'live', 3, 3),
                                                                                                                                                        (9, 2, 2, 'Falling Slowly Again', 205, 'https://cdn.sportt5.com/audio/falling-slowly-again.mp3', 'https://cdn.sportt5.com/covers/rainy-letters.jpg', 3, 7300, 'live', 3, 3),
                                                                                                                                                        (10, 2, 2, 'One More Goodbye', 239, 'https://cdn.sportt5.com/audio/one-more-goodbye.mp3', 'https://cdn.sportt5.com/covers/rainy-letters.jpg', 4, 6100, 'live', 3, 3),
                                                                                                                                                        (11, 2, 2, 'Empty Balcony', 198, 'https://cdn.sportt5.com/audio/empty-balcony.mp3', 'https://cdn.sportt5.com/covers/rainy-letters.jpg', 5, 4900, 'live', 3, 3),
                                                                                                                                                        (12, 2, 2, 'After the Storm', 217, 'https://cdn.sportt5.com/audio/after-the-storm.mp3', 'https://cdn.sportt5.com/covers/rainy-letters.jpg', 6, 2300, 'pending', 3, 3),

                                                                                                                                                        (13, 3, 3, 'Neon Highway', 192, 'https://cdn.sportt5.com/audio/neon-highway.mp3', 'https://cdn.sportt5.com/covers/future-neon.jpg', 1, 5400, 'live', 4, 4),
                                                                                                                                                        (14, 3, 3, 'Pixel Sunrise', 185, 'https://cdn.sportt5.com/audio/pixel-sunrise.mp3', 'https://cdn.sportt5.com/covers/future-neon.jpg', 2, 4200, 'live', 4, 4),
                                                                                                                                                        (15, 3, 3, 'Cyber Rain', 201, 'https://cdn.sportt5.com/audio/cyber-rain.mp3', 'https://cdn.sportt5.com/covers/future-neon.jpg', 3, 3100, 'live', 4, 4),
                                                                                                                                                        (16, 3, 3, 'Glitch Heart', 176, 'https://cdn.sportt5.com/audio/glitch-heart.mp3', 'https://cdn.sportt5.com/covers/future-neon.jpg', 4, 2900, 'live', 4, 4),
                                                                                                                                                        (17, 3, 3, 'Chrome Dreams', 209, 'https://cdn.sportt5.com/audio/chrome-dreams.mp3', 'https://cdn.sportt5.com/covers/future-neon.jpg', 5, 1800, 'live', 4, 4),
                                                                                                                                                        (18, 3, 3, 'Final Signal', 223, 'https://cdn.sportt5.com/audio/final-signal.mp3', 'https://cdn.sportt5.com/covers/future-neon.jpg', 6, 0, 'pending', 4, 4),

                                                                                                                                                        (19, 4, 4, 'Tokyo Moonlight', 212, 'https://cdn.sportt5.com/audio/tokyo-moonlight.mp3', 'https://cdn.sportt5.com/covers/sakura-after-dark.jpg', 1, 6500, 'live', 5, 5),
                                                                                                                                                        (20, 4, 4, 'Sakura Taxi', 193, 'https://cdn.sportt5.com/audio/sakura-taxi.mp3', 'https://cdn.sportt5.com/covers/sakura-after-dark.jpg', 2, 4100, 'live', 5, 5),
                                                                                                                                                        (21, 4, 4, 'Lost in Shibuya', 221, 'https://cdn.sportt5.com/audio/lost-in-shibuya.mp3', 'https://cdn.sportt5.com/covers/sakura-after-dark.jpg', 3, 3600, 'live', 5, 5),
                                                                                                                                                        (22, 4, 4, 'Pink Street', 184, 'https://cdn.sportt5.com/audio/pink-street.mp3', 'https://cdn.sportt5.com/covers/sakura-after-dark.jpg', 4, 2400, 'live', 5, 5),
                                                                                                                                                        (23, 4, 4, 'City Love Tape', 206, 'https://cdn.sportt5.com/audio/city-love-tape.mp3', 'https://cdn.sportt5.com/covers/sakura-after-dark.jpg', 5, 3000, 'live', 5, 5),
                                                                                                                                                        (24, 4, 4, 'Last Train Home', 231, 'https://cdn.sportt5.com/audio/last-train-home.mp3', 'https://cdn.sportt5.com/covers/sakura-after-dark.jpg', 6, 500, 'pending', 5, 5),

                                                                                                                                                        (25, 5, 5, 'Black Thunder Intro', 101, 'https://cdn.sportt5.com/audio/black-thunder-intro.mp3', 'https://cdn.sportt5.com/covers/black-thunder.jpg', 1, 7600, 'live', 6, 6),
                                                                                                                                                        (26, 5, 5, 'Storm Breaker', 219, 'https://cdn.sportt5.com/audio/storm-breaker.mp3', 'https://cdn.sportt5.com/covers/black-thunder.jpg', 2, 10500, 'live', 6, 6),
                                                                                                                                                        (27, 5, 5, 'Dark Festival', 244, 'https://cdn.sportt5.com/audio/dark-festival.mp3', 'https://cdn.sportt5.com/covers/black-thunder.jpg', 3, 8900, 'live', 6, 6),
                                                                                                                                                        (28, 5, 5, 'Electric Beast', 211, 'https://cdn.sportt5.com/audio/electric-beast.mp3', 'https://cdn.sportt5.com/covers/black-thunder.jpg', 4, 7200, 'live', 6, 6),
                                                                                                                                                        (29, 5, 5, 'Midnight Arena', 230, 'https://cdn.sportt5.com/audio/midnight-arena.mp3', 'https://cdn.sportt5.com/covers/black-thunder.jpg', 5, 6100, 'live', 6, 6),
                                                                                                                                                        (30, 5, 5, 'Thunder Outro', 154, 'https://cdn.sportt5.com/audio/thunder-outro.mp3', 'https://cdn.sportt5.com/covers/black-thunder.jpg', 6, 4900, 'live', 6, 6);

/* =========================
   SONG ARTISTS
========================= */

INSERT INTO song_artists (song_id, artist_id, role) VALUES
                                                        (1, 1, 'main'), (2, 1, 'main'), (3, 1, 'main'), (4, 1, 'main'), (5, 1, 'main'), (6, 1, 'main'),
                                                        (7, 2, 'main'), (8, 2, 'main'), (9, 2, 'main'), (10, 2, 'main'), (11, 2, 'main'), (12, 2, 'main'),
                                                        (13, 3, 'main'), (14, 3, 'main'), (15, 3, 'main'), (16, 3, 'main'), (17, 3, 'main'), (18, 3, 'main'),
                                                        (19, 4, 'main'), (20, 4, 'main'), (21, 4, 'main'), (22, 4, 'main'), (23, 4, 'main'), (24, 4, 'main'),
                                                        (25, 5, 'main'), (26, 5, 'main'), (27, 5, 'main'), (28, 5, 'main'), (29, 5, 'main'), (30, 5, 'main'),
                                                        (7, 1, 'producer'),
                                                        (13, 5, 'remixer'),
                                                        (19, 2, 'featured'),
                                                        (26, 3, 'producer'),
                                                        (28, 4, 'featured');

/* =========================
   SONG GENRES
========================= */

INSERT INTO song_genres (song_id, genre_id) VALUES
                                                (1, 3), (2, 3), (3, 3), (4, 3), (5, 3), (6, 3),
                                                (7, 1), (7, 7), (8, 1), (8, 7), (9, 1), (10, 1), (11, 7), (12, 1),
                                                (13, 4), (13, 8), (14, 8), (15, 4), (16, 4), (17, 8), (18, 4),
                                                (19, 9), (19, 5), (20, 9), (21, 9), (22, 5), (23, 9), (24, 7),
                                                (25, 10), (26, 4), (26, 10), (27, 4), (27, 10), (28, 4), (29, 10), (30, 10);

/* =========================
   PLAYLISTS
========================= */

INSERT INTO playlists (id, user_id, title, description, cover_url, is_public, total_songs) VALUES
                                                                                               (1, 7, 'My Chill Night', 'Songs for coding and late night editing.', 'https://cdn.sportt5.com/playlists/chill-night.jpg', true, 8),
                                                                                               (2, 8, 'Rainy Mood', 'Soft songs for rainy days.', 'https://cdn.sportt5.com/playlists/rainy-mood.jpg', true, 7),
                                                                                               (3, 9, 'Workout EDM', 'High energy EDM tracks.', 'https://cdn.sportt5.com/playlists/workout-edm.jpg', true, 6),
                                                                                               (4, 10, 'City Pop Drive', 'City pop and R&B for driving.', 'https://cdn.sportt5.com/playlists/city-pop-drive.jpg', true, 6),
                                                                                               (5, 11, 'Private Favorites', 'My private favorite songs.', 'https://cdn.sportt5.com/playlists/private-favorites.jpg', false, 5);

/* =========================
   PLAYLIST SONGS
========================= */

INSERT INTO playlist_songs (playlist_id, song_id, added_by) VALUES
                                                                (1, 1, 7), (1, 2, 7), (1, 3, 7), (1, 5, 7), (1, 7, 7), (1, 8, 7), (1, 19, 7), (1, 23, 7),
                                                                (2, 7, 8), (2, 8, 8), (2, 9, 8), (2, 10, 8), (2, 11, 8), (2, 12, 8), (2, 3, 8),
                                                                (3, 13, 9), (3, 15, 9), (3, 16, 9), (3, 26, 9), (3, 27, 9), (3, 28, 9),
                                                                (4, 19, 10), (4, 20, 10), (4, 21, 10), (4, 22, 10), (4, 23, 10), (4, 24, 10),
                                                                (5, 1, 11), (5, 8, 11), (5, 13, 11), (5, 19, 11), (5, 26, 11);

/* =========================
   LIKED SONGS
========================= */

INSERT INTO liked_songs (user_id, song_id) VALUES
                                               (7, 1), (7, 2), (7, 7), (7, 13), (7, 26),
                                               (8, 7), (8, 8), (8, 9), (8, 19), (8, 20),
                                               (9, 13), (9, 15), (9, 26), (9, 27), (9, 28),
                                               (10, 19), (10, 20), (10, 21), (10, 23), (10, 8),
                                               (11, 1), (11, 5), (11, 10), (11, 22), (11, 29);

/* =========================
   PLAY HISTORY
========================= */

INSERT INTO play_history (id, user_id, song_id, played_at, seconds_played, device_type) VALUES
                                                                                            (1, 7, 1, '2026-05-01 20:10:00', 162, 'desktop'),
                                                                                            (2, 7, 2, '2026-05-01 20:14:00', 145, 'desktop'),
                                                                                            (3, 7, 7, '2026-05-01 20:18:00', 210, 'desktop'),
                                                                                            (4, 7, 13, '2026-05-02 09:30:00', 180, 'mobile'),
                                                                                            (5, 7, 26, '2026-05-02 09:35:00', 219, 'mobile'),
                                                                                            (6, 8, 7, '2026-05-03 21:00:00', 214, 'mobile'),
                                                                                            (7, 8, 8, '2026-05-03 21:04:00', 226, 'mobile'),
                                                                                            (8, 8, 9, '2026-05-03 21:09:00', 205, 'mobile'),
                                                                                            (9, 8, 19, '2026-05-04 12:30:00', 212, 'web'),
                                                                                            (10, 8, 20, '2026-05-04 12:35:00', 193, 'web'),
                                                                                            (11, 9, 13, '2026-05-05 06:20:00', 192, 'mobile'),
                                                                                            (12, 9, 15, '2026-05-05 06:24:00', 201, 'mobile'),
                                                                                            (13, 9, 26, '2026-05-05 06:29:00', 219, 'mobile'),
                                                                                            (14, 9, 27, '2026-05-05 06:34:00', 244, 'mobile'),
                                                                                            (15, 9, 28, '2026-05-05 06:40:00', 211, 'mobile'),
                                                                                            (16, 10, 19, '2026-05-06 19:00:00', 212, 'tv'),
                                                                                            (17, 10, 20, '2026-05-06 19:04:00', 193, 'tv'),
                                                                                            (18, 10, 21, '2026-05-06 19:08:00', 221, 'tv'),
                                                                                            (19, 10, 23, '2026-05-06 19:13:00', 206, 'tv'),
                                                                                            (20, 11, 29, '2026-05-07 23:00:00', 230, 'desktop');

/* =========================
   USER LIBRARY
========================= */

INSERT INTO user_library_albums (user_id, album_id) VALUES
                                                        (7, 1), (7, 2),
                                                        (8, 2), (8, 4),
                                                        (9, 3), (9, 5),
                                                        (10, 4), (10, 2),
                                                        (11, 1), (11, 5);

INSERT INTO user_library_playlists (user_id, playlist_id) VALUES
                                                              (7, 2), (7, 3),
                                                              (8, 1), (8, 4),
                                                              (9, 1), (9, 5),
                                                              (10, 2), (10, 3),
                                                              (11, 1), (11, 4);

/* =========================
   FOLLOWS
========================= */

INSERT INTO user_playlist_follows (user_id, playlist_id) VALUES
                                                             (7, 2), (7, 3),
                                                             (8, 1), (8, 4),
                                                             (9, 1), (9, 5),
                                                             (10, 2), (10, 3),
                                                             (11, 1), (11, 4);

INSERT INTO user_artist_follows (user_id, artist_id) VALUES
                                                         (7, 1), (7, 2), (7, 5),
                                                         (8, 2), (8, 4),
                                                         (9, 3), (9, 5),
                                                         (10, 2), (10, 4),
                                                         (11, 1), (11, 5);

/* =========================
   SUBSCRIPTIONS
========================= */

INSERT INTO subscriptions (id, name, price, duration_days, description) VALUES
                                                                            (1, 'Free', 0.00, 30, 'Free plan with ads.'),
                                                                            (2, 'Premium Monthly', 59000.00, 30, 'Monthly premium plan without ads.'),
                                                                            (3, 'Premium Yearly', 590000.00, 365, 'Yearly premium plan with discount.');

INSERT INTO user_subscription_details (id, user_id, subscription_id, start_date, end_date, status) VALUES
                                                                                                       (1, 7, 2, '2026-05-01 00:00:00', '2026-05-31 23:59:59', 'active'),
                                                                                                       (2, 8, 1, '2026-05-01 00:00:00', '2026-05-31 23:59:59', 'active'),
                                                                                                       (3, 9, 3, '2026-01-01 00:00:00', '2026-12-31 23:59:59', 'active'),
                                                                                                       (4, 10, 2, '2026-04-15 00:00:00', '2026-05-15 23:59:59', 'expired'),
                                                                                                       (5, 11, 1, '2026-05-01 00:00:00', '2026-05-31 23:59:59', 'active');

/* =========================
   SONG COMMENTS
========================= */

INSERT INTO song_comments (id, song_id, user_id, parent_comment_id, content, is_deleted) VALUES
                                                                                             (1, 1, 7, NULL, 'Perfect track for editing videos at night.', false),
                                                                                             (2, 1, 8, 1, 'Same here, this beat is super clean.', false),
                                                                                             (3, 7, 9, NULL, 'The vocal is really emotional.', false),
                                                                                             (4, 8, 10, NULL, 'This should be on the trending page.', false),
                                                                                             (5, 13, 11, NULL, 'Love the synth sound in this one.', false),
                                                                                             (6, 19, 7, NULL, 'City pop vibe is amazing.', false),
                                                                                             (7, 26, 8, NULL, 'This drop is powerful.', false),
                                                                                             (8, 27, 9, NULL, 'Great for workout playlist.', false),
                                                                                             (9, 20, 10, NULL, 'Feels like driving in Tokyo at night.', false),
                                                                                             (10, 5, 11, NULL, 'Very relaxing loop.', false);

/* =========================
   NOTIFICATIONS
========================= */

INSERT INTO notifications (id, type, title, message, target_url, created_by) VALUES
                                                                                 (1, 'new_song', 'New song from Minh Beats', 'Night Desk is now available.', '/songs/1', 2),
                                                                                 (2, 'new_album', 'New album released', 'Rainy Letters by Linh Voice is out now.', '/albums/2', 3),
                                                                                 (3, 'new_song', 'New EDM track', 'Storm Breaker is now live.', '/songs/26', 6),
                                                                                 (4, 'comment_reply', 'Someone replied to your comment', 'Anna replied to your comment on Night Desk.', '/songs/1/comments/2', NULL),
                                                                                 (5, 'subscription', 'Premium expiring soon', 'Your Premium Monthly plan is ending soon.', '/account/subscription', 1),
                                                                                 (6, 'system', 'Welcome to Sport T5', 'Thanks for joining our music platform.', '/home', 1);

INSERT INTO user_notifications (id, user_id, notification_id, status, read_at) VALUES
                                                                                   (1, 7, 1, 'read', '2026-05-01 20:00:00'),
                                                                                   (2, 7, 2, 'unread', NULL),
                                                                                   (3, 7, 4, 'unread', NULL),
                                                                                   (4, 8, 1, 'read', '2026-05-02 08:00:00'),
                                                                                   (5, 8, 3, 'unread', NULL),
                                                                                   (6, 9, 3, 'read', '2026-05-05 06:00:00'),
                                                                                   (7, 9, 6, 'read', '2026-05-01 10:00:00'),
                                                                                   (8, 10, 2, 'unread', NULL),
                                                                                   (9, 10, 5, 'unread', NULL),
                                                                                   (10, 11, 1, 'read', '2026-05-03 14:00:00'),
                                                                                   (11, 11, 3, 'unread', NULL),
                                                                                   (12, 11, 6, 'read', '2026-05-01 09:00:00');
