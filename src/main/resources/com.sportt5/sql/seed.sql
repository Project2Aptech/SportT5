/* =====================================================
USERS
===================================================== */

INSERT INTO users (
    username,
    email,
    password_hash,
    role,
    account_type,
    display_name,
    avatar_url,
    bio,
    birth_date,
    is_active
)
VALUES
    ('admin1','admin1@sportt5.com','$2a$10$NJWg8R5zygzBWrOZlrA3TOw/rWr3ZUUSofU7GFLx5VL0iRv99DsOG','ADMIN','PREMIUM','Admin One','images/avatar.png','System administrator','1995-01-01',TRUE),

    ('artist1','artist1@sportt5.com','$2a$10$NJWg8R5zygzBWrOZlrA3TOw/rWr3ZUUSofU7GFLx5VL0iRv99DsOG','ARTIST','PRO','Artist One','images/artists/artist1.jpeg','Pop artist','1998-02-10',TRUE),

    ('artist2','artist2@sportt5.com','$2a$10$NJWg8R5zygzBWrOZlrA3TOw/rWr3ZUUSofU7GFLx5VL0iRv99DsOG','ARTIST','PREMIUM','Artist Two','images/artists/artist2.jpeg','Rock artist','1997-03-12',TRUE),

    ('artist3','artist3@sportt5.com','$2a$10$NJWg8R5zygzBWrOZlrA3TOw/rWr3ZUUSofU7GFLx5VL0iRv99DsOG','ARTIST','PRO','Artist Three','images/artists/artist3.jpeg','Jazz artist','1996-04-14',TRUE),

    ('user1','user1@sportt5.com','$2a$10$NJWg8R5zygzBWrOZlrA3TOw/rWr3ZUUSofU7GFLx5VL0iRv99DsOG','USER','NORMAL','User One','images/avatar.png','Music lover','2000-05-11',TRUE),

    ('user2','user2@sportt5.com','$2a$10$NJWg8R5zygzBWrOZlrA3TOw/rWr3ZUUSofU7GFLx5VL0iRv99DsOG','USER','PREMIUM','User Two','images/avatar.png','Playlist collector','2001-06-21',TRUE),

    ('user3','user3@sportt5.com','$2a$10$NJWg8R5zygzBWrOZlrA3TOw/rWr3ZUUSofU7GFLx5VL0iRv99DsOG','USER','NORMAL','User Three','images/avatar.png','EDM fan','2002-07-15',TRUE),

    ('user4','user4@sportt5.com','$2a$10$NJWg8R5zygzBWrOZlrA3TOw/rWr3ZUUSofU7GFLx5VL0iRv99DsOG','USER','PRO','User Four','images/avatar.png','Chill music fan','1999-08-18',TRUE),

    ('artist4','artist4@sportt5.com','$2a$10$NJWg8R5zygzBWrOZlrA3TOw/rWr3ZUUSofU7GFLx5VL0iRv99DsOG','ARTIST','PREMIUM','Artist Four','images/artists/artist4.jpeg','Hip hop artist','1994-09-19',TRUE),

    ('artist5','artist5@sportt5.com','$2a$10$NJWg8R5zygzBWrOZlrA3TOw/rWr3ZUUSofU7GFLx5VL0iRv99DsOG','ARTIST','PRO','Artist Five','images/artists/artist5.jpeg','Lo-fi producer','1993-10-20',TRUE),

    ('artist6','artist6@sportt5.com','$2a$10$NJWg8R5zygzBWrOZlrA3TOw/rWr3ZUUSofU7GFLx5VL0iRv99DsOG','ARTIST','NORMAL','Artist Six','images/artists/artist6.jpeg','Indie singer','1992-11-05',TRUE),

    ('artist7','artist7@sportt5.com','$2a$10$NJWg8R5zygzBWrOZlrA3TOw/rWr3ZUUSofU7GFLx5VL0iRv99DsOG','ARTIST','PRO','Artist Seven','images/artists/artist7.jpeg','Rap artist','1991-12-15',TRUE);

/* =====================================================
   GENRES
===================================================== */

INSERT INTO genres (name, slug)
VALUES
    ('Pop','pop'),
    ('Rock','rock'),
    ('Jazz','jazz'),
    ('Hip Hop','hip-hop'),
    ('LoFi','lofi'),
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
    (2,'Pop Dreams','images/albums/album1.jpeg','2024-01-01'),
    (3,'Rock Legends','images/albums/album2.jpeg','2024-01-10'),
    (4,'Smooth Jazz','images/albums/album3.jpeg','2024-01-15'),
    (9,'Hip Hop Streets','images/albums/album4.jpeg','2024-01-20'),
    (10,'LoFi Nights','images/albums/album5.jpeg','2024-01-25'),
    (2,'Summer Pop','images/albums/album6.jpeg','2024-02-01'),
    (3,'Metal Fire','images/albums/album7.jpeg','2024-02-10'),
    (4,'Jazz Lounge','images/albums/album8.jpeg','2024-02-15'),
    (9,'Rap World','images/albums/album9.jpeg','2024-02-20'),
    (10,'Chill Beats','images/albums/album10.jpeg','2024-02-25'),
    (11,'Indie Souls','images/albums/album11.jpeg','2024-03-01'),
    (12,'Street Rap','images/albums/album12.jpeg','2024-03-10');

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
    status,
    required_account_type
)
VALUES
    (
        1,
        1,
        'E Là Không Thể',
        210,
        'songs/e-la-khong-the.mp3',
        'images/songs/song1.jpeg',
        1,
        1200,
        'LIVE',
        'NORMAL'
    ),

    (
        1,
        1,
        'Chiều Thu Hoa Bóng Nắng',
        205,
        'songs/chieu-thu-hoa-bong-nang.mp3',
        'images/songs/song2.jpeg',
        2,
        1800,
        'LIVE',
        'NORMAL'
    ),

    (
        1,
        1,
        'Đông Phai Mờ Dáng Ai',
        240,
        'songs/dong-phai-mo-dang-ai.mp3',
        'images/songs/song3.jpeg',
        3,
        1500,
        'LIVE',
        'NORMAL'
    ),

    (
        2,
        2,
        'Hoa Nở Không Màu',
        250,
        'songs/hoa-no-khong-mau.mp3',
        'images/songs/song4.jpeg',
        1,
        3500,
        'LIVE',
        'PRO'
    ),

    (
        2,
        2,
        'Cay',
        200,
        'songs/cay.mp3',
        'images/songs/song5.jpeg',
        2,
        900,
        'LIVE',
        'NORMAL'
    ),

    (
        3,
        3,
        'Hôm Nay Em Cưới Rồi',
        230,
        'songs/hom-nay-em-cuoi-roi.mp3',
        'images/songs/song6.jpeg',
        1,
        1700,
        'LIVE',
        'NORMAL'
    ),

    (
        3,
        3,
        'Lời Tâm Sự Số 3',
        260,
        'songs/loi-tam-su-so-3.mp3',
        'images/songs/song7.jpeg',
        2,
        1100,
        'LIVE',
        'NORMAL'
    ),

    (
        4,
        4,
        'Bước Qua Đời Nhau',
        240,
        'songs/buoc-qua-doi-nhau.mp3',
        'images/songs/song8.jpeg',
        1,
        4200,
        'LIVE',
        'PRO'
    ),

    (
        4,
        4,
        'Lá Xa Lìa Cành',
        220,
        'songs/la-xa-lia-canh.mp3',
        'images/songs/song9.jpeg',
        2,
        3900,
        'LIVE',
        'NORMAL'
    ),

    (
        4,
        4,
        'Thích Thì Đến',
        210,
        'songs/thich-thi-den.mp3',
        'images/songs/song10.jpeg',
        3,
        2500,
        'LIVE',
        'NORMAL'
    ),

    (
        5,
        5,
        'Bài Này Chill Phết',
        230,
        'songs/bai-nay-chill-phet.mp3',
        'images/songs/song11.jpeg',
        1,
        5100,
        'LIVE',
        'PREMIUM'
    ),

    (
        5,
        5,
        'Già Vợ Yếu',
        215,
        'songs/gia-vo-yeu.mp3',
        'images/songs/song12.jpeg',
        2,
        1300,
        'LIVE',
        'NORMAL'
    ),

    (
        6,
        6,
        'Sau Lời Từ Khước',
        260,
        'songs/sau-loi-tu-khuoc.mp3',
        'images/songs/song13.jpeg',
        1,
        3000,
        'LIVE',
        'PRO'
    ),

    (
        6,
        6,
        'Họ Chưa Từng Sai',
        250,
        'songs/ho-chua-tung-sai.mp3',
        'images/songs/song14.jpeg',
        2,
        2800,
        'LIVE',
        'NORMAL'
    ),

    (
        7,
        7,
        'Anh Đã Không Biết Cách Yêu Em',
        235,
        'songs/anh-da-khong-biet-cach-yeu-em.mp3',
        'images/songs/song15.jpeg',
        1,
        1400,
        'LIVE',
        'NORMAL'
    ),

    (
        7,
        7,
        'Còn Gì Đau Hơn Chữ Đã Từng',
        245,
        'songs/con-gi-dau-hon-chu-da-tung.mp3',
        'images/songs/song16.jpeg',
        2,
        3600,
        'LIVE',
        'PRO'
    ),

    (
        8,
        8,
        'Ai Là Người Thương Em',
        220,
        'songs/ai-la-nguoi-thuong-em.mp3',
        'images/songs/song17.jpeg',
        1,
        4100,
        'LIVE',
        'NORMAL'
    ),

    (
        8,
        8,
        'Âm Thầm Bên Em',
        255,
        'songs/am-tham-ben-em.mp3',
        'images/songs/song18.jpeg',
        2,
        8000,
        'LIVE',
        'PREMIUM'
    ),

    (
        9,
        9,
        'Ngày Mai Người Ta Lấy Chồng',
        240,
        'songs/ngay-mai-nguoi-ta-lay-chong.mp3',
        'images/songs/song19.jpeg',
        1,
        4600,
        'LIVE',
        'PRO'
    ),

    (
        9,
        9,
        'Tình Yêu Hoa Gió',
        215,
        'songs/tinh-yeu-hoa-gio.mp3',
        'covers/song20.jpg',
        2,
        900,
        'LIVE',
        'NORMAL'
    ),

    (
        10,
        10,
        'Vạn Lý Sầu',
        225,
        'songs/van-ly-sau.mp3',
        'covers/song21.jpg',
        1,
        6200,
        'LIVE',
        'PREMIUM'
    ),

    (
        10,
        10,
        'Đà Lạt Cơn Mưa Không Em',
        245,
        'songs/da-lat-con-mua-khong-em.mp3',
        'covers/song22.jpg',
        2,
        3100,
        'LIVE',
        'PRO'
    ),

    (
        11,
        11,
        'Tái Sinh',
        250,
        'songs/tai-sinh.mp3',
        'covers/song23.jpg',
        1,
        1500,
        'LIVE',
        'NORMAL'
    ),

    (
        11,
        11,
        'Ngày Mai Em Đi Mất',
        235,
        'songs/ngay-mai-em-di-mat.mp3',
        'covers/song24.jpg',
        2,
        1700,
        'LIVE',
        'NORMAL'
    ),

    (
        12,
        12,
        'Lối Nhỏ',
        210,
        'songs/loi-nho.mp3',
        'covers/song25.jpg',
        1,
        9500,
        'LIVE',
        'PREMIUM'
    ),

    (
        12,
        12,
        'Đế Vương',
        260,
        'songs/de-vuong.mp3',
        'covers/song26.jpg',
        2,
        1200,
        'LIVE',
        'NORMAL'
    );

/* =====================================================
   SONG GENRES
===================================================== */

INSERT INTO song_genres (
    song_id,
    genre_id
)
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
    (5,'Morning Chill','Relax morning playlist','images/playlists/playlist1.jpeg',TRUE),

    (6,'Workout Hits','Gym playlist','images/playlists/playlist2.jpeg',TRUE),

    (7,'Late Night Coding','Coding playlist','images/playlists/playlist3.jpeg',TRUE),

    (8,'Study Time','Study playlist','images/playlists/playlist4.jpeg',TRUE),

    (5,'Jazz Cafe','Cafe vibes','images/playlists/playlist5.jpeg',TRUE),

    (6,'Rock Energy','Rock playlist','images/playlists/playlist6.jpeg',TRUE),

    (7,'Pop Mood','Pop playlist','images/playlists/playlist7.jpeg',TRUE),

    (8,'LoFi Sleep','Sleep playlist','images/playlists/playlist8.jpeg',TRUE),

    (5,'Rap Party','Party playlist','images/playlists/playlist9.jpeg',TRUE),

    (6,'Indie Relax','Relax indie songs','images/playlists/playlist10.jpeg',TRUE);

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

/* =====================================================
   SUBSCRIPTIONS
   plan_type: PRO=$9.99  PREMIUM=$19.99
   6 tháng: 2023-10 → 2024-03
   Paying users: artist1(2) PRO, artist2(3) PREMIUM,
                 artist3(4) PRO,  user2(6)   PREMIUM,
                 user4(8)   PRO,  artist4(9) PREMIUM,
                 artist5(10) PRO
===================================================== */

INSERT INTO subscriptions (user_id, plan_type, amount, started_at, expires_at, status) VALUES
-- artist1 (id=2) — PRO
(2,'PRO', 9.99,'2023-10-01','2023-10-31','EXPIRED'),
(2,'PRO', 9.99,'2023-11-01','2023-11-30','EXPIRED'),
(2,'PRO', 9.99,'2023-12-01','2023-12-31','EXPIRED'),
(2,'PRO', 9.99,'2024-01-01','2024-01-31','EXPIRED'),
(2,'PRO', 9.99,'2024-02-01','2024-02-29','EXPIRED'),
(2,'PRO', 9.99,'2024-03-01','2024-03-31','ACTIVE'),
-- artist2 (id=3) — PREMIUM
(3,'PREMIUM',19.99,'2023-10-01','2023-10-31','EXPIRED'),
(3,'PREMIUM',19.99,'2023-11-01','2023-11-30','EXPIRED'),
(3,'PREMIUM',19.99,'2023-12-01','2023-12-31','EXPIRED'),
(3,'PREMIUM',19.99,'2024-01-01','2024-01-31','EXPIRED'),
(3,'PREMIUM',19.99,'2024-02-01','2024-02-29','EXPIRED'),
(3,'PREMIUM',19.99,'2024-03-01','2024-03-31','ACTIVE'),
-- artist3 (id=4) — PRO
(4,'PRO', 9.99,'2023-10-01','2023-10-31','EXPIRED'),
(4,'PRO', 9.99,'2023-11-01','2023-11-30','EXPIRED'),
(4,'PRO', 9.99,'2023-12-01','2023-12-31','EXPIRED'),
(4,'PRO', 9.99,'2024-01-01','2024-01-31','EXPIRED'),
(4,'PRO', 9.99,'2024-02-01','2024-02-29','EXPIRED'),
(4,'PRO', 9.99,'2024-03-01','2024-03-31','ACTIVE'),
-- user2 (id=6) — PREMIUM
(6,'PREMIUM',19.99,'2023-10-01','2023-10-31','EXPIRED'),
(6,'PREMIUM',19.99,'2023-11-01','2023-11-30','EXPIRED'),
(6,'PREMIUM',19.99,'2023-12-01','2023-12-31','EXPIRED'),
(6,'PREMIUM',19.99,'2024-01-01','2024-01-31','EXPIRED'),
(6,'PREMIUM',19.99,'2024-02-01','2024-02-29','EXPIRED'),
(6,'PREMIUM',19.99,'2024-03-01','2024-03-31','ACTIVE'),
-- user4 (id=8) — PRO
(8,'PRO', 9.99,'2023-10-01','2023-10-31','EXPIRED'),
(8,'PRO', 9.99,'2023-11-01','2023-11-30','EXPIRED'),
(8,'PRO', 9.99,'2023-12-01','2023-12-31','EXPIRED'),
(8,'PRO', 9.99,'2024-01-01','2024-01-31','EXPIRED'),
(8,'PRO', 9.99,'2024-02-01','2024-02-29','EXPIRED'),
(8,'PRO', 9.99,'2024-03-01','2024-03-31','ACTIVE'),
-- artist4 (id=9) — PREMIUM
(9,'PREMIUM',19.99,'2023-10-01','2023-10-31','EXPIRED'),
(9,'PREMIUM',19.99,'2023-11-01','2023-11-30','EXPIRED'),
(9,'PREMIUM',19.99,'2023-12-01','2023-12-31','EXPIRED'),
(9,'PREMIUM',19.99,'2024-01-01','2024-01-31','EXPIRED'),
(9,'PREMIUM',19.99,'2024-02-01','2024-02-29','EXPIRED'),
(9,'PREMIUM',19.99,'2024-03-01','2024-03-31','ACTIVE'),
-- artist5 (id=10) — PRO
(10,'PRO', 9.99,'2023-10-01','2023-10-31','EXPIRED'),
(10,'PRO', 9.99,'2023-11-01','2023-11-30','EXPIRED'),
(10,'PRO', 9.99,'2023-12-01','2023-12-31','EXPIRED'),
(10,'PRO', 9.99,'2024-01-01','2024-01-31','EXPIRED'),
(10,'PRO', 9.99,'2024-02-01','2024-02-29','EXPIRED'),
(10,'PRO', 9.99,'2024-03-01','2024-03-31','ACTIVE');

/* =====================================================
   ARTIST EARNINGS
   6 tháng: 2023-10 → 2024-03
   Tỉ lệ: ~$0.004 / stream
   Mỗi tháng stream_count tăng dần ~5-10%
===================================================== */

INSERT INTO artist_earnings (artist_id, period_start, period_end, stream_count, amount) VALUES
-- artist1 (id=2) — Pop
(2,'2023-10-01','2023-10-31', 42000, 168.00),
(2,'2023-11-01','2023-11-30', 45000, 180.00),
(2,'2023-12-01','2023-12-31', 49000, 196.00),
(2,'2024-01-01','2024-01-31', 52000, 208.00),
(2,'2024-02-01','2024-02-29', 56000, 224.00),
(2,'2024-03-01','2024-03-31', 61000, 244.00),
-- artist2 (id=3) — Rock
(3,'2023-10-01','2023-10-31', 33000, 132.00),
(3,'2023-11-01','2023-11-30', 35000, 140.00),
(3,'2023-12-01','2023-12-31', 37500, 150.00),
(3,'2024-01-01','2024-01-31', 40000, 160.00),
(3,'2024-02-01','2024-02-29', 43000, 172.00),
(3,'2024-03-01','2024-03-31', 46500, 186.00),
-- artist3 (id=4) — Jazz
(4,'2023-10-01','2023-10-31', 58000, 232.00),
(4,'2023-11-01','2023-11-30', 62000, 248.00),
(4,'2023-12-01','2023-12-31', 67000, 268.00),
(4,'2024-01-01','2024-01-31', 71000, 284.00),
(4,'2024-02-01','2024-02-29', 76000, 304.00),
(4,'2024-03-01','2024-03-31', 82000, 328.00),
-- artist4 (id=9) — Hip Hop
(9,'2023-10-01','2023-10-31', 71000, 284.00),
(9,'2023-11-01','2023-11-30', 76000, 304.00),
(9,'2023-12-01','2023-12-31', 82000, 328.00),
(9,'2024-01-01','2024-01-31', 88000, 352.00),
(9,'2024-02-01','2024-02-29', 95000, 380.00),
(9,'2024-03-01','2024-03-31',103000, 412.00),
-- artist5 (id=10) — LoFi
(10,'2023-10-01','2023-10-31', 88000, 352.00),
(10,'2023-11-01','2023-11-30', 94000, 376.00),
(10,'2023-12-01','2023-12-31',101000, 404.00),
(10,'2024-01-01','2024-01-31',108000, 432.00),
(10,'2024-02-01','2024-02-29',116000, 464.00),
(10,'2024-03-01','2024-03-31',125000, 500.00);
