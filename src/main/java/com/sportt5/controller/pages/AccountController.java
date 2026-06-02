package com.sportt5.controller.pages;

import com.sportt5.App;
import com.sportt5.controller.EditProfileController;
import com.sportt5.controller.components.SidebarController;
import com.sportt5.controller.components.SubscriptionController;
import com.sportt5.model.Users;
import com.sportt5.model.enums.Roles;
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
import javafx.stage.StageStyle;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.Locale;


public class AccountController {
    //UI element
    @FXML private Label displayNameLabel,accountTypeHeader;
    @FXML private Label emailLabel;
    @FXML private Label checkEmail;
    @FXML private Label planLabel;
    @FXML private Label billingDateLabel;
    @FXML private Label priceLabel;
    @FXML private ImageView avatarImageView;

    //icon loading
    @FXML private ProgressIndicator progressIndicator;

    private final AuthService authService = new AuthService();
//    private SidebarController sidebarController;
//
//    public SidebarController getSidebarController() {
//        return sidebarController;
//    }

    @FXML
    public void initialize() {
        loadAccountType();
        loadUserProfile();
    }

    @FXML
    public void handleBuySubscription(){
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com.sportt5/view/users/subscription-plans.fxml"));
            DialogPane pane = loader.load();
            pane.getStylesheets().add(
                    App.class.getResource("/com.sportt5/css/dialog.css").toExternalForm()
            );
            Dialog<Void> dialog = new Dialog<>();
            dialog.setDialogPane(pane);
            dialog.initOwner(avatarImageView.getScene().getWindow());
            dialog.initModality(Modality.APPLICATION_MODAL);
            dialog.initStyle(StageStyle.UTILITY);

            SubscriptionController ctrl = loader.getController();
            ctrl.initData(UserSession.getInstance().getCurrentUser());
            dialog.showAndWait();

            // Refresh UI to reflect new subscription tier
            loadUserProfile();

        } catch (Exception e) {
            showError("Error " + e.getMessage());
            System.out.println(e.getMessage());
        }


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

    public void loadAccountType(){
        Users user = UserSession.getInstance().getCurrentUser();

        if (planLabel != null && user != null) {
            planLabel.setText(user.getAccountType() != null ? user.getAccountType().name() : "NORMAL");
        }
        if (accountTypeHeader != null && user != null) {
            accountTypeHeader.setText(
                    String.format("%s Member",
                            user.getAccountType() != null ? user.getAccountType().name() : "NORMAL"
                    )
            );
        }
        switch(user.getAccountType()){
            case PRO -> priceLabel.setText("9.99/month");
            case PREMIUM  -> priceLabel.setText("14.99/month");
            default -> priceLabel.setText("Free");
        }
        emailLabel.setText(nonNull(user.getEmail()));
        checkEmail.setText((user.getEmail() != null ? "V" : "X"));
    }

    public void loadUserProfile(){
        UserSession session = UserSession.getInstance();
        int userId = (session != null) ? session.getCurrentUserId() : -1;

        new Thread(() -> {
            try {
                Users fresh = authService.getUserById(userId);
                UserSession.setCurrentUser(fresh);
                Users user = UserSession.getInstance().getCurrentUser();

                System.out.println("====fresh======\n" + user);

                Platform.runLater(() -> {
                    bindToUi(user);
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
//        String planText = (u.getAccountType() != null) ? u.getAccountType().name() : "UNKNOWN";
//        System.out.println("Setting planLabel to: " + planText);
//        planLabel.setText(planText);

        System.out.println("Avatar URL = " + u.getAvatarUrl());
        String avatarUrl = ApiClient.resolveUrl(u.getAvatarUrl());
        if (avatarUrl != null) {
            avatarImageView.setImage(new Image(avatarUrl, true));
        } else {
            avatarImageView.setImage(new Image(App.class.getResource("/com.sportt5/img/avatar.png").toExternalForm()));
        }

        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern("MMMM yyyy", Locale.ENGLISH);

        if (u.getCreatedAt() != null) {
            billingDateLabel.setText(
                    "Member since " + u.getCreatedAt().format(formatter)
            );
        } else {
            billingDateLabel.setText("Member since N/A");
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
