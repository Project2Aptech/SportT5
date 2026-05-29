package com.sportt5.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.sportt5.model.Users;
import com.sportt5.service.AuthService;
import com.sportt5.service.ConnectionServer;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;

public class EditProfileController {
    //Editable fields
    @FXML private TextField displayNameField;
    @FXML private TextField emailField;
    @FXML private TextField avatarUrlField;
    @FXML private TextArea bioArea;
    @FXML private DatePicker birthDatePicker;
    @FXML private ImageView avatarPreview;

    @FXML private PasswordField editPasswordField;
    @FXML private PasswordField editConfirmPasswordField;

    @FXML private Button saveButton;
    @FXML private Button cancelButton;

    private Users currentUser;

    public void initData(Users user){
        this.currentUser = user;

        displayNameField.setText(user.getDisplayName());
        emailField.setText(user.getEmail());
        avatarUrlField.setText(user.getAvatarUrl() != null ? user.getAvatarUrl() : "");
        bioArea.setText(user.getBio());
        birthDatePicker.setValue(user.getBirthDate());

    }
    @FXML
    private void handleChooseAvatar() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose Avatar");

        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter(
                        "Image Files",
                        "*.png", "*.jpg", "*.jpeg"
                )
        );

        File selectedFile =
                fileChooser.showOpenDialog(
                        avatarUrlField.getScene().getWindow()
                );

        if (selectedFile == null) {
            return;
        }

        try {
            AuthService authService = new AuthService();
            String avatarUrl =
                    authService.updateAvatar(selectedFile);
            if (avatarUrl != null) {
                avatarUrlField.setText(avatarUrl);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @FXML
    public void handleSave() throws IOException, InterruptedException {
        Map<String, Object> updates  = new HashMap<>();
        updates.put("displayName", displayNameField.getText());
        updates.put("email", emailField.getText());
        updates.put("bio", bioArea.getText());
        updates.put("avatarUrl", avatarUrlField.getText());
        updates.put("birthDate", birthDatePicker.getValue());

        AuthService authService = new AuthService();
        authService.updateUser(currentUser.getId(),updates);

        Stage stage = (Stage) saveButton.getScene().getWindow();
        stage.close();
    }
    @FXML
    public void handleCancel(){
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }
}
