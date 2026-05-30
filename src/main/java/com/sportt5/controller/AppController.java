package com.sportt5.controller;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputControl;
import javafx.scene.layout.HBox;
import javafx.scene.Parent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.prefs.Preferences;

public class AppController {
    private static final String LANGUAGE_KEY = "language";
    private static final String ENGLISH = "en";
    private static final String VIETNAMESE = "vi";
    private static final Preferences PREFS = Preferences.userNodeForPackage(AppController.class);
    private static final Map<String, String> EN_TO_VI = createTranslations();
    private static final Map<String, String> VI_TO_EN = reverseTranslations();

    @FXML
    private StackPane authView;
    @FXML
    private HBox homeView;
    @FXML
    private StackPane homeHero;
    @FXML
    private HBox genreTopBar;
    @FXML
    private HBox libraryTopBar;
    @FXML
    private HBox playlistTopBar;
    @FXML
    private HBox accountTopBar;
    @FXML
    private HBox adminTopBar;
    @FXML
    private javafx.scene.control.ScrollPane homePage;
    @FXML
    private javafx.scene.control.ScrollPane genrePage;
    @FXML
    private javafx.scene.control.ScrollPane favouritesPage;
    @FXML
    private javafx.scene.control.ScrollPane playlistPage;
    @FXML
    private javafx.scene.control.ScrollPane accountPage;
    @FXML
    private javafx.scene.control.ScrollPane adminPage;
    @FXML
    private javafx.scene.control.ScrollPane adminMusicPage;
    @FXML
    private javafx.scene.control.ScrollPane adminUploadPage;
    @FXML
    private javafx.scene.control.ScrollPane adminAnalyticsPage;
    @FXML
    private javafx.scene.control.ScrollPane adminFansPage;
    @FXML
    private javafx.scene.control.ScrollPane adminSettingsPage;
    @FXML
    private javafx.scene.control.ScrollPane artistPage;
    @FXML
    private javafx.scene.control.ScrollPane artistMusicPage;
    @FXML
    private javafx.scene.control.ScrollPane artistUploadPage;
    @FXML
    private javafx.scene.control.ScrollPane artistAnalyticsPage;
    @FXML
    private javafx.scene.control.ScrollPane artistFansPage;
    @FXML
    private VBox userBrowseNav;
    @FXML
    private VBox adminBrowseNav;
    @FXML
    private VBox artistBrowseNav;
    @FXML
    private Label libraryTitleLabel;
    @FXML
    private VBox userLibraryNav;
    @FXML
    private HBox homeNavItem;
    @FXML
    private HBox genreNavItem;
    @FXML
    private HBox favouritesNavItem;
    @FXML
    private HBox playlistNavItem;
    @FXML
    private HBox accountNavItem;
    @FXML
    private HBox adminPanelNavItem;
    @FXML
    private HBox artistPanelNavItem;
    @FXML
    private HBox adminDashboardNavItem;
    @FXML
    private HBox adminMusicNavItem;
    @FXML
    private HBox adminUploadNavItem;
    @FXML
    private HBox adminAnalyticsNavItem;
    @FXML
    private HBox adminFansNavItem;
    @FXML
    private HBox adminSettingsNavItem;
    @FXML
    private HBox exitAdminNavItem;
    @FXML
    private HBox artistDashboardNavItem;
    @FXML
    private HBox artistMusicNavItem;
    @FXML
    private HBox artistUploadNavItem;
    @FXML
    private HBox artistAnalyticsNavItem;
    @FXML
    private HBox artistFansNavItem;
    @FXML
    private HBox exitArtistNavItem;
    @FXML
    private HBox logoutNavItem;
    @FXML
    private VBox loginCard;
    @FXML
    private VBox signupCard;
    @FXML
    private TextField loginEmailField;
    @FXML
    private PasswordField loginPasswordField;
    @FXML
    private Label loginStatusLabel;
    @FXML
    private Label profileNameLabel;
    @FXML
    private Label profileTierLabel;
    @FXML
    private TextField signupNameField;
    @FXML
    private TextField signupEmailField;
    @FXML
    private PasswordField signupPasswordField;
    @FXML
    private Label signupStatusLabel;
    @FXML
    private Label playerCoverIcon;
    @FXML
    private Label playerTitleLabel;
    @FXML
    private Label playerArtistLabel;
    @FXML
    private Button playPauseButton;
    @FXML
    private Button shuffleButton;
    @FXML
    private Button repeatButton;
    @FXML
    private ProgressBar playerProgressBar;
    @FXML
    private ProgressBar volumeProgressBar;
    @FXML
    private Label currentTimeLabel;
    @FXML
    private Label durationLabel;

    private boolean adminMode;
    private boolean adminAccess;
    private boolean artistMode;
    private boolean artistAccess;
    private String signedInName = "Alex Studio";
    private String signedInTier = "PREMIUM";
    private String currentLanguage = PREFS.get(LANGUAGE_KEY, ENGLISH);
    private final Track[] demoQueue = {
            new Track("Death Grips is Online", "DEATH GRIPS", "▥", 195),
            new Track("Midnight City", "M83", "▯", 243),
            new Track("Glow On", "Turnstile", "▰", 218),
            new Track("Feel Good Inc.", "Gorillaz", "◉", 222),
            new Track("Prism Drift", "Aura Studio", "✦", 255)
    };
    private final Timeline playerTimeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> tickPlayer()));
    private int currentTrackIndex;
    private int elapsedSeconds;
    private boolean playing;
    private boolean shuffleEnabled;
    private boolean repeatEnabled;

    @FXML
    private void initialize() {
        playerTimeline.setCycleCount(Timeline.INDEFINITE);
        updatePlayerUi();
        applyLanguage();
    }

    @FXML
    private void showLogin() {
        show(loginCard);
        hide(signupCard);
        loginStatusLabel.setText("");
    }

    @FXML
    private void showSignup() {
        show(signupCard);
        hide(loginCard);
        signupStatusLabel.setText("");
    }

    @FXML
    private void signIn() {
        if (isBlank(loginEmailField.getText()) || isBlank(loginPasswordField.getText())) {
            loginStatusLabel.setText("Enter your email and password.");
            return;
        }

        String email = loginEmailField.getText().trim().toLowerCase();
        String password = loginPasswordField.getText();
        if ("admin@sportt5.local".equals(email) && "admin123".equals(password)) {
            adminAccess = true;
            artistAccess = false;
            adminMode = false;
            artistMode = false;
            signedInName = "Minh Admin";
            signedInTier = "ADMIN";
            openHome();
            return;
        }
        if ("artist@sportt5.local".equals(email) && "artist123".equals(password)) {
            adminAccess = false;
            artistAccess = true;
            adminMode = false;
            artistMode = false;
            signedInName = "Aura Studio";
            signedInTier = "ARTIST";
            openHome();
            return;
        }
        if ("user@sportt5.local".equals(email) && "user123".equals(password)) {
            adminAccess = false;
            artistAccess = false;
            adminMode = false;
            artistMode = false;
            signedInName = "Alex Studio";
            signedInTier = "PREMIUM";
            openHome();
            return;
        }

        loginStatusLabel.setText("Use user, artist, or admin mock account.");
    }

    @FXML
    private void signUp() {
        if (isBlank(signupNameField.getText()) || isBlank(signupEmailField.getText()) || isBlank(signupPasswordField.getText())) {
            signupStatusLabel.setText("Complete all fields to continue.");
            return;
        }

        adminMode = false;
        adminAccess = false;
        artistMode = false;
        artistAccess = false;
        signedInName = "Alex Studio";
        signedInTier = "PREMIUM";
        openHome();
    }

    @FXML
    private void forgotPassword() {
        loginStatusLabel.setText("Password reset is mocked in this frontend build.");
        applyLanguageToNode(loginStatusLabel);
    }

    @FXML
    private void switchToEnglish() {
        setLanguage(ENGLISH);
    }

    @FXML
    private void switchToVietnamese() {
        setLanguage(VIETNAMESE);
    }

    @FXML
    private void logout() {
        adminMode = false;
        adminAccess = false;
        artistMode = false;
        artistAccess = false;
        signedInName = "Alex Studio";
        signedInTier = "PREMIUM";

        loginEmailField.clear();
        loginPasswordField.clear();
        signupNameField.clear();
        signupEmailField.clear();
        signupPasswordField.clear();
        loginStatusLabel.setText("");
        signupStatusLabel.setText("");
        playing = false;
        playerTimeline.stop();
        updatePlayerUi();

        profileNameLabel.setText(signedInName);
        profileTierLabel.setText(signedInTier);
        configureSidebarForRole();
        hide(homeView);
        show(authView);
        showLogin();
    }

    @FXML
    private void showHomePage() {
        if (adminMode) {
            showAdminPage();
            return;
        }
        if (artistMode) {
            showArtistPage();
            return;
        }

        show(homeHero);
        show(homePage);
        hide(genreTopBar);
        hide(libraryTopBar);
        hide(playlistTopBar);
        hide(accountTopBar);
        hide(adminTopBar);
        hide(genrePage);
        hide(favouritesPage);
        hide(playlistPage);
        hide(accountPage);
        hideAdminPages();
        hideArtistPages();
        setUserActive(homeNavItem);
    }

    @FXML
    private void showGenrePage() {
        hide(homeHero);
        hide(homePage);
        hide(libraryTopBar);
        hide(playlistTopBar);
        hide(accountTopBar);
        hide(adminTopBar);
        hide(favouritesPage);
        hide(playlistPage);
        hide(accountPage);
        hideAdminPages();
        hideArtistPages();
        show(genreTopBar);
        show(genrePage);
        setUserActive(genreNavItem);
    }

    @FXML
    private void showFavouritesPage() {
        hide(homeHero);
        hide(homePage);
        hide(genreTopBar);
        hide(genrePage);
        hide(playlistTopBar);
        hide(playlistPage);
        hide(accountTopBar);
        hide(accountPage);
        hide(adminTopBar);
        hideAdminPages();
        hideArtistPages();
        show(libraryTopBar);
        show(favouritesPage);
        setUserActive(favouritesNavItem);
    }

    @FXML
    private void showPlaylistPage() {
        hide(homeHero);
        hide(homePage);
        hide(genreTopBar);
        hide(genrePage);
        hide(libraryTopBar);
        hide(favouritesPage);
        hide(accountTopBar);
        hide(accountPage);
        hide(adminTopBar);
        hideAdminPages();
        hideArtistPages();
        show(playlistTopBar);
        show(playlistPage);
        setUserActive(playlistNavItem);
    }

    @FXML
    private void showAccountPage() {
        hide(homeHero);
        hide(homePage);
        hide(genreTopBar);
        hide(genrePage);
        hide(libraryTopBar);
        hide(favouritesPage);
        hide(playlistTopBar);
        hide(playlistPage);
        hide(adminTopBar);
        hideAdminPages();
        hideArtistPages();
        show(accountTopBar);
        show(accountPage);
        setUserActive(accountNavItem);
    }

    @FXML
    private void enterAdminPanel() {
        if (!adminAccess) {
            return;
        }

        adminMode = true;
        artistMode = false;
        profileNameLabel.setText("System Admin");
        profileTierLabel.setText("ADMIN");
        configureSidebarForRole();
        showAdminPage();
    }

    @FXML
    private void exitAdminPanel() {
        adminMode = false;
        profileNameLabel.setText(signedInName);
        profileTierLabel.setText(signedInTier);
        configureSidebarForRole();
        showHomePage();
    }

    @FXML
    private void enterArtistPanel() {
        if (!artistAccess) {
            return;
        }

        artistMode = true;
        adminMode = false;
        profileNameLabel.setText("Aura Studio");
        profileTierLabel.setText("ARTIST");
        configureSidebarForRole();
        showArtistPage();
    }

    @FXML
    private void exitArtistPanel() {
        artistMode = false;
        profileNameLabel.setText(signedInName);
        profileTierLabel.setText(signedInTier);
        configureSidebarForRole();
        showHomePage();
    }

    private void showAdminPage() {
        showAdminContent(adminPage, adminDashboardNavItem);
    }

    @FXML
    private void showAdminMusicPage() {
        showAdminContent(adminMusicPage, adminMusicNavItem);
    }

    @FXML
    private void showAdminUploadPage() {
        showAdminContent(adminUploadPage, adminUploadNavItem);
    }

    @FXML
    private void showAdminAnalyticsPage() {
        showAdminContent(adminAnalyticsPage, adminAnalyticsNavItem);
    }

    @FXML
    private void showAdminFansPage() {
        showAdminContent(adminFansPage, adminFansNavItem);
    }

    @FXML
    private void showAdminSettingsPage() {
        showAdminContent(adminSettingsPage, adminSettingsNavItem);
    }

    private void showArtistPage() {
        showArtistContent(artistPage, artistDashboardNavItem);
    }

    @FXML
    private void showArtistMusicPage() {
        showArtistContent(artistMusicPage, artistMusicNavItem);
    }

    @FXML
    private void showArtistUploadPage() {
        showArtistContent(artistUploadPage, artistUploadNavItem);
    }

    @FXML
    private void showArtistAnalyticsPage() {
        showArtistContent(artistAnalyticsPage, artistAnalyticsNavItem);
    }

    @FXML
    private void showArtistFansPage() {
        showArtistContent(artistFansPage, artistFansNavItem);
    }

    @FXML
    private void togglePlayback() {
        playing = !playing;
        if (playing) {
            playerTimeline.play();
        } else {
            playerTimeline.pause();
        }
        updatePlayerUi();
    }

    @FXML
    private void nextTrack() {
        changeTrack(nextTrackIndex());
    }

    @FXML
    private void previousTrack() {
        if (elapsedSeconds > 3) {
            elapsedSeconds = 0;
            updatePlayerUi();
            return;
        }
        int previous = currentTrackIndex == 0 ? demoQueue.length - 1 : currentTrackIndex - 1;
        changeTrack(previous);
    }

    @FXML
    private void toggleShuffle() {
        shuffleEnabled = !shuffleEnabled;
        setToggleButtonActive(shuffleButton, shuffleEnabled);
    }

    @FXML
    private void toggleRepeat() {
        repeatEnabled = !repeatEnabled;
        setToggleButtonActive(repeatButton, repeatEnabled);
    }

    private void showAdminContent(javafx.scene.control.ScrollPane page, HBox activeNav) {
        hide(homeHero);
        hide(homePage);
        hide(genreTopBar);
        hide(genrePage);
        hide(libraryTopBar);
        hide(favouritesPage);
        hide(playlistTopBar);
        hide(playlistPage);
        hide(accountTopBar);
        hide(accountPage);
        show(adminTopBar);
        hideAdminPages();
        hideArtistPages();
        show(page);
        setAdminActive(activeNav);
    }

    private void showArtistContent(javafx.scene.control.ScrollPane page, HBox activeNav) {
        hide(homeHero);
        hide(homePage);
        hide(genreTopBar);
        hide(genrePage);
        hide(libraryTopBar);
        hide(favouritesPage);
        hide(playlistTopBar);
        hide(playlistPage);
        hide(accountTopBar);
        hide(accountPage);
        show(adminTopBar);
        hideAdminPages();
        hideArtistPages();
        show(page);
        setArtistActive(activeNav);
    }

    private void show(javafx.scene.Node node) {
        node.setVisible(true);
        node.setManaged(true);
    }

    private void hide(javafx.scene.Node node) {
        node.setVisible(false);
        node.setManaged(false);
    }

    private void openHome() {
        profileNameLabel.setText(signedInName);
        profileTierLabel.setText(signedInTier);
        configureSidebarForRole();
        hide(authView);
        show(homeView);
        showHomePage();
        applyLanguage();
    }

    private void configureSidebarForRole() {
        if (adminMode) {
            hide(userBrowseNav);
            hide(libraryTitleLabel);
            hide(userLibraryNav);
            hide(accountNavItem);
            show(adminBrowseNav);
            hide(artistBrowseNav);
            show(adminSettingsNavItem);
            return;
        }
        if (artistMode) {
            hide(userBrowseNav);
            hide(libraryTitleLabel);
            hide(userLibraryNav);
            hide(accountNavItem);
            hide(adminBrowseNav);
            hide(adminSettingsNavItem);
            show(artistBrowseNav);
            return;
        }

        show(userBrowseNav);
        show(libraryTitleLabel);
        show(userLibraryNav);
        show(accountNavItem);
        if (adminAccess) {
            show(adminPanelNavItem);
        } else {
            hide(adminPanelNavItem);
        }
        if (artistAccess) {
            show(artistPanelNavItem);
        } else {
            hide(artistPanelNavItem);
        }
        hide(adminBrowseNav);
        hide(artistBrowseNav);
        hide(adminSettingsNavItem);
    }

    private void hideAdminPages() {
        hide(adminPage);
        hide(adminMusicPage);
        hide(adminUploadPage);
        hide(adminAnalyticsPage);
        hide(adminFansPage);
        hide(adminSettingsPage);
    }

    private void hideArtistPages() {
        hide(artistPage);
        hide(artistMusicPage);
        hide(artistUploadPage);
        hide(artistAnalyticsPage);
        hide(artistFansPage);
    }

    private void setUserActive(HBox activeItem) {
        setActive(homeNavItem, activeItem == homeNavItem);
        setActive(genreNavItem, activeItem == genreNavItem);
        setActive(favouritesNavItem, activeItem == favouritesNavItem);
        setActive(playlistNavItem, activeItem == playlistNavItem);
        setActive(accountNavItem, activeItem == accountNavItem);
        setActive(adminPanelNavItem, activeItem == adminPanelNavItem);
        setActive(artistPanelNavItem, activeItem == artistPanelNavItem);
    }

    private void setAdminActive(HBox activeItem) {
        setActive(adminDashboardNavItem, activeItem == adminDashboardNavItem);
        setActive(adminMusicNavItem, activeItem == adminMusicNavItem);
        setActive(adminUploadNavItem, activeItem == adminUploadNavItem);
        setActive(adminAnalyticsNavItem, activeItem == adminAnalyticsNavItem);
        setActive(adminSettingsNavItem, activeItem == adminSettingsNavItem);
        setActive(exitAdminNavItem, activeItem == exitAdminNavItem);
    }

    private void setArtistActive(HBox activeItem) {
        setActive(artistDashboardNavItem, activeItem == artistDashboardNavItem);
        setActive(artistMusicNavItem, activeItem == artistMusicNavItem);
        setActive(artistUploadNavItem, activeItem == artistUploadNavItem);
        setActive(artistAnalyticsNavItem, activeItem == artistAnalyticsNavItem);
        setActive(artistFansNavItem, activeItem == artistFansNavItem);
        setActive(exitArtistNavItem, activeItem == exitArtistNavItem);
    }

    private void setActive(HBox item, boolean active) {
        item.getStyleClass().removeAll("nav-active", "nav-item");
        item.getStyleClass().add(active ? "nav-active" : "nav-item");
    }

    private void tickPlayer() {
        Track track = demoQueue[currentTrackIndex];
        elapsedSeconds++;
        if (elapsedSeconds >= track.durationSeconds()) {
            if (repeatEnabled) {
                elapsedSeconds = 0;
            } else {
                changeTrack(nextTrackIndex());
            }
        }
        updatePlayerUi();
    }

    private int nextTrackIndex() {
        if (shuffleEnabled) {
            return (currentTrackIndex + 2) % demoQueue.length;
        }
        return (currentTrackIndex + 1) % demoQueue.length;
    }

    private void changeTrack(int index) {
        currentTrackIndex = index;
        elapsedSeconds = 0;
        updatePlayerUi();
        if (playing) {
            playerTimeline.play();
        }
    }

    private void updatePlayerUi() {
        if (playerTitleLabel == null) {
            return;
        }

        Track track = demoQueue[currentTrackIndex];
        playerTitleLabel.setText(track.title());
        playerArtistLabel.setText(track.artist());
        playerCoverIcon.setText(track.coverIcon());
        playPauseButton.setText(playing ? "❚❚" : "▶");
        currentTimeLabel.setText(formatTime(elapsedSeconds));
        durationLabel.setText(formatTime(track.durationSeconds()));
        playerProgressBar.setProgress((double) elapsedSeconds / track.durationSeconds());
        if (volumeProgressBar.getProgress() < 0) {
            volumeProgressBar.setProgress(0.70);
        }
        setToggleButtonActive(shuffleButton, shuffleEnabled);
        setToggleButtonActive(repeatButton, repeatEnabled);
    }

    private void setToggleButtonActive(Button button, boolean active) {
        button.getStyleClass().removeAll("player-icon-button", "player-icon-button-active");
        button.getStyleClass().add(active ? "player-icon-button-active" : "player-icon-button");
    }

    private String formatTime(int totalSeconds) {
        int minutes = totalSeconds / 60;
        int seconds = totalSeconds % 60;
        return String.format("%d:%02d", minutes, seconds);
    }

    private boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }

    private void setLanguage(String language) {
        currentLanguage = language;
        Locale.setDefault(VIETNAMESE.equals(language) ? Locale.forLanguageTag("vi-VN") : Locale.ENGLISH);
        PREFS.put(LANGUAGE_KEY, language);
        applyLanguage();
    }

    private void applyLanguage() {
        applyLanguageToNode(authView);
        applyLanguageToNode(homeView);
        attachLanguageHandlers(authView);
        attachLanguageHandlers(homeView);
    }

    private void applyLanguageToNode(Node node) {
        if (node == null) {
            return;
        }

        if (node instanceof Labeled labeled) {
            labeled.setText(translate(labeled.getText()));
        }
        if (node instanceof TextInputControl input) {
            input.setPromptText(translate(input.getPromptText()));
        }
        if (node instanceof ScrollPane scrollPane) {
            applyLanguageToNode(scrollPane.getContent());
        }
        if (node instanceof Parent parent) {
            for (Node child : parent.getChildrenUnmodifiable()) {
                applyLanguageToNode(child);
            }
        }
    }

    private void attachLanguageHandlers(Node node) {
        if (node == null) {
            return;
        }

        if (node instanceof Button button && button.getStyleClass().contains("language-button")) {
            String text = button.getText();
            if ("English".equals(text) || "Tiếng Anh".equals(text)) {
                button.setOnAction(event -> switchToEnglish());
            } else if ("Tiếng Việt".equals(text)) {
                button.setOnAction(event -> switchToVietnamese());
            }
        }
        if (node instanceof ScrollPane scrollPane) {
            attachLanguageHandlers(scrollPane.getContent());
        }
        if (node instanceof Parent parent) {
            for (Node child : parent.getChildrenUnmodifiable()) {
                attachLanguageHandlers(child);
            }
        }
    }

    private String translate(String value) {
        if (value == null || value.isBlank()) {
            return value;
        }

        String english = VI_TO_EN.getOrDefault(value, value);
        return VIETNAMESE.equals(currentLanguage) ? EN_TO_VI.getOrDefault(english, english) : english;
    }

    private static Map<String, String> reverseTranslations() {
        Map<String, String> reverse = new LinkedHashMap<>();
        for (Map.Entry<String, String> entry : EN_TO_VI.entrySet()) {
            reverse.put(entry.getValue(), entry.getKey());
        }
        return reverse;
    }

    private static Map<String, String> createTranslations() {
        Map<String, String> map = new LinkedHashMap<>();
        map.put("Login", "Đăng nhập");
        map.put("Sign Up", "Đăng ký");
        map.put("CREATE ACCOUNT", "TẠO TÀI KHOẢN");
        map.put("JOIN SPORT T5 STUDIO", "THAM GIA SPORT T5 STUDIO");
        map.put("EMAIL OR USERNAME", "EMAIL HOẶC TÊN ĐĂNG NHẬP");
        map.put("EMAIL", "EMAIL");
        map.put("PASSWORD", "MẬT KHẨU");
        map.put("DISPLAY NAME", "TÊN HIỂN THỊ");
        map.put("Forgot Password?", "Quên mật khẩu?");
        map.put("Remember this device.", "Ghi nhớ thiết bị này.");
        map.put("SIGN IN", "ĐĂNG NHẬP");
        map.put("Complete all fields to continue.", "Điền đầy đủ thông tin để tiếp tục.");
        map.put("Enter your email and password.", "Nhập email và mật khẩu.");
        map.put("Use user, artist, or admin mock account.", "Dùng tài khoản mẫu user, artist hoặc admin.");
        map.put("Password reset is mocked in this frontend build.", "Tính năng đặt lại mật khẩu đang là mock frontend.");

        map.put("BROWSE", "KHÁM PHÁ");
        map.put("LIBRARY", "THƯ VIỆN");
        map.put("Home", "Trang chủ");
        map.put("Genre", "Thể loại");
        map.put("Top Charts", "BXH");
        map.put("Podcast", "Podcast");
        map.put("Admin Panel", "Bảng quản trị");
        map.put("Artist Panel", "Bảng nghệ sĩ");
        map.put("Favourites", "Yêu thích");
        map.put("Playlist", "Danh sách phát");
        map.put("Account Settings", "Cài đặt tài khoản");
        map.put("Admin Settings", "Cài đặt quản trị");
        map.put("Logout", "Đăng xuất");
        map.put("Back to User", "Quay lại người dùng");

        map.put("WHAT'S NEW?", "CÓ GÌ MỚI?");
        map.put("Experience the latest sonic landscape curated just for you. From underground beats to chart-topping anthems, dive into the pulse of the contemporary music scene.", "Khám phá những âm thanh mới nhất được tuyển chọn cho bạn. Từ underground đến các bản hit, hòa mình vào nhịp đập âm nhạc đương đại.");
        map.put("▶  START LISTENING", "▶  BẮT ĐẦU NGHE");
        map.put("Top Albums", "Album nổi bật");
        map.put("Latest Albums", "Album mới");
        map.put("Latest Singles", "Single mới");
        map.put("Search for tracks, artists...", "Tìm bài hát, nghệ sĩ...");
        map.put("Search analytics, tracks, or fans...", "Tìm phân tích, bài hát hoặc fan...");
        map.put("Search artists, albums, or tracks...", "Tìm nghệ sĩ, album hoặc bài hát...");
        map.put("Search in library...", "Tìm trong thư viện...");
        map.put("Search artists, songs, podcasts...", "Tìm nghệ sĩ, bài hát, podcast...");

        map.put("STORE", "CỬA HÀNG");
        map.put("All Genres", "Tất cả");
        map.put("Rock", "Rock");
        map.put("Electronic", "Điện tử");
        map.put("Jazz", "Jazz");
        map.put("Hip Hop", "Hip Hop");
        map.put("Indie", "Indie");
        map.put("Buy Now", "Mua ngay");
        map.put("Top Sellers", "Bán chạy");
        map.put("VIEW FULL CHART", "XEM BẢNG XẾP HẠNG");

        map.put("My Library", "Thư viện của tôi");
        map.put("Playlists", "Danh sách phát");
        map.put("Artists", "Nghệ sĩ");
        map.put("Albums", "Album");
        map.put("Downloaded", "Đã tải");
        map.put("Liked Songs", "Bài hát đã thích");
        map.put("Your Playlists", "Danh sách phát của bạn");
        map.put("Recently Played", "Nghe gần đây");
        map.put("VIEW ALL", "XEM TẤT CẢ");

        map.put("Subscription Plan", "Gói đăng ký");
        map.put("CURRENT PLAN", "GÓI HIỆN TẠI");
        map.put("Next billing date", "Ngày thanh toán tiếp theo");
        map.put("Manage Subscription", "Quản lý đăng ký");
        map.put("Personal Information", "Thông tin cá nhân");
        map.put("EMAIL ADDRESS", "ĐỊA CHỈ EMAIL");
        map.put("Change", "Đổi");
        map.put("Two-Factor", "Xác thực");
        map.put("Authentication", "hai lớp");
        map.put("Purchase History", "Lịch sử mua hàng");
        map.put("Language", "Ngôn ngữ");
        map.put("Choose the display language for this device.", "Chọn ngôn ngữ hiển thị cho thiết bị này.");
        map.put("English", "Tiếng Anh");
        map.put("Tiếng Việt", "Tiếng Việt");
        map.put("Edit Profile", "Sửa hồ sơ");
        map.put("Load More Transactions", "Tải thêm giao dịch");

        map.put("Dashboard", "Tổng quan");
        map.put("Users", "Người dùng");
        map.put("Music Review", "Duyệt nhạc");
        map.put("Analytics", "Phân tích");
        map.put("My Music", "Nhạc của tôi");
        map.put("Upload", "Tải lên");
        map.put("Fans", "Người hâm mộ");
        map.put("Admin Dashboard", "Bảng quản trị");
        map.put("Platform overview: users, music submissions, revenue, and moderation queue.", "Tổng quan nền tảng: người dùng, nhạc gửi duyệt, doanh thu và hàng chờ kiểm duyệt.");
        map.put("Export Report", "Xuất báo cáo");
        map.put("TOTAL USERS", "TỔNG NGƯỜI DÙNG");
        map.put("PENDING REVIEWS", "CHỜ DUYỆT");
        map.put("PLATFORM REVENUE", "DOANH THU NỀN TẢNG");
        map.put("Platform Activity", "Hoạt động nền tảng");
        map.put("Moderation Queue", "Hàng chờ kiểm duyệt");
        map.put("Latest Actions", "Hoạt động gần đây");
        map.put("View Audit Log", "Xem nhật ký");
        map.put("User Management", "Quản lý người dùng");
        map.put("Manage listeners, artists, admins, bans, and verification status.", "Quản lý người nghe, nghệ sĩ, admin, khóa tài khoản và xác minh.");
        map.put("Create User", "Tạo người dùng");
        map.put("ACTIVE USERS", "NGƯỜI DÙNG HOẠT ĐỘNG");
        map.put("ARTIST ACCOUNTS", "TÀI KHOẢN NGHỆ SĨ");
        map.put("SUSPENDED", "ĐÃ KHÓA");
        map.put("All Users", "Tất cả người dùng");
        map.put("Listeners", "Người nghe");
        map.put("Admins", "Quản trị");
        map.put("Music Review", "Duyệt nhạc");
        map.put("Approve, reject, or flag submitted tracks before they go live.", "Duyệt, từ chối hoặc gắn cờ bài hát trước khi phát hành.");
        map.put("Review Guidelines", "Quy định duyệt");
        map.put("PENDING", "CHỜ DUYỆT");
        map.put("APPROVED THIS WEEK", "ĐÃ DUYỆT TUẦN NÀY");
        map.put("FLAGGED", "BỊ GẮN CỜ");
        map.put("Review Queue", "Hàng chờ duyệt");
        map.put("Approve", "Duyệt");
        map.put("Reject", "Từ chối");
        map.put("Flag", "Gắn cờ");

        map.put("Artist Dashboard", "Bảng nghệ sĩ");
        map.put("Track your releases, listeners, earnings, and audience growth.", "Theo dõi phát hành, người nghe, doanh thu và tăng trưởng khán giả.");
        map.put("Upload New Music", "Tải nhạc mới");
        map.put("TOTAL REVENUE", "TỔNG DOANH THU");
        map.put("MONTHLY LISTENERS", "NGƯỜI NGHE THÁNG");
        map.put("FOLLOWERS GROWTH", "TĂNG TRƯỞNG FOLLOWER");
        map.put("Revenue Performance", "Hiệu suất doanh thu");
        map.put("Recent Releases", "Phát hành gần đây");
        map.put("Upload Music", "Tải nhạc lên");
        map.put("Submit a release package for admin review. Frontend mock only.", "Gửi gói phát hành để admin duyệt. Chỉ là mock frontend.");
        map.put("Artist Analytics", "Phân tích nghệ sĩ");
        map.put("Streams, saves, listener retention, and top countries.", "Lượt nghe, lượt lưu, tỷ lệ nghe hết và quốc gia nổi bật.");
        map.put("STREAMS", "LƯỢT NGHE");
        map.put("SAVES", "LƯỢT LƯU");
        map.put("AVG COMPLETION", "TỶ LỆ NGHE HẾT");

        return map;
    }

    private record Track(String title, String artist, String coverIcon, int durationSeconds) {
    }
}
