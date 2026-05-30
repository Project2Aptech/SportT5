package com.sportt5.controller;

import com.sportt5.model.Users;
import com.sportt5.model.enums.Roles;
import com.sportt5.session.UserSession;
import com.sportt5.util.ApiClient;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;


public class SidebarController {
    //Home Sidebar
    @FXML private Label profileNameLabel, profileTierLabel, brandLabel;
    @FXML private ImageView sidebarAvatar;
    @FXML private HBox homeNavItem, libraryItem, albumItem, artistItem, accountNavItem, adminItem;
    //Artist Sidebar
    @FXML private HBox artistDashboardNavItem, artistMusicNavItem, artistUploadNavItem, artistAnalyticsNavItem, artistFansNavItem, exitArtistNavItem;
    //Admin Sidebar
    @FXML private HBox adminDashboardNavItem, adminUserNavItem, adminReviewNavItem, adminAnalyticsNavItem, exitAdminNavItem;
    //App controller
    private AppController appController;

    public void setAppController(AppController appController) { this.appController = appController; }
    public HBox getAccountNavItem() { return accountNavItem; }
    public HBox getHomeNavItem() { return homeNavItem; }
    public HBox getLibraryItem() { return libraryItem; }
    public HBox getAlbumItem() { return albumItem; }
    public HBox getArtistItem() { return artistItem; }
    public HBox getAdminItem() { return adminItem; }
    public HBox getArtistDashboardNavItem() { return artistDashboardNavItem; }
    public HBox getArtistMusicNavItem() { return artistMusicNavItem; }
    public HBox getArtistUploadNavItem() { return artistUploadNavItem; }
    public HBox getArtistAnalyticsNavItem() { return artistAnalyticsNavItem; }
    public HBox getArtistFansNavItem() { return artistFansNavItem; }
    public HBox getAdminDashboardNavItem() { return adminDashboardNavItem; }
    public HBox getAdminUserNavItem() { return adminUserNavItem; }
    public HBox getAdminReviewNavItem() { return adminReviewNavItem; }
    public HBox getAdminAnalyticsNavItem() { return adminAnalyticsNavItem; }

    public void resetNavStyles() {
        if (homeNavItem != null) {
        accountNavItem.getStyleClass().setAll("nav-item");
        homeNavItem.getStyleClass().setAll("nav-item");
        libraryItem.getStyleClass().setAll("nav-item");
        albumItem.getStyleClass().setAll("nav-item");
        artistItem.getStyleClass().setAll("nav-item");
        }
        else if (artistDashboardNavItem != null) {
            artistDashboardNavItem.getStyleClass().setAll("nav-item");
            artistMusicNavItem.getStyleClass().setAll("nav-item");
            artistUploadNavItem.getStyleClass().setAll("nav-item");
            artistAnalyticsNavItem.getStyleClass().setAll("nav-item");
            artistFansNavItem.getStyleClass().setAll("nav-item");
        } else if (adminDashboardNavItem != null) {
            adminDashboardNavItem.getStyleClass().setAll("nav-item");
            adminUserNavItem.getStyleClass().setAll("nav-item");
            adminReviewNavItem.getStyleClass().setAll("nav-item");
            adminAnalyticsNavItem.getStyleClass().setAll("nav-item");
        }
    }

    @FXML
    public void initialize() {
        Users user = UserSession.getInstance().getCurrentUser();
        Roles role = user != null ? user.getRole() : null;

        if (profileNameLabel != null && user != null) {
            profileNameLabel.setText(user.getDisplayName() != null ? user.getDisplayName() : user.getUsername());
        }
        if (profileTierLabel != null && user != null) {
            profileTierLabel.setText(user.getAccountType() != null ? user.getAccountType().name() : "NORMAL");
        }
        if (sidebarAvatar != null && user != null) {
            String url = ApiClient.resolveUrl(user.getAvatarUrl());
            if (url != null) sidebarAvatar.setImage(new Image(url, true));
        }

        //<-----Home sidebar----->
        if (brandLabel != null) {
            brandLabel.setOnMouseClicked(e -> {
                if (appController != null) appController.showHomePage();
            });
        }
        if (profileNameLabel != null) {
            profileNameLabel.setOnMouseClicked(e -> {
                if (appController != null) appController.showAccountPage();
            });
        }
        if (homeNavItem != null) {
            homeNavItem.setOnMouseClicked(e -> {
                if (appController != null) appController.showHomePage();
            });
        }
        if (libraryItem != null) {
            libraryItem.setOnMouseClicked(e -> {
                if (appController != null) appController.showLibraryPage();
            });
        }
        if (albumItem != null) {
            albumItem.setOnMouseClicked(e -> {
                if (appController != null) appController.showAlbumPage();
            });
        }
        if (artistItem != null) {

            boolean canShowArtist =
                    role == Roles.ADMIN ||
                            role == Roles.ARTIST;

            artistItem.setVisible(canShowArtist);
            artistItem.setManaged(canShowArtist);

            if (canShowArtist) {
                artistItem.setOnMouseClicked(e -> {
                    if (appController != null) {
                        appController.showArtistSideBar();
                    }
                });
            }
        }
        if (accountNavItem != null) {
            accountNavItem.setOnMouseClicked(e -> {
                if (appController != null) appController.showAccountPage();
            });
        }
        if (adminItem != null) {

            boolean isAdmin = role == Roles.ADMIN;

            adminItem.setVisible(isAdmin);
            adminItem.setManaged(isAdmin);

            if (isAdmin) {
                adminItem.setOnMouseClicked(e -> {
                    if (appController != null) {
                        appController.showAdminSideBar();
                    }
                });
            }
        }

        //<-----Artist sidebar----->
        if (artistDashboardNavItem != null) {
            artistDashboardNavItem.setOnMouseClicked(e -> {
                if (appController != null) appController.showArtistDashBoardPage();
            });
        }
        if (artistMusicNavItem != null) {
            artistMusicNavItem.setOnMouseClicked(e -> {
                if (appController != null) appController.showArtistMusicPage();
            });
        }
        if (artistUploadNavItem != null) {
            artistUploadNavItem.setOnMouseClicked(e -> {
                if (appController != null) appController.showArtistUploadPage();
            });
        }
        if (artistAnalyticsNavItem != null) {
            artistAnalyticsNavItem.setOnMouseClicked(e -> {
                if (appController != null) appController.showArtistAnalyticsPage();
            });
        }
        if (artistFansNavItem != null) {
            artistFansNavItem.setOnMouseClicked(e -> {
                if (appController != null) appController.showArtistFanPage();
            });
        }
        if (exitArtistNavItem != null) {
            exitArtistNavItem.setOnMouseClicked(e -> {
                if (appController != null) appController.showHomeSideBar();
            });
        }
        //<-----Admin sidebar----->
        if (adminDashboardNavItem != null) {
            adminDashboardNavItem.setOnMouseClicked(e -> {
                if (appController != null) appController.showAdminDashBoardPage();
            });
        }
        if (adminUserNavItem != null) {
            adminUserNavItem.setOnMouseClicked(e -> {
                if (appController != null) appController.showAdminUserPage();
            });
        }
        if (adminReviewNavItem != null) {
            adminReviewNavItem.setOnMouseClicked(e -> {
                if (appController != null) appController.showAdminReviewPage();
            });
        }
        if (adminAnalyticsNavItem != null) {
            adminAnalyticsNavItem.setOnMouseClicked(e -> {
                if (appController != null) appController.showAdminAnalyticsPage();
            });
        }
        if (exitAdminNavItem != null) {
            exitAdminNavItem.setOnMouseClicked(e -> {
                if (appController != null) appController.showHomeSideBar();
            });
        }
    }
}
