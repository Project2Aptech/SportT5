package com.sportt5.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.sportt5.model.AuthResponse;
import com.sportt5.model.Users;
import com.sportt5.util.DataServer;

import java.io.File;
import java.io.IOException;
import java.net.http.HttpResponse;
import java.util.Map;

public class AuthService {
    private ObjectMapper mapper = new ObjectMapper();
    {
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }
    public String updateAvatar(File selectedFile) {
        String endpoint = "users/me/avatar";
        try {
            HttpResponse<String> response =
                    DataServer.uploadAvatar(
                            endpoint,
                            selectedFile
                    );

            System.out.println(response.body());
            JsonNode node =
                    mapper.readTree(response.body());
            return node.get("avatarUrl").asText();

        } catch (Exception e) {

            e.printStackTrace();
        }

        return null;
    }

    public Users updateUser(int id, Map<String, Object> updates) throws IOException, InterruptedException {
        String endpoint = String.format("users/%d", id);
        String jsonBody = mapper.writeValueAsString(updates);
        HttpResponse<String> response = DataServer.patch(endpoint, jsonBody);
        JsonNode node = mapper.readTree(response.body());

        if(response.statusCode() == 200){
            return mapper.readValue(response.body(), Users.class);
        }
        else {
            String message = node.get("message").asText();
            throw new RuntimeException(message);
        }
    }

    public Users getUserById(int id) throws IOException, InterruptedException {
        String endpoint = String.format("users/%d", id);
        HttpResponse<String> response = DataServer.get(endpoint);
        JsonNode node = mapper.readTree(response.body());

        System.out.println("=== GET /users/" + id + " ===");
        System.out.println("Status = " + response.statusCode());
        System.out.println("Body   = " + response.body());
        if(response.statusCode() == 200){
            if (node == null) {
                throw new RuntimeException("User data not found");
            }
            return mapper.treeToValue(node, Users.class);
        }
        else {
            String message = node.get("message").asText();
            throw new RuntimeException(message);
        }
    }


    public AuthResponse register(String nameInput,String emailInput, String passwordInput) throws IOException, InterruptedException {
        String jsonPayload = String.format("""
            {
                "username":"%s",
                "email":"%s",
                "password":"%s"
            }
            """, nameInput, emailInput, passwordInput);

        String endpointRegister = "auth/register";

        HttpResponse<String> response = DataServer.post(endpointRegister, jsonPayload);
        JsonNode node = mapper.readTree(response.body());

        if(response.statusCode() == 200){
            String token = node.get("accessToken").asText();
            JsonNode userNode = node.get("user");
            Users user = mapper.treeToValue(userNode, Users.class);

            return new AuthResponse(token, user);
        }
        else {
            String message = node.get("message").asText();
            throw new RuntimeException(message);
        }
    }

    public AuthResponse authenticate(String login,String password) throws IOException, InterruptedException {
        String endpointLogin = "auth/login";
        String jsonPayload = String.format("{\"email\":\"%s\", \"password\":\"%s\"}", login, password);
        HttpResponse<String> response = DataServer.post(endpointLogin, jsonPayload);
        JsonNode node = mapper.readTree(response.body());

        if (response.statusCode() == 200) {
            String token = node.get("accessToken").asText();
            JsonNode userNode = node.get("user");
            Users user = mapper.treeToValue(userNode, Users.class);

            return new AuthResponse(token, user);
        } else {
            String message = node.get("message").asText();
            throw new RuntimeException(message);
        }
    }
}
