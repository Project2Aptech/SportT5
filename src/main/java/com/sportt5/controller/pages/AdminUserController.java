package com.sportt5.controller.pages;

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

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;

public class AdminUserController {
    private final AdminService adminService = new AdminService();
    @FXML private GridPane userGrid;
    @FXML private Label totalUser;
    @FXML private Label trackUploaded;

    @FXML
    public void initialize() {
        loadUserProfile();
    }

    public void loadUserProfile(){
        new Thread(() -> {
            try {
                UserResponse response = adminService.getUser();
                List<Users> users = response.getContent();
                System.out.println(users.size());

                Platform.runLater(() -> {
                    renderUsers(users);
                });
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }).start();
    }
    private void renderUsers(List<Users> users) {
        userGrid.getChildren().removeIf(node -> {
            Integer row = GridPane.getRowIndex(node);
            return row != null && row > 0;
        });

        int row = 1;

        for (Users user : users) {
            HBox nameBox = new HBox(12);
//            StackPane avatar = new StackPane();
//            avatar.setPrefSize(34, 34);
//            avatar.getStyleClass().addAll("admin-thumb", "thumb-pink");

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

            Label planLabel = new Label(
                    user.getAccountType() != null
                            ? user.getAccountType().toString()
                            : "NORMAL"
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

            userGrid.add(nameBox, 0, row);
            userGrid.add(emailLabel, 1, row);
            userGrid.add(planLabel, 2, row);
            userGrid.add(joinedLabel, 3, row);
            userGrid.add(actionLabel, 4, row);

            row++;
        }
    }
}
