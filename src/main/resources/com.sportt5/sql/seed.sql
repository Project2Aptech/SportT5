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
    ('admin1','admin1@sportt5.com','123456','ADMIN','PREMIUM','Admin One','avatars/admin1.jpg','System administrator','1995-01-01',TRUE),

    ('artist1','artist1@sportt5.com','123456','ARTIST','PRO','Artist One','avatars/artist1.jpg','Pop artist','1998-02-10',TRUE),

    ('artist2','artist2@sportt5.com','123456','ARTIST','PREMIUM','Artist Two','avatars/artist2.jpg','Rock artist','1997-03-12',TRUE),

    ('artist3','artist3@sportt5.com','123456','ARTIST','PRO','Artist Three','avatars/artist3.jpg','Jazz artist','1996-04-14',TRUE),

    ('user1','user1@sportt5.com','123456','USER','NORMAL','User One','avatars/user1.jpg','Music lover','2000-05-11',TRUE),

    ('user2','user2@sportt5.com','123456','USER','PREMIUM','User Two','avatars/user2.jpg','Playlist collector','2001-06-21',TRUE),

    ('user3','user3@sportt5.com','123456','USER','NORMAL','User Three','avatars/user3.jpg','EDM fan','2002-07-15',TRUE),

    ('user4','user4@sportt5.com','123456','USER','PRO','User Four','avatars/user4.jpg','Chill music fan','1999-08-18',TRUE),

    ('artist4','artist4@sportt5.com','123456','ARTIST','PREMIUM','Artist Four','avatars/artist4.jpg','Hip hop artist','1994-09-19',TRUE),

    ('artist5','artist5@sportt5.com','123456','ARTIST','PRO','Artist Five','avatars/artist5.jpg','Lo-fi producer','1993-10-20',TRUE);

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

/* =====================================================
   SONGS (Đã sửa sạch lỗi cú pháp)
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
        'songs/Anh_Quân_Idol_-_E_Là_Không_Thể.mp3',
        'covers/song1.jpg',
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
        'songs/DatKaa_-_Chiều_Thu_Hoa_Bóng_Nắng.mp3',
        'covers/song2.jpg',
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
        'songs/DatKaa_-_Đông_Phai_Mờ_Dáng_Ai.mp3',
        'covers/song3.jpg',
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
        'songs/Hoài_Lâm_-_Hoa_Nở_Không_Màu.mp3',
        'covers/song4.jpg',
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
        'songs/Khắc_Hưng_-_Cay.mp3',
        'covers/song5.jpg',
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
        'songs/Khải_Đăng_-_Hôm_Nay_Em_Cưới_Rồi.mp3',
        'covers/song6.jpg',
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
        'songs/Lãng_-_lời_tâm_sự_3.mp3',
        'covers/song7.jpg',
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
        'songs/Lê_Bảo_Bình_-_Bước_Qua_Đời_Nhau.mp3',
        'covers/song8.jpg',
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
        'songs/Lê_Bảo_Bình_-_Lá_Xa_Lìa_Cành.mp3',
        'covers/song9.jpg',
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
        'songs/Lê_Bảo_Bình_-_THÍCH_THÌ_ĐẾN.mp3',
        'covers/song10.jpg',
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
        'songs/MIN_-_Bài_này_chill_phết.mp3',
        'covers/song11.jpg',
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
        'songs/Ngô_Kiến_Huy_-_Già_Vợ_Yếu.mp3',
        'covers/song12.jpg',
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
        'songs/Phan_Mạnh_Quỳnh_-_Sau_Lời_Từ_Khước.mp3',
        'covers/song13.jpg',
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
        'songs/Phúc_Rev_-_Họ_chưa_từng_sai.mp3',
        'covers/song14.jpg',
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
        'songs/Quang_Dang_Tran_-ANH_ĐÃ_KHÔNG_BIẾT_CÁCH_YÊU_EM.mp3',
        'covers/song15.jpg',
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
        'songs/Quân_A.P_-_Còn_Gì_Đau_Hơn_Chữ_Đã_Từng.mp3',
        'covers/song16.jpg',
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
        'songs/Quân_A.P_-_Ai_Là_Người_Thương_Em.mp3',
        'covers/song17.jpg',
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
        'songs/Sơn_Tùng_M-TP_-_Âm_Thầm_Bên_Em.mp3',
        'covers/song18.jpg',
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
        'songs/Thành_Đạt_-_Ngày_Mai_Người_Ta_Lấy_Chồng.mp3',
        'covers/song19.jpg',
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
        'songs/Tình_Yêu_Hoa_Gió__Truong_Thế_Vinh.mp3',
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
        'songs/TRO-Music_-_VẠN_LÝ_SẦU.mp3',
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
        'songs/TRO-Music_-_ĐÀ_LẠT_CƠN_MƯA_KHÔNG_EM.mp3',
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
        'songs/Tùng_Dương_-_Tái_Sinh.mp3',
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
        'songs/Đạt_G_-_Ngày_Mai_Em_Đi_Mất.mp3',
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
        'songs/Đen_-_Lối_Nhỏ.mp3',
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
        'songs/ĐỂ_VƯƠNG_-_ĐỈNH_ĐÚNG.mp3',
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
    (5,'Morning Chill','Relax morning playlist','covers/p1.jpg',TRUE),

    (6,'Workout Hits','Gym playlist','covers/p2.jpg',TRUE),

    (7,'Late Night Coding','Coding playlist','covers/p3.jpg',TRUE),

    (8,'Study Time','Study playlist','covers/p4.jpg',TRUE),

    (5,'Jazz Cafe','Cafe vibes','covers/p5.jpg',TRUE),

    (6,'Rock Energy','Rock playlist','covers/p6.jpg',TRUE),

    (7,'Pop Mood','Pop playlist','covers/p7.jpg',TRUE),

    (8,'LoFi Sleep','Sleep playlist','covers/p8.jpg',TRUE),

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