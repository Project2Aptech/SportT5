package com.sportt5.controller.components;

import com.sportt5.controller.AppController;
import com.sportt5.model.Albums;
import com.sportt5.model.Songs;
import com.sportt5.model.Users;
import com.sportt5.service.LibraryService;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Popup;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TopBarController {

    @FXML private StackPane homeHero;
    @FXML private Button listenButton;
    @FXML private HBox libraryTopBar;
    @FXML private HBox accountTopBar;
    @FXML private HBox artistTopBar;
    @FXML private HBox adminTopBar;

    @FXML private TextField homeSearchField;
    @FXML private TextField librarySearchField;

    private AppController appController;

    private final LibraryService libraryService = new LibraryService();
    private List<Users> allArtists = new ArrayList<>();
    private List<Albums> allAlbums = new ArrayList<>();
    private Map<Integer, String> genresMap = new HashMap<>();

    private final Popup suggestionPopup = new Popup();
    private final VBox suggestionVBox = new VBox();
    private boolean programmaticChange = false;

    public void setAppController(AppController appController) {
        this.appController = appController;
    }

    @FXML
    public void initialize() {
        suggestionVBox.getStyleClass().add("search-popup");
        suggestionVBox.setMinWidth(320);
        suggestionPopup.getContent().add(suggestionVBox);

        new Thread(() -> {
            try {
                allArtists = libraryService.getAllArtists();
                allAlbums = libraryService.getAllAlbums();
                genresMap = libraryService.getGenresMap();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();

        if (listenButton != null)
            listenButton.setOnAction(e -> { if (appController != null) appController.showLibrarySongsAll(); });
        if (homeSearchField != null) setupSearchField(homeSearchField);
        if (librarySearchField != null) setupSearchField(librarySearchField);
    }

    private void setupSearchField(TextField field) {
        PauseTransition pause = new PauseTransition(Duration.millis(350));

        field.textProperty().addListener((observable, oldValue, newValue) -> {
            if (programmaticChange) { programmaticChange = false; return; }
            String kw = newValue.trim();
            pause.setOnFinished(event -> {showSuggestions(field, kw);});
            pause.playFromStart();
        });

        field.setOnAction(event -> {
            String keyword = field.getText().trim();
            suggestionPopup.hide();
            if (!keyword.isBlank() && appController != null) {
                programmaticChange = true;
                homeSearchField.setText(keyword);
                programmaticChange = true;
                librarySearchField.setText(keyword);
                appController.showLibraryPageWithSearch(keyword, "song");
            }
        });

        field.focusedProperty().addListener((obs, wasFocused, isFocused) -> {
            if (!isFocused) {
                PauseTransition hideDelay = new PauseTransition(Duration.millis(200));
                hideDelay.setOnFinished(e -> suggestionPopup.hide());
                hideDelay.play();
            }
        });
    }

    private void showSuggestions(TextField field, String keyword) {
        if (keyword.length() < 2) {
            suggestionPopup.hide();
            return;
        }
        String kw = keyword.toLowerCase();

        List<Users> matchedArtist = allArtists.stream()
                .filter(u -> u.getDisplayName() != null && u.getDisplayName().toLowerCase().contains(kw))
                .limit(3)
                .collect(Collectors.toList());

        List<String> matchesGenres = genresMap.values().stream()
                .filter(g -> g.toLowerCase().contains(kw))
                .limit(3)
                .collect(Collectors.toList());

        List<Albums> matchedAlbums = allAlbums.stream()
                .filter(a -> a.getTitle() != null && a.getTitle().toLowerCase().contains(kw))
                .limit(3)
                .collect(Collectors.toList());

        new Thread(() -> {
            try {
                List<Songs> matchedSongs = libraryService.searchSongs(keyword);
                List<Songs> limitedSongs = matchedSongs.stream().limit(5).collect(Collectors.toList());
                Platform.runLater(() -> buildSuggestions(field, limitedSongs, matchedArtist, matchedAlbums, matchesGenres));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void buildSuggestions(TextField field, List<Songs> songs, List<Users> artists, List<Albums> albums, List<String> genres) {
        suggestionVBox.getChildren().clear();
        boolean hasAny = false;
        if (!songs.isEmpty()) {
            hasAny = true;
            suggestionVBox.getChildren().add(sectionHeader("Songs"));
            for (Songs song : songs) {
                Label item = suggestionItem(song.getTitle());
                item.setOnMouseClicked(e -> selectSuggestion(song.getTitle(), "song"));
                suggestionVBox.getChildren().add(item);
            }
        }

        if (!albums.isEmpty()) {
            hasAny = true;
            suggestionVBox.getChildren().add(sectionHeader("Albums"));
            for (Albums album : albums) {
                Label item = suggestionItem(album.getTitle());
                item.setOnMouseClicked(e -> selectSuggestion(album.getTitle(), "album"));
                suggestionVBox.getChildren().add(item);
            }
        }

        if (!artists.isEmpty()) {
            hasAny = true;
            suggestionVBox.getChildren().add(sectionHeader("Artist"));
            for (Users artist : artists) {
                Label item = suggestionItem(artist.getDisplayName());
                item.setOnMouseClicked(e -> selectSuggestion(artist.getDisplayName(), "artist"));
                suggestionVBox.getChildren().add(item);
            }
        }

        if (!genres.isEmpty()) {
            hasAny = true;
            suggestionVBox.getChildren().add(sectionHeader("Genre"));
            for (String genre : genres) {
                Label item = suggestionItem(genre);
                item.setOnMouseClicked(e -> selectSuggestion(genre, "genre"));
                suggestionVBox.getChildren().add(item);
            }
        }

        if (hasAny) {
            Bounds bounds = field.localToScreen(field.getBoundsInLocal());
            if (bounds != null) {
                suggestionPopup.show(field,bounds.getMinX(),bounds.getMaxY() + 4);
            }
            else {
                suggestionPopup.hide();
            }
        }
    }

    private Label sectionHeader(String string) {
        Label label = new Label(string);
        label.getStyleClass().add("suggestion-header");
        return label;
    }

    private Label suggestionItem(String string) {
        Label label = new Label("  " + string);
        label.getStyleClass().add("suggestion-item");
        label.setMaxWidth(Double.MAX_VALUE);
        return label;
    }

    private void selectSuggestion(String value, String type) {
        suggestionPopup.hide();
        programmaticChange = true;
        homeSearchField.setText(value);
        programmaticChange = true;
        librarySearchField.setText(value);
        if (appController != null) {
            appController.showLibraryPageWithSearch(value, type);
        }
    }

    public StackPane getHomeHero()  { return homeHero; }
    public HBox getLibraryTopBar()  { return libraryTopBar; }
    public HBox getAccountTopBar()  { return accountTopBar; }
    public HBox getArtistTopBar()   { return artistTopBar; }
    public HBox getAdminTopBar()    { return adminTopBar; }


}
