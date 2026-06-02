package com.sportt5.controller.pages;

import com.sportt5.controller.AppController;
import com.sportt5.model.SongResponse;
import com.sportt5.model.Songs;
import com.sportt5.model.UserResponse;
import com.sportt5.model.Users;
import com.sportt5.service.AdminService;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;

public class AdminDashboardController {
    private final AdminService adminService = new AdminService();
    @FXML private GridPane userGridDashboard;
    @FXML private Label totalUser;
    @FXML private Label trackUploaded;
    private AppController appController;

    @FXML
    public void initialize() {
        loadUserProfile();
    }

    public void loadUserProfile(){
        new Thread(() -> {
            try {
                UserResponse response = adminService.getUser();
                List<Users> users = response.getContent();

                SongResponse responseSong = adminService.getSong();
                List<Songs> songs = responseSong.getContent();

                Platform.runLater(() -> {
                    totalUser.setText(String.valueOf(users.size()));
                    trackUploaded.setText(String.valueOf(songs.size()));
                    renderUsersDashboard(users);
                });
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }).start();
    }
    @FXML
    private void handleViewAllUsers(ActionEvent event) {
//        System.out.println("appController = " + appController);
//
//        if (appController != null) {
//            appController.showAdminUserPage();
//        }
    }

    private void renderUsersDashboard(List<Users> users) {
        userGridDashboard.getChildren().removeIf(node -> {
            Integer row = GridPane.getRowIndex(node);

            if (row == null) {
                return false;
            }

            return row > 0;
        });

        List<Users> latestUsers = users.stream()
                .filter(u -> u.getCreatedAt() != null)
                .sorted(Comparator.comparing(Users::getCreatedAt).reversed())
                .limit(10)
                .toList();
        int row = 1;

        for (Users user : latestUsers) {
            HBox nameBox = new HBox(12);

            VBox infoBox = new VBox(2);

            Label nameLabel = new Label(user.getDisplayName());
            nameLabel.getStyleClass().add("table-title");

            Label statusLabel = new Label(
                    user.isActive() ? "Active" : "Inactive"
            );
            statusLabel.getStyleClass().add("table-text");

            infoBox.getChildren().addAll(nameLabel, statusLabel);
            nameBox.getChildren().addAll(infoBox);

            Label emailLabel = new Label(user.getEmail());

            String plan = user.getAccountType() != null
                    ? user.getAccountType().name()
                    : "NORMAL";

            Label planLabel = new Label(plan);

            planLabel.getStyleClass().add(
                    switch (plan) {
                        case "PRO" -> "plan-pro";
                        case "PREMIUM" -> "plan-premium";
                        default -> "plan-normal";
                    }
            );

            Label joinedLabel = new Label(
                    user.getCreatedAt().format(
                            DateTimeFormatter.ofPattern("dd/MM/yyyy")
                    )
            );

            Label actionLabel = new Label("•••");

            emailLabel.getStyleClass().add("table-text");
            joinedLabel.getStyleClass().add("table-text");
            actionLabel.getStyleClass().add("row-action");

            userGridDashboard.add(nameBox, 0, row);
            userGridDashboard.add(emailLabel, 1, row);
            userGridDashboard.add(planLabel, 2, row);
            userGridDashboard.add(joinedLabel, 3, row);
            userGridDashboard.add(actionLabel, 4, row);

            row++;
        }
    }
}
