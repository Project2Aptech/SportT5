USE sportt5;

/* =====================================================
   USERS
===================================================== */

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
        artist_id 1,
        album_id 1,
        title 'E Là Không Thể',
        duration_seconds 210,
        file_url 'songs/Anh_Quân_Idol_-_E_Là_Không_Thể.mp3',
        cover_url 'covers/song1.jpg',
        track_number 1,
        play_count 1200,
        status 'LIVE',
        required_account_type 'NORMAL'
    ),

    (
        artist_id 1,
        album_id 1,
        title 'Chiều Thu Hoa Bóng Nắng',
        duration_seconds 205,
        file_url 'songs/DatKaa_-_Chiều_Thu_Hoa_Bóng_Nắng.mp3',
        cover_url 'covers/song2.jpg',
        track_number 2,
        play_count 1800,
        status 'LIVE',
        required_account_type 'NORMAL'
    ),

    (
        artist_id 1,
        album_id 1,
        title 'Đông Phai Mờ Dáng Ai',
        duration_seconds 240,
        file_url 'songs/DatKaa_-_Đông_Phai_Mờ_Dáng_Ai.mp3',
        cover_url 'covers/song3.jpg',
        track_number 3,
        play_count 1500,
        status 'LIVE',
        required_account_type 'NORMAL'
    ),

    (
        artist_id 2,
        album_id 2,
        title 'Hoa Nở Không Màu',
        duration_seconds 250,
        file_url 'songs/Hoài_Lâm_-_Hoa_Nở_Không_Màu.mp3',
        cover_url 'covers/song4.jpg',
        track_number 1,
        play_count 3500,
        status 'LIVE',
        required_account_type 'PRO'
    ),

    (
        artist_id 2,
        album_id 2,
        title 'Cay',
        duration_seconds 200,
        file_url 'songs/Khắc_Hưng_-_Cay.mp3',
        cover_url 'covers/song5.jpg',
        track_number 2,
        play_count 900,
        status 'LIVE',
        required_account_type 'NORMAL'
    ),

    (
        artist_id 3,
        album_id 3,
        title 'Hôm Nay Em Cưới Rồi',
        duration_seconds 230,
        file_url 'songs/Khải_Đăng_-_Hôm_Nay_Em_Cưới_Rồi.mp3',
        cover_url 'covers/song6.jpg',
        track_number 1,
        play_count 1700,
        status 'LIVE',
        required_account_type 'NORMAL'
    ),

    (
        artist_id 3,
        album_id 3,
        title 'Lời Tâm Sự Số 3',
        duration_seconds 260,
        file_url 'songs/Lãng_-_lời_tâm_sự_3.mp3',
        cover_url 'covers/song7.jpg',
        track_number 2,
        play_count 1100,
        status 'LIVE',
        required_account_type 'NORMAL'
    ),

    (
        artist_id 4,
        album_id 4,
        title 'Bước Qua Đời Nhau',
        duration_seconds 240,
        file_url 'songs/Lê_Bảo_Bình_-_Bước_Qua_Đời_Nhau.mp3',
        cover_url 'covers/song8.jpg',
        track_number 1,
        play_count 4200,
        status 'LIVE',
        required_account_type 'PRO'
    ),

    (
        artist_id 4,
        album_id 4,
        title 'Lá Xa Lìa Cành',
        duration_seconds 220,
        file_url 'songs/Lê_Bảo_Bình_-_Lá_Xa_Lìa_Cành.mp3',
        cover_url 'covers/song9.jpg',
        track_number 2,
        play_count 3900,
        status 'LIVE',
        required_account_type 'NORMAL'
    ),

    (
        artist_id 4,
        album_id 4,
        title 'Thích Thì Đến',
        duration_seconds 210,
        file_url 'songs/Lê_Bảo_Bình_-_THÍCH_THÌ_ĐẾN.mp3',
        cover_url 'covers/song10.jpg',
        track_number 3,
        play_count 2500,
        status 'LIVE',
        required_account_type 'NORMAL'
    ),

    (
        artist_id 5,
        album_id 5,
        title 'Bài Này Chill Phết',
        duration_seconds 230,
        file_url 'songs/MIN_-_Bài_này_chill_phết.mp3',
        cover_url 'covers/song11.jpg',
        track_number 1,
        play_count 5100,
        status 'LIVE',
        required_account_type 'PREMIUM'
    ),

    (
        artist_id 5,
        album_id 5,
        title 'Già Vợ Yếu',
        duration_seconds 215,
        file_url 'songs/Ngô_Kiến_Huy_-_Già_Vợ_Yếu.mp3',
        cover_url 'covers/song12.jpg',
        track_number 2,
        play_count 1300,
        status 'LIVE',
        required_account_type 'NORMAL'
    ),

    (
        artist_id 6,
        album_id 6,
        title 'Sau Lời Từ Khước',
        duration_seconds 260,
        file_url 'songs/Phan_Mạnh_Quỳnh_-_Sau_Lời_Từ_Khước.mp3',
        cover_url 'covers/song13.jpg',
        track_number 1,
        play_count 3000,
        status 'LIVE',
        required_account_type 'PRO'
    ),

    (
        artist_id 6,
        album_id 6,
        title 'Họ Chưa Từng Sai',
        duration_seconds 250,
        file_url 'songs/Phúc_Rev_-_Họ_chưa_từng_sai.mp3',
        cover_url 'covers/song14.jpg',
        track_number 2,
        play_count 2800,
        status 'LIVE',
        required_account_type 'NORMAL'
    ),

    (
        artist_id 7,
        album_id 7,
        title 'Anh Đã Không Biết Cách Yêu Em',
        duration_seconds 235,
        file_url 'songs/Quang_Dang_Tran_-ANH_ĐÃ_KHÔNG_BIẾT_CÁCH_YÊU_EM.mp3',
        cover_url 'covers/song15.jpg',
        track_number 1,
        play_count 1400,
        status 'LIVE',
        required_account_type 'NORMAL'
    ),

    (
        artist_id 7,
        album_id 7,
        title 'Còn Gì Đau Hơn Chữ Đã Từng',
        duration_seconds 245,
        file_url 'songs/Quân_A.P_-_Còn_Gì_Đau_Hơn_Chữ_Đã_Từng.mp3',
        cover_url 'covers/song16.jpg',
        track_number 2,
        play_count 3600,
        status 'LIVE',
        required_account_type 'PRO'
    ),

    (
        artist_id 8,
        album_id 8,
        title 'Ai Là Người Thương Em',
        duration_seconds 220,
        file_url 'songs/Quân_A.P_-_Ai_Là_Người_Thương_Em.mp3',
        cover_url 'covers/song17.jpg',
        track_number 1,
        play_count 4100,
        status 'LIVE',
        required_account_type 'NORMAL'
    ),

    (
        artist_id 8,
        album_id 8,
        title 'Âm Thầm Bên Em',
        duration_seconds 255,
        file_url 'songs/Sơn_Tùng_M-TP_-_Âm_Thầm_Bên_Em.mp3',
        cover_url 'covers/song18.jpg',
        track_number 2,
        play_count 8000,
        status 'LIVE',
        required_account_type 'PREMIUM'
    ),

    (
        artist_id 9,
        album_id 9,
        title 'Ngày Mai Người Ta Lấy Chồng',
        duration_seconds 240,
        file_url 'songs/Thành_Đạt_-_Ngày_Mai_Người_Ta_Lấy_Chồng.mp3',
        cover_url 'covers/song19.jpg',
        track_number 1,
        play_count 4600,
        status 'LIVE',
        required_account_type 'PRO'
    ),

    (
        artist_id 9,
        album_id 9,
        title 'Trường Thế Vinh',
        duration_seconds 215,
        file_url 'songs/Tình_Yêu_Hoa_Gió__Truong_Thế_Vinh.mp3',
        cover_url 'covers/song20.jpg',
        track_number 2,
        play_count 900,
        status 'LIVE',
        required_account_type 'NORMAL'
    ),

    (
        artist_id 10,
        album_id 10,
        title 'Vạn Lý Sầu',
        duration_seconds 225,
        file_url 'songs/TRO-Music_-_VẠN_LÝ_SẦU.mp3',
        cover_url 'covers/song21.jpg',
        track_number 1,
        play_count 6200,
        status 'LIVE',
        required_account_type 'PREMIUM'
    ),

    (
        artist_id 10,
        album_id 10,
        title 'Đà Lạt Cơn Mưa Không Em',
        duration_seconds 245,
        file_url 'songs/TRO-Music_-_ĐÀ_LẠT_CƠN_MƯA_KHÔNG_EM.mp3',
        cover_url 'covers/song22.jpg',
        track_number 2,
        play_count 3100,
        status 'LIVE',
        required_account_type 'PRO'
    ),

    (
        artist_id 11,
        album_id 11,
        title 'Tái Sinh',
        duration_seconds 250,
        file_url 'songs/Tùng_Dương_-_Tái_Sinh.mp3',
        cover_url 'covers/song23.jpg',
        track_number 1,
        play_count 1500,
        status 'LIVE',
        required_account_type 'NORMAL'
    ),

    (
        artist_id 11,
        album_id 11,
        title 'Ngày Mai Em Đi Mất',
        duration_seconds 235,
        file_url 'songs/Đạt_G_-_Ngày_Mai_Em_Đi_Mất.mp3',
        cover_url 'covers/song24.jpg',
        track_number 2,
        play_count 1700,
        status 'LIVE',
        required_account_type 'NORMAL'
    ),

    (
        artist_id 12,
        album_id 12,
        title 'Lối Nhỏ',
        duration_seconds 210,
        file_url 'songs/Đen_-_Lối_Nhỏ.mp3',
        cover_url 'covers/song25.jpg',
        track_number 1,
        play_count 9500,
        status 'LIVE',
        required_account_type 'PREMIUM'
    ),

    (
        artist_id 12,
        album_id 12,
        title 'Đỉnh Đúng',
        duration_seconds 260,
        file_url 'songs/ĐỂ_VƯƠNG_-_ĐỈNH_ĐÚNG.mp3',
        cover_url 'covers/song26.jpg',
        track_number 2,
        play_count 1200,
        status 'LIVE',
        required_account_type 'NORMAL'
    )
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