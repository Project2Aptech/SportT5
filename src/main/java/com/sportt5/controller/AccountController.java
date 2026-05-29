package com.sportt5.controller;

import com.sportt5.App;
import com.sportt5.model.Users;
import com.sportt5.service.AuthService;
import com.sportt5.session.UserSession;
import com.sportt5.util.ApiClient;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.IOException;


public class AccountController {
    //UI element
    @FXML private Label displayNameLabel;
    @FXML private Label emailLabel;
    @FXML private Label checkEmail;
    @FXML private Label planLabel;
    @FXML private Label billingDateLabel;
    @FXML private Label priceLabel;
    @FXML private ImageView avatarImageView;

    //icon loading
    @FXML private ProgressIndicator progressIndicator;

    private final AuthService authService = new AuthService();

    @FXML
    public void initialize() {
        loadUserProfile();
    }
    public void handleEditProfile(){
        try {
            FXMLLoader loader = new FXMLLoader(App.class.getResource("/com.sportt5/view/pages/edit-profile.fxml"));
            Parent root = loader.load();
            EditProfileController ctrl = loader.getController();

            ctrl.initData(UserSession.getInstance().getCurrentUser());
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Edit Profile");
            Scene scene = new Scene(root);
            scene.getStylesheets().add(
                    App.class.getResource("/com.sportt5/css/editProfile.css").toExternalForm()
            );

            stage.setScene(scene);
            stage.showAndWait();

            loadUserProfile();
        } catch (IOException e) {
            showError("Unable to open edit dialog: " + e.getMessage());
        }
    }

    public void loadUserProfile(){
        UserSession session = UserSession.getInstance();
        int userId = (session != null) ? session.getCurrentUserId() : -1;


        new Thread(()->{
            try{
            Users fresh = authService.getUserById(userId);
            UserSession.setCurrentUser(fresh);

            if (fresh == null) {
                showError("User is null");
            }

            Platform.runLater(() -> {
                bindToUi(fresh);
            });

            } catch (Exception e) {
                Platform.runLater(() -> {
                    showError("Failed to load profile: " + e.getMessage());
                });
            }

        }).start();
    }
    private void bindToUi(Users u) {
        displayNameLabel.setText((u.getDisplayName() != null ?  u.getDisplayName() : "User"));
        emailLabel.setText(nonNull(u.getEmail()));
        planLabel.setText(u.getAccountType() != null ? u.getAccountType().name() : "NORMAL");
        checkEmail.setText((u.getEmail() != null ? "V" : "X"));
        System.out.println("Avatar URL = " + u.getAvatarUrl());
        String avatarUrl = ApiClient.resolveUrl(u.getAvatarUrl());
        if (avatarUrl != null) {
            avatarImageView.setImage(new Image(avatarUrl, true));
        } else {
            avatarImageView.setImage(new Image(App.class.getResource("/com.sportt5/img/avatar.png").toExternalForm()));
        }
    }

    private String nonNull(String s) {
        return s == null ? "" : s;
    }


    @FXML
    public void handleSignOut(ActionEvent event) {
        UserSession.cleanSession();

        try {
            Parent root = FXMLLoader.load(App.class.getResource("/com.sportt5/view/auth/auth-view.fxml"));

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 1220, 810);
            scene.getStylesheets().add(App.class.getResource("/com.sportt5/css/style.css").toExternalForm());

            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void showError(String msg) {
        displayNameLabel.setText(msg);
    }
}
