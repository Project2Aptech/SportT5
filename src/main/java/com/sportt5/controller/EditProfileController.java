package com.sportt5.controller;

import com.sportt5.model.Users;
import com.sportt5.service.AuthService;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class EditProfileController {
    //Editable fields
    @FXML private TextField displayNameField;
    @FXML private TextField emailField;
    @FXML private TextField avatarUrlField;
    @FXML private TextArea bioArea;
    @FXML private DatePicker birthDatePicker;
    @FXML private ImageView avatarImageView;
    @FXML private PasswordField editPasswordField;
    @FXML private PasswordField editConfirmPasswordField;

    private String selectedAvatarUrl;

    @FXML private Button saveButton;
    @FXML private Button cancelButton;

    private Users currentUser;

    public void initData(Users user){
        this.currentUser = user;

        displayNameField.setText(user.getDisplayName());
        emailField.setText(user.getEmail());

        String avatarUrl = user.getAvatarUrl();

        if (avatarUrl != null && !avatarUrl.isBlank()) {
            selectedAvatarUrl = avatarUrl;
            avatarImageView.setImage(new Image(avatarUrl, true));
        } else {
            avatarImageView.setImage(
                    new Image(getClass().getResource("/com.sportt5/img/avatar.png").toExternalForm())
            );
        }
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
                        avatarImageView.getScene().getWindow()
                );

        if (selectedFile == null) {
            return;
        }

        try {
            AuthService authService = new AuthService();
            String avatarUrl = authService.updateAvatar(selectedFile);
            if (avatarUrl != null) {
                selectedAvatarUrl = avatarUrl;
                avatarImageView.setImage(new Image(avatarUrl, true));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @FXML
    public void handleSave() throws IOException, InterruptedException {
        Map<String, Object> updates  = new HashMap<>();
        updates.put("displayName", displayNameField.getText());
        updates.put("bio", bioArea.getText());
        if (selectedAvatarUrl != null) {
            updates.put("avatarUrl", selectedAvatarUrl);
        }

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
