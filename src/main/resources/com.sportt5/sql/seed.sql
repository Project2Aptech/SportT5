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
    ('admin1','admin1@sportt5.com','$2a$10$NJWg8R5zygzBWrOZlrA3TOw/rWr3ZUUSofU7GFLx5VL0iRv99DsOG','ADMIN','PREMIUM','Admin One','https://res.cloudinary.com/dnnhtiafm/image/upload/v1780582720/artist8_yw1pmn.jpg','System administrator','1995-01-01',TRUE),

    ('artist1','artist1@sportt5.com','$2a$10$NJWg8R5zygzBWrOZlrA3TOw/rWr3ZUUSofU7GFLx5VL0iRv99DsOG','ARTIST','PRO','Artist One','https://res.cloudinary.com/dnnhtiafm/image/upload/v1780582735/artist1_rvdvog.jpg','Pop artist','1998-02-10',TRUE),

    ('artist2','artist2@sportt5.com','$2a$10$NJWg8R5zygzBWrOZlrA3TOw/rWr3ZUUSofU7GFLx5VL0iRv99DsOG','ARTIST','PREMIUM','Artist Two','https://res.cloudinary.com/dnnhtiafm/image/upload/v1780582714/artist2_xe6vm8.jpg','Rock artist','1997-03-12',TRUE),

    ('artist3','artist3@sportt5.com','$2a$10$NJWg8R5zygzBWrOZlrA3TOw/rWr3ZUUSofU7GFLx5VL0iRv99DsOG','ARTIST','PRO','Artist Three','https://res.cloudinary.com/dnnhtiafm/image/upload/v1780582715/artist3_qb2r0x.jpg','Jazz artist','1996-04-14',TRUE),

    ('user1','user1@sportt5.com','$2a$10$NJWg8R5zygzBWrOZlrA3TOw/rWr3ZUUSofU7GFLx5VL0iRv99DsOG','USER','NORMAL','User One','https://res.cloudinary.com/dnnhtiafm/image/upload/v1780582722/artist9_hphcvl.jpg','Music lover','2000-05-11',TRUE),

    ('user2','user2@sportt5.com','$2a$10$NJWg8R5zygzBWrOZlrA3TOw/rWr3ZUUSofU7GFLx5VL0iRv99DsOG','USER','PREMIUM','User Two','https://res.cloudinary.com/dnnhtiafm/image/upload/v1780582723/artist10_ratdlf.jpg','Playlist collector','2001-06-21',TRUE),

    ('user3','user3@sportt5.com','$2a$10$NJWg8R5zygzBWrOZlrA3TOw/rWr3ZUUSofU7GFLx5VL0iRv99DsOG','USER','NORMAL','User Three','https://res.cloudinary.com/dnnhtiafm/image/upload/v1780582724/artist11_xa3rjn.jpg','EDM fan','2002-07-15',TRUE),

    ('user4','user4@sportt5.com','$2a$10$NJWg8R5zygzBWrOZlrA3TOw/rWr3ZUUSofU7GFLx5VL0iRv99DsOG','USER','PRO','User Four','https://res.cloudinary.com/dnnhtiafm/image/upload/v1780582725/artist12_wkouqo.jpg','Chill music fan','1999-08-18',TRUE),

    ('artist4','artist4@sportt5.com','$2a$10$NJWg8R5zygzBWrOZlrA3TOw/rWr3ZUUSofU7GFLx5VL0iRv99DsOG','ARTIST','PREMIUM','Artist Four','https://res.cloudinary.com/dnnhtiafm/image/upload/v1780582716/artist4_iejt0i.jpg','Hip hop artist','1994-09-19',TRUE),

    ('artist5','artist5@sportt5.com','$2a$10$NJWg8R5zygzBWrOZlrA3TOw/rWr3ZUUSofU7GFLx5VL0iRv99DsOG','ARTIST','PRO','Artist Five','https://res.cloudinary.com/dnnhtiafm/image/upload/v1780582717/artist5_pktnoe.jpg','Lo-fi producer','1993-10-20',TRUE),

    ('artist6','artist6@sportt5.com','$2a$10$NJWg8R5zygzBWrOZlrA3TOw/rWr3ZUUSofU7GFLx5VL0iRv99DsOG','ARTIST','NORMAL','Artist Six','https://res.cloudinary.com/dnnhtiafm/image/upload/v1780582718/artist6_bus7v7.jpg','Indie singer','1992-11-05',TRUE),

    ('artist7','artist7@sportt5.com','$2a$10$NJWg8R5zygzBWrOZlrA3TOw/rWr3ZUUSofU7GFLx5VL0iRv99DsOG','ARTIST','PRO','Artist Seven','https://res.cloudinary.com/dnnhtiafm/image/upload/v1780582719/artist7_adqjpg.jpg','Rap artist','1991-12-15',TRUE);

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
    (2,'Pop Dreams','https://res.cloudinary.com/dnnhtiafm/image/upload/v1780582780/album1_d6g5db.jpg','2024-01-01'),
    (3,'Rock Legends','https://res.cloudinary.com/dnnhtiafm/image/upload/v1780582755/album2_vqjq8a.jpg','2024-01-10'),
    (4,'Smooth Jazz','https://res.cloudinary.com/dnnhtiafm/image/upload/v1780582756/album3_qygfu9.jpg','2024-01-15'),
    (9,'Hip Hop Streets','https://res.cloudinary.com/dnnhtiafm/image/upload/v1780582757/album4_zz8mii.jpg','2024-01-20'),
    (10,'LoFi Nights','https://res.cloudinary.com/dnnhtiafm/image/upload/v1780582759/album5_fvtknd.jpg','2024-01-25'),
    (2,'Summer Pop','https://res.cloudinary.com/dnnhtiafm/image/upload/v1780582760/album6_gcgmc2.jpg','2024-02-01'),
    (3,'Metal Fire','https://res.cloudinary.com/dnnhtiafm/image/upload/v1780582761/album7_vlewrv.jpg','2024-02-10'),
    (4,'Jazz Lounge','https://res.cloudinary.com/dnnhtiafm/image/upload/v1780582763/album8_y9n12h.jpg','2024-02-15'),
    (9,'Rap World','https://res.cloudinary.com/dnnhtiafm/image/upload/v1780582764/album9_cjmjzu.jpg','2024-02-20'),
    (10,'Chill Beats','https://res.cloudinary.com/dnnhtiafm/image/upload/v1780582765/album10_rrhhva.jpg','2024-02-25'),
    (11,'Indie Souls','https://res.cloudinary.com/dnnhtiafm/image/upload/v1780582767/album11_m7lsip.jpg','2024-03-01'),
    (12,'Street Rap','https://res.cloudinary.com/dnnhtiafm/image/upload/v1780582768/album12_uuef3v.jpg','2024-03-10');

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
        2,
        1,
        'E Là Không Thể',
        210,
        'https://res.cloudinary.com/dnnhtiafm/video/upload/v1780581908/e-la-khong-the_zxjjqd.mp3',
        'https://res.cloudinary.com/dnnhtiafm/image/upload/v1780582654/song1_ou1w5p.jpg',
        1,
        1200,
        'LIVE',
        'NORMAL'
    ),

    (
        2,
        1,
        'Chiều Thu Hoa Bóng Nắng',
        205,
        'https://res.cloudinary.com/dnnhtiafm/video/upload/v1780581920/chieu-thu-hoa-bong-nang_caem5m.mp3',
        'https://res.cloudinary.com/dnnhtiafm/image/upload/v1780582641/song2_hvx8gl.jpg',
        2,
        1800,
        'LIVE',
        'NORMAL'
    ),

    (
        2,
        1,
        'Đông Phai Mờ Dáng Ai',
        240,
        'https://res.cloudinary.com/dnnhtiafm/video/upload/v1780581920/dong-phai-mo-dang-ai_xw8bpr.mp3',
        'https://res.cloudinary.com/dnnhtiafm/image/upload/v1780582642/song3_fsoyrw.jpg',
        3,
        1500,
        'LIVE',
        'NORMAL'
    ),

    (
        3,
        2,
        'Hoa Nở Không Màu',
        250,
        'https://res.cloudinary.com/dnnhtiafm/video/upload/v1780581916/hoa-no-khong-mau_e2jsgx.mp3',
        'https://res.cloudinary.com/dnnhtiafm/image/upload/v1780582642/song4_iod4po.jpg',
        1,
        3500,
        'LIVE',
        'PRO'
    ),

    (
        3,
        2,
        'Cay',
        200,
        'https://res.cloudinary.com/dnnhtiafm/video/upload/v1780581914/cay_khigvd.mp3',
        'https://res.cloudinary.com/dnnhtiafm/image/upload/v1780582643/song5_ilq96m.jpg',
        2,
        900,
        'LIVE',
        'NORMAL'
    ),

    (
        4,
        3,
        'Hôm Nay Em Cưới Rồi',
        230,
        'https://res.cloudinary.com/dnnhtiafm/video/upload/v1780581910/hom-nay-em-cuoi-roi_bwltrl.mp3',
        'https://res.cloudinary.com/dnnhtiafm/image/upload/v1780582643/song6_ler5bq.jpg',
        1,
        1700,
        'LIVE',
        'NORMAL'
    ),

    (
        4,
        3,
        'Lời Tâm Sự Số 3',
        260,
        'https://res.cloudinary.com/dnnhtiafm/video/upload/v1780581919/loi-tam-su-so-3_ykep6o.mp3',
        'https://res.cloudinary.com/dnnhtiafm/image/upload/v1780582644/song7_nslkut.jpg',
        2,
        1100,
        'LIVE',
        'NORMAL'
    ),

    (
        9,
        4,
        'Bước Qua Đời Nhau',
        240,
        'https://res.cloudinary.com/dnnhtiafm/video/upload/v1780581918/buoc-qua-doi-nhau_y6pxnl.mp3',
        'https://res.cloudinary.com/dnnhtiafm/image/upload/v1780582645/song8_hmjbm6.jpg',
        1,
        4200,
        'LIVE',
        'PRO'
    ),

    (
        9,
        4,
        'Lá Xa Lìa Cành',
        220,
        'https://res.cloudinary.com/dnnhtiafm/video/upload/v1780581911/la-xa-lia-canh_jjedqc.mp3',
        'https://res.cloudinary.com/dnnhtiafm/image/upload/v1780582645/song9_t58vsn.jpg',
        2,
        3900,
        'LIVE',
        'NORMAL'
    ),

    (
        9,
        4,
        'Thích Thì Đến',
        210,
        'https://res.cloudinary.com/dnnhtiafm/video/upload/v1780581923/thich-thi-den_zetlpv.mp3',
        'https://res.cloudinary.com/dnnhtiafm/image/upload/v1780582646/song10_mlsphs.jpg',
        3,
        2500,
        'LIVE',
        'NORMAL'
    ),

    (
        10,
        5,
        'Bài Này Chill Phết',
        230,
        'https://res.cloudinary.com/dnnhtiafm/video/upload/v1780581915/bai-nay-chill-phet_a5xedb.mp3',
        'https://res.cloudinary.com/dnnhtiafm/image/upload/v1780582647/song11_dmrrvx.jpg',
        1,
        5100,
        'LIVE',
        'PREMIUM'
    ),

    (
        10,
        5,
        'Già Vợ Yếu',
        215,
        'https://res.cloudinary.com/dnnhtiafm/video/upload/v1780581908/gia-vo-yeu_zk7sad.mp3',
        'https://res.cloudinary.com/dnnhtiafm/image/upload/v1780582648/song12_vl5g1z.jpg',
        2,
        1300,
        'LIVE',
        'NORMAL'
    ),

    (
        2,
        6,
        'Sau Lời Từ Khước',
        260,
        'https://res.cloudinary.com/dnnhtiafm/video/upload/v1780581910/sau-loi-tu-khuoc_om6wmb.mp3',
        'https://res.cloudinary.com/dnnhtiafm/image/upload/v1780582649/song13_ofpv1g.jpg',
        1,
        3000,
        'LIVE',
        'PRO'
    ),

    (
        2,
        6,
        'Họ Chưa Từng Sai',
        250,
        'https://res.cloudinary.com/dnnhtiafm/video/upload/v1780581910/ho-chua-tung-sai_osltbf.mp3',
        'https://res.cloudinary.com/dnnhtiafm/image/upload/v1780582650/song14_szwziy.jpg',
        2,
        2800,
        'LIVE',
        'NORMAL'
    ),

    (
        3,
        7,
        'Anh Đã Không Biết Cách Yêu Em',
        235,
        'https://res.cloudinary.com/dnnhtiafm/video/upload/v1780581918/anh-da-khong-biet-cach-yeu-em_vgsojh.mp3',
        'https://res.cloudinary.com/dnnhtiafm/image/upload/v1780582650/song15_xcjkkw.jpg',
        1,
        1400,
        'LIVE',
        'NORMAL'
    ),

    (
        3,
        7,
        'Còn Gì Đau Hơn Chữ Đã Từng',
        245,
        'https://res.cloudinary.com/dnnhtiafm/video/upload/v1780581921/con-gi-dau-hon-chu-da-tung_qlzgnd.mp3',
        'https://res.cloudinary.com/dnnhtiafm/image/upload/v1780582651/song16_kgx5ua.jpg',
        2,
        3600,
        'LIVE',
        'PRO'
    ),

    (
        4,
        8,
        'Ai Là Người Thương Em',
        220,
        'https://res.cloudinary.com/dnnhtiafm/video/upload/v1780581926/ai-la-nguoi-thuong-em_qgnbig.mp3',
        'https://res.cloudinary.com/dnnhtiafm/image/upload/v1780582652/song17_xbrkjz.jpg',
        1,
        4100,
        'LIVE',
        'NORMAL'
    ),

    (
        4,
        8,
        'Âm Thầm Bên Em',
        255,
        'https://res.cloudinary.com/dnnhtiafm/video/upload/v1780581928/am-tham-ben-em_fm4lpi.mp3',
        'https://res.cloudinary.com/dnnhtiafm/image/upload/v1780582652/song18_nx9l9c.jpg',
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
        'https://res.cloudinary.com/dnnhtiafm/video/upload/v1780581924/ngay-mai-nguoi-ta-lay-chong_wr7fos.mp3',
        'https://res.cloudinary.com/dnnhtiafm/image/upload/v1780582653/song19_mwe930.jpg',
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
        'https://res.cloudinary.com/dnnhtiafm/video/upload/v1780581924/tinh-yeu-hoa-gio_zrcfqp.mp3',
        'https://res.cloudinary.com/dnnhtiafm/image/upload/v1780582654/song1_ou1w5p.jpg',
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
        'https://res.cloudinary.com/dnnhtiafm/video/upload/v1780581914/van-ly-sau_rm6t5a.mp3',
        'https://res.cloudinary.com/dnnhtiafm/image/upload/v1780582641/song2_hvx8gl.jpg',
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
        'https://res.cloudinary.com/dnnhtiafm/video/upload/v1780581918/da-lat-con-mua-khong-em_kucxga.mp3',
        'https://res.cloudinary.com/dnnhtiafm/image/upload/v1780582642/song3_fsoyrw.jpg',
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
        'https://res.cloudinary.com/dnnhtiafm/video/upload/v1780581912/tai-sinh_ook2rf.mp3',
        'https://res.cloudinary.com/dnnhtiafm/image/upload/v1780582642/song4_iod4po.jpg',
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
        'https://res.cloudinary.com/dnnhtiafm/video/upload/v1780581908/ngay-mai-em-di-mat_xkyo7i.mp3',
        'https://res.cloudinary.com/dnnhtiafm/image/upload/v1780582643/song5_ilq96m.jpg',
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
        'https://res.cloudinary.com/dnnhtiafm/video/upload/v1780581912/loi-nho_ww6bzo.mp3',
        'https://res.cloudinary.com/dnnhtiafm/image/upload/v1780582643/song6_ler5bq.jpg',
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
        'https://res.cloudinary.com/dnnhtiafm/video/upload/v1780581916/de-vuong_iq9wsu.mp3',
        'https://res.cloudinary.com/dnnhtiafm/image/upload/v1780582644/song7_nslkut.jpg',
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
    (5,'Morning Chill','Relax morning playlist','https://res.cloudinary.com/dnnhtiafm/image/upload/v1780582682/playlist1_brhwxq.jpg',TRUE),

    (6,'Workout Hits','Gym playlist','https://res.cloudinary.com/dnnhtiafm/image/upload/v1780582683/playlist2_au6wly.jpg',TRUE),

    (7,'Late Night Coding','Coding playlist','https://res.cloudinary.com/dnnhtiafm/image/upload/v1780582667/playlist3_zoni3a.jpg',TRUE),

    (8,'Study Time','Study playlist','https://res.cloudinary.com/dnnhtiafm/image/upload/v1780582668/playlist4_zjkved.jpg',TRUE),

    (5,'Jazz Cafe','Cafe vibes','https://res.cloudinary.com/dnnhtiafm/image/upload/v1780582669/playlist5_iu0zbd.jpg',TRUE),

    (6,'Rock Energy','Rock playlist','https://res.cloudinary.com/dnnhtiafm/image/upload/v1780582669/playlist6_dzqxxn.jpg',TRUE),

    (7,'Pop Mood','Pop playlist','https://res.cloudinary.com/dnnhtiafm/image/upload/v1780582670/playlist7_zxch2m.jpg',TRUE),

    (8,'LoFi Sleep','Sleep playlist','https://res.cloudinary.com/dnnhtiafm/image/upload/v1780582671/playlist8_pffq2o.jpg',TRUE),

    (5,'Rap Party','Party playlist','https://res.cloudinary.com/dnnhtiafm/image/upload/v1780582672/playlist9_m41gnu.jpg',TRUE),

    (6,'Indie Relax','Relax indie songs','https://res.cloudinary.com/dnnhtiafm/image/upload/v1780582673/playlist10_jixbko.jpg',TRUE);

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
