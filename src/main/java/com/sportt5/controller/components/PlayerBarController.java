package com.sportt5.controller.components;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.sportt5.model.Songs;
import com.sportt5.model.Users;
import com.sportt5.model.enums.AccountType;
import com.sportt5.model.enums.RequiredAccountType;
import com.sportt5.session.UserSession;
import com.sportt5.util.ApiClient;
import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.RotateTransition;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

import java.net.http.HttpResponse;

public class PlayerBarController {

    @FXML private StackPane coverPane;
    @FXML private ImageView coverImage;
    @FXML private Label nowTitle;
    @FXML private Label nowArtist;
    @FXML private Button btnPlay;
    @FXML private ProgressBar progressBar;
    @FXML private Label currentTimeLabel;
    @FXML private Label totalTimeLabel;
    @FXML private ProgressBar volumeBar;

    private MediaPlayer mediaPlayer;
    private Timeline progressTimeline;
    private RotateTransition spinAnimation;

    private static PlayerBarController instance;

    private final ObjectMapper mapper = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

    public static PlayerBarController getInstance() {
        return instance;
    }

    @FXML
    public void initialize() {
        instance = this;

        Circle clip = new Circle(23, 23, 23);
        coverPane.setClip(clip);

        spinAnimation = new RotateTransition(Duration.seconds(8), coverPane);
        spinAnimation.setByAngle(360);
        spinAnimation.setCycleCount(Animation.INDEFINITE);
        spinAnimation.setInterpolator(Interpolator.LINEAR);

        progressBar.setOnMouseClicked(e -> {
            if (mediaPlayer == null) return;
            Duration total = mediaPlayer.getTotalDuration();
            if (total == null || total.isUnknown() || total.isIndefinite()) return;
            double ratio = e.getX() / progressBar.getWidth();
            mediaPlayer.seek(total.multiply(Math.max(0, Math.min(1, ratio))));
        });

        volumeBar.setOnMouseClicked(e -> {
            double vol = Math.max(0, Math.min(1, e.getX() / volumeBar.getWidth()));
            volumeBar.setProgress(vol);
            if (mediaPlayer != null) mediaPlayer.setVolume(vol);
        });
    }

    public void playSong(Songs song) {
        // SongSummaryResponse không trả về requiredAccountType & fileUrl
        // → luôn fetch full details để kiểm tra quyền truy cập chính xác
        if (song.getFileUrl() == null || song.getFileUrl().isBlank()) {
            new Thread(() -> {
                try {
                    HttpResponse<String> resp = ApiClient.get("songs/" + song.getId());
                    if (resp.statusCode() == 200) {
                        Songs full = mapper.readValue(resp.body(), Songs.class);
                        Platform.runLater(() -> {
                            if (!canAccess(full)) {
                                showAccessDenied(full);
                            } else {
                                doPlay(full);
                            }
                        });
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();
        } else {
            // fileUrl đã có → vẫn kiểm tra requiredAccountType nếu đã biết
            if (!canAccess(song)) {
                showAccessDenied(song);
            } else {
                doPlay(song);
            }
        }
    }

    // Kiểm tra user.accountType >= song.requiredAccountType theo thứ tự NORMAL < PRO < PREMIUM
    private boolean canAccess(Songs song) {
        UserSession session = UserSession.getInstance();
        if (session == null) return false;
        Users user = session.getCurrentUser();
        if (user == null) return false;

        AccountType userType = user.getAccountType() != null
                ? user.getAccountType() : AccountType.NORMAL;
        RequiredAccountType required = song.getRequiredAccountType() != null
                ? song.getRequiredAccountType() : RequiredAccountType.NORMAL;

        return userType.ordinal() >= required.ordinal();
    }

    private void showAccessDenied(Songs song) {
        RequiredAccountType required = song.getRequiredAccountType() != null
                ? song.getRequiredAccountType() : RequiredAccountType.NORMAL;
        nowTitle.setText("🔒 " + song.getTitle());
        nowArtist.setText("Cần tài khoản " + required.name() + " để nghe bài này");
    }

    private void doPlay(Songs song) {
        stopCurrent();

        nowTitle.setText(song.getTitle());
        nowArtist.setText("");

        String coverUrl = ApiClient.resolveUrl(song.getCoverUrl());
        if (coverUrl != null) coverImage.setImage(new Image(coverUrl, true));

        String audioUrl = resolveAudioUrl(song.getFileUrl());
        if (audioUrl == null) return;

        try {
            Media media = new Media(audioUrl);
            mediaPlayer = new MediaPlayer(media);
            mediaPlayer.setVolume(volumeBar.getProgress());

            mediaPlayer.setOnReady(() -> {
                totalTimeLabel.setText(formatDuration(mediaPlayer.getTotalDuration()));
                mediaPlayer.play();
                btnPlay.setText("⏸");
                spinAnimation.play();
                startProgressTimeline();
            });

            mediaPlayer.setOnEndOfMedia(this::onSongEnded);
            mediaPlayer.setOnError(() -> Platform.runLater(() -> btnPlay.setText("▶")));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void onPlayPause() {
        if (mediaPlayer == null) return;
        if (mediaPlayer.getStatus() == MediaPlayer.Status.PLAYING) {
            mediaPlayer.pause();
            btnPlay.setText("▶");
            spinAnimation.pause();
            stopProgressTimeline();
        } else {
            mediaPlayer.play();
            btnPlay.setText("⏸");
            spinAnimation.play();
            startProgressTimeline();
        }
    }

    private void startProgressTimeline() {
        stopProgressTimeline();
        progressTimeline = new Timeline(new KeyFrame(Duration.millis(500), e -> {
            if (mediaPlayer == null) return;
            Duration current = mediaPlayer.getCurrentTime();
            Duration total   = mediaPlayer.getTotalDuration();
            if (total == null || total.isUnknown() || total.isIndefinite()) return;
            progressBar.setProgress(current.toMillis() / total.toMillis());
            currentTimeLabel.setText(formatDuration(current));
        }));
        progressTimeline.setCycleCount(Animation.INDEFINITE);
        progressTimeline.play();
    }

    private void stopProgressTimeline() {
        if (progressTimeline != null) {
            progressTimeline.stop();
            progressTimeline = null;
        }
    }

    private void stopCurrent() {
        stopProgressTimeline();
        spinAnimation.stop();
        coverPane.setRotate(0);
        progressBar.setProgress(0);
        currentTimeLabel.setText("0:00");
        totalTimeLabel.setText("0:00");
        btnPlay.setText("▶");
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.dispose();
            mediaPlayer = null;
        }
    }

    private void onSongEnded() {
        Platform.runLater(() -> {
            btnPlay.setText("▶");
            spinAnimation.stop();
            coverPane.setRotate(0);
            progressBar.setProgress(1);
            stopProgressTimeline();
        });
    }

    private String resolveAudioUrl(String fileUrl) {
        if (fileUrl == null || fileUrl.isBlank()) return null;
        if (fileUrl.startsWith("http")) return fileUrl;
        String filename = fileUrl.contains("/")
                ? fileUrl.substring(fileUrl.lastIndexOf('/') + 1) : fileUrl;
        var resource = getClass().getResource("/com.sportt5/songs/" + filename);
        if (resource != null) return resource.toExternalForm();
        return ApiClient.resolveUrl(fileUrl);
    }

    private String formatDuration(Duration d) {
        if (d == null || d.isUnknown() || d.isIndefinite()) return "0:00";
        int secs = (int) d.toSeconds();
        return String.format("%d:%02d", secs / 60, secs % 60);
    }
}
