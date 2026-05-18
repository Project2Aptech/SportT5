/* =====================================================
   USERS
===================================================== */

INSERT INTO users (
    username,
    email,
    password_hash,
    role,
    display_name,
    avatar_url,
    bio,
    birth_date,
    is_verified
)
VALUES
('admin1','admin1@sportt5.com','hash123','admin','Admin One','avatars/admin1.jpg','System administrator','1995-01-01',TRUE),
('artist1','artist1@sportt5.com','hash123','artist','Artist One','avatars/artist1.jpg','Pop artist','1998-02-10',TRUE),
('artist2','artist2@sportt5.com','hash123','artist','Artist Two','avatars/artist2.jpg','Rock artist','1997-03-12',TRUE),
('artist3','artist3@sportt5.com','hash123','artist','Artist Three','avatars/artist3.jpg','Jazz artist','1996-04-14',TRUE),
('user1','user1@sportt5.com','hash123','user','User One','avatars/user1.jpg','Music lover','2000-05-11',FALSE),
('user2','user2@sportt5.com','hash123','user','User Two','avatars/user2.jpg','Playlist collector','2001-06-21',FALSE),
('user3','user3@sportt5.com','hash123','user','User Three','avatars/user3.jpg','EDM fan','2002-07-15',FALSE),
('user4','user4@sportt5.com','hash123','user','User Four','avatars/user4.jpg','Chill music fan','1999-08-18',FALSE),
('artist4','artist4@sportt5.com','hash123','artist','Artist Four','avatars/artist4.jpg','Hip hop artist','1994-09-19',TRUE),
('artist5','artist5@sportt5.com','hash123','artist','Artist Five','avatars/artist5.jpg','Lo-fi producer','1993-10-20',TRUE);

/* =====================================================
   GENRES
===================================================== */

INSERT INTO genres (name, slug)
VALUES
('Pop','pop'),
('Rock','rock'),
('Jazz','jazz'),
('Hip Hop','hip-hop'),
('Lo-fi','lo-fi'),
('EDM','edm'),
('Classical','classical'),
('R&B','rnb'),
('Country','country'),
('Indie','indie');

/* =====================================================
   ALBUMS
===================================================== */

INSERT INTO albums (
    artist_id,
    title,
    cover_url,
    release_date
)
VALUES
(2,'Pop Dreams','covers/pop_dreams.jpg','2024-01-01'),
(3,'Rock Legends','covers/rock_legends.jpg','2024-01-10'),
(4,'Smooth Jazz','covers/smooth_jazz.jpg','2024-01-15'),
(9,'Hip Hop Streets','covers/hiphop_streets.jpg','2024-01-20'),
(10,'LoFi Nights','covers/lofi_nights.jpg','2024-01-25'),
(2,'Summer Pop','covers/summer_pop.jpg','2024-02-01'),
(3,'Metal Fire','covers/metal_fire.jpg','2024-02-10'),
(4,'Jazz Lounge','covers/jazz_lounge.jpg','2024-02-15'),
(9,'Rap World','covers/rap_world.jpg','2024-02-20'),
(10,'Chill Beats','covers/chill_beats.jpg','2024-02-25');

/* =====================================================
   SONGS
===================================================== */

INSERT INTO songs (
    artist_id,
    album_id,
    title,
    duration_seconds,
    file_url,
    cover_url,
    track_number,
    play_count,
    status
)
VALUES
(2,1,'Dream Light',210,'songs/dream_light.mp3','covers/dream_light.jpg',1,1200,'live'),
(2,1,'Sky Pop',180,'songs/sky_pop.mp3','covers/sky_pop.jpg',2,950,'live'),
(3,2,'Rock The Night',240,'songs/rock_night.mp3','covers/rock_night.jpg',1,2300,'live'),
(3,7,'Metal Heart',250,'songs/metal_heart.mp3','covers/metal_heart.jpg',1,1800,'live'),
(4,3,'Jazz Coffee',200,'songs/jazz_coffee.mp3','covers/jazz_coffee.jpg',1,890,'live'),
(4,8,'Blue Piano',260,'songs/blue_piano.mp3','covers/blue_piano.jpg',2,920,'live'),
(9,4,'Street Flow',230,'songs/street_flow.mp3','covers/street_flow.jpg',1,3100,'live'),
(9,9,'Rap King',210,'songs/rap_king.mp3','covers/rap_king.jpg',2,2800,'live'),
(10,5,'Night Coding',300,'songs/night_coding.mp3','covers/night_coding.jpg',1,4100,'live'),
(10,10,'Rain Study',320,'songs/rain_study.mp3','covers/rain_study.jpg',2,3900,'live');

/* =====================================================
   SONG GENRES
===================================================== */

INSERT INTO song_genres (song_id, genre_id)
VALUES
(1,1),
(2,1),
(3,2),
(4,2),
(5,3),
(6,3),
(7,4),
(8,4),
(9,5),
(10,5);

/* =====================================================
   PLAYLISTS
===================================================== */

INSERT INTO playlists (
    user_id,
    title,
    description,
    cover_url,
    is_public
)
VALUES
(5,'Morning Chill','Relax morning playlist','covers/p1.jpg',TRUE),
(6,'Workout Hits','Gym playlist','covers/p2.jpg',TRUE),
(7,'Late Night Coding','Coding music','covers/p3.jpg',TRUE),
(8,'Study Time','Focus playlist','covers/p4.jpg',TRUE),
(5,'Jazz Cafe','Cafe vibes','covers/p5.jpg',TRUE),
(6,'Rock Energy','Rock music','covers/p6.jpg',TRUE),
(7,'Pop Mood','Top pop songs','covers/p7.jpg',TRUE),
(8,'LoFi Sleep','Sleep music','covers/p8.jpg',TRUE),
(5,'Rap Party','Party playlist','covers/p9.jpg',TRUE),
(6,'Indie Relax','Relax indie songs','covers/p10.jpg',TRUE);

/* =====================================================
   PLAYLIST SONGS
===================================================== */

INSERT INTO playlist_songs (
    playlist_id,
    song_id
)
VALUES
(1,9),
(1,10),
(2,3),
(2,4),
(3,9),
(3,10),
(4,5),
(5,6),
(6,3),
(7,1);

/* =====================================================
   LIKED SONGS
===================================================== */

INSERT INTO liked_songs (
    user_id,
    song_id
)
VALUES
(5,1),
(5,3),
(6,4),
(6,7),
(7,9),
(7,10),
(8,5),
(8,6),
(5,9),
(6,10);

/* =====================================================
   PLAY HISTORY
===================================================== */

INSERT INTO play_history (
    user_id,
    song_id,
    seconds_played,
    device_type
)
VALUES
(5,1,210,'desktop'),
(5,2,180,'mobile'),
(6,3,240,'desktop'),
(6,4,250,'web'),
(7,5,200,'desktop'),
(7,6,260,'mobile'),
(8,7,230,'desktop'),
(8,8,210,'web'),
(5,9,300,'desktop'),
(6,10,320,'mobile');

/* =====================================================
   ARTIST FOLLOWS
===================================================== */

INSERT INTO artist_follows (
    user_id,
    artist_id
)
VALUES
(5,2),
(5,3),
(6,4),
(6,9),
(7,10),
(7,2),
(8,3),
(8,4),
(5,10),
(6,2);

/* =====================================================
   PLAYLIST FOLLOWS
===================================================== */

INSERT INTO playlist_follows (
    user_id,
    playlist_id
)
VALUES
(5,2),
(5,3),
(6,1),
(6,4),
(7,5),
(7,6),
(8,7),
(8,8),
(5,9),
(6,10);

/* =====================================================
   SONG COMMENTS
===================================================== */

INSERT INTO song_comments (
    song_id,
    user_id,
    parent_comment_id,
    content,
    is_deleted
)
VALUES
(1,5,NULL,'Amazing song!',FALSE),
(2,6,NULL,'Love this vibe',FALSE),
(3,7,NULL,'Rock is alive!',FALSE),
(4,8,NULL,'Great guitar solo',FALSE),
(5,5,NULL,'Smooth jazz track',FALSE),
(6,6,NULL,'Perfect cafe music',FALSE),
(7,7,NULL,'Rap flow is insane',FALSE),
(8,8,NULL,'Best hip hop beat',FALSE),
(9,5,NULL,'Coding with this song everyday',FALSE),
(10,6,NULL,'Very relaxing music',FALSE);