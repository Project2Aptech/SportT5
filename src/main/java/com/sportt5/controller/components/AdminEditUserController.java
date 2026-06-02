package com.sportt5.controller.components;

import com.sportt5.model.Users;
import com.sportt5.model.enums.AccountType;
import com.sportt5.model.enums.Roles;
import com.sportt5.service.AdminService;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AdminEditUserController {
    @FXML private Label trackUploaded;
    @FXML private TextField usernameField;
    @FXML private TextField emailField;
    @FXML private ComboBox<AccountType> accountTypeBox;
    @FXML private ComboBox<Roles> roleTypeBox;
    private final AdminService adminService = new AdminService();
    private int userId;


    @FXML
    public void initialize()
    {
        roleTypeBox.getItems().addAll(
                Roles.USER,
                Roles.ARTIST,
                Roles.ADMIN
        );

        accountTypeBox.getItems().addAll(
                AccountType.NORMAL,
                AccountType.PRO,
                AccountType.PREMIUM
        );
    }

    public void setUser(Users user) {
        this.userId = user.getId();
        usernameField.setText(user.getUsername());
        emailField.setText(user.getEmail());

        if (user.getAccountType() != null) {
            accountTypeBox.setValue(user.getAccountType());
        }
        if (user.getRole() != null) {
            roleTypeBox.setValue(user.getRole());
        }
    }

    public void handleSave(){
        try {
            AccountType accountType = accountTypeBox.getValue();
            Roles role = roleTypeBox.getValue();

            if (accountType == null || role == null) {
                System.out.println("Please select account type and role");
                return;
            }

            boolean success = adminService.updateUserRole(
                    userId,
                    accountType.name(),
                    role.name()
            );

            if (success) {
                System.out.println("Update user success");

                Stage stage = (Stage) usernameField.getScene().getWindow();
                stage.close();
            } else {
                System.out.println("Update failed");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public void handleCancel(){
        Stage stage = (Stage) usernameField.getScene().getWindow();
        stage.close();
    }
}
