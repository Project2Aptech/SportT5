package com.sportt5.controller.pages;

import com.sportt5.controller.components.AdminEditUserController;
import com.sportt5.model.UserResponse;
import com.sportt5.model.Users;
import com.sportt5.model.enums.AccountType;
import com.sportt5.model.enums.Roles;
import com.sportt5.service.AdminService;
import com.sportt5.session.UserSession;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class AdminUserController {
    private final AdminService adminService = new AdminService();
    @FXML private GridPane userGrid;
    @FXML private Label totalUser;

    @FXML
    public void initialize() {
        Users currentUser = UserSession.getInstance().getCurrentUser();

        if (currentUser == null ||
                currentUser.getRole() != Roles.ADMIN) {
            return;
        }
        loadUserProfile();
    }

    public void loadUserProfile(){
        new Thread(() -> {
            try {
                UserResponse response = adminService.getUser();
                List<Users> users = response.getContent();
                users.forEach(u ->
                        System.out.println("Test"+
                                u.getUsername() + " -> " + u.isActive()
                        )
                );
                Platform.runLater(() -> {
                    renderUsers(users);
                });
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }).start();
    }

    private void openEditUserDialog(Users user) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource(
                            "/com.sportt5/view/admin/edit-user.fxml"
                    )
            );

            Parent root = loader.load();

            root.getStylesheets().add(
                    getClass()
                            .getResource("/com.sportt5/css/admin.css")
                            .toExternalForm()
            );
            AdminEditUserController controller = loader.getController();
            controller.setUser(user);
            Stage stage = new Stage();
            stage.setTitle("Edit User");
            stage.setScene(new Scene(root));

            stage.initModality(Modality.APPLICATION_MODAL);

            stage.showAndWait();

            loadUserProfile();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void statusUser(Users user) {
        String action = user.isActive()
                ? "deactivate"
                : "activate";

        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Confirm");
        confirm.setHeaderText(null);
        confirm.setContentText(
                "Are you sure you want to "
                        + action
                        + " user "
                        + user.getUsername()
                        + "?"
        );

        if (confirm.showAndWait().orElse(ButtonType.CANCEL) != ButtonType.OK) {
            return;
        }

        try {
            boolean success =
                    adminService.updateDeactivateUser(user.getId(),action);

            if (success) {
                loadUserProfile();
            } else {
                new Alert(
                        Alert.AlertType.ERROR,
                        "Failed to " + action + " user."
                ).showAndWait();
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void handleDeleteUser(Users user) {
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Delete User");
        confirm.setHeaderText("Delete User");
        confirm.setContentText(
                "Are you sure you want to delete user \""
                        + user.getUsername()
                        + "\"?\n\nThis action cannot be undone."
        );

        Optional<ButtonType> result = confirm.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                boolean success = adminService.deleteUser(user.getId());

                if (success) {
                    loadUserProfile();
                }
                else {
                    new Alert(Alert.AlertType.ERROR, "Failed to delete user."
                    ).showAndWait();
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }


        private void renderUsers(List<Users> users) {
        if (userGrid == null) {
            System.out.println("userGrid chưa inject xong!");
            return;
        }

        userGrid.getChildren().removeIf(node -> {
            Integer row = GridPane.getRowIndex(node);
            return row != null && row > 0;
        });


        int row = 1;

        for (Users user : users) {
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

            String role = user.getRole() != null
                    ? user.getRole().toString()
                    : "USER";

            Label roleLabel = new Label(role);

            String active = user.isActive() ? "ACTIVE" : "INACTIVE";
            Label activeLabel = new Label(active);

            activeLabel.getStyleClass().add(
                    user.isActive()
                            ? "status-active"
                            : "status-inactive"
            );

            String plan = user.getAccountType() != null
                    ? user.getAccountType().name()
                    : "NORMAL";

            Label planLabel = new Label(plan);
            roleLabel.getStyleClass().add("role-" + role.toLowerCase());

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

            HBox actionBox = new HBox(8);

            Button editBtn = new Button("️Edit");
            Button statusBtn = new Button(
                    user.isActive() ? "🚫" : "🔓"
            );

            Button deleteBtn = new Button("Delete️");

            editBtn.getStyleClass().add("action-edit-btn");
            statusBtn.getStyleClass().add("action-deactivate-btn");
            deleteBtn.getStyleClass().add("action-delete-btn");

            statusBtn.setOnAction(e -> statusUser (user));
            editBtn.setOnAction(e -> openEditUserDialog(user));
            deleteBtn.setOnAction(e -> handleDeleteUser(user));

            actionBox.getChildren().addAll(editBtn,statusBtn, deleteBtn);

            emailLabel.getStyleClass().add("table-text");
            joinedLabel.getStyleClass().add("table-text");

            userGrid.add(nameBox, 0, row);
            userGrid.add(emailLabel, 1, row);
            userGrid.add(roleLabel, 2, row);
            userGrid.add(activeLabel, 3, row);
            userGrid.add(planLabel, 4, row);
            userGrid.add(joinedLabel, 5, row);
            userGrid.add(actionBox, 6, row);

            row++;
        }
    }

}
