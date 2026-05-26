package com.sportt5.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sportt5.model.AuthResponse;
import com.sportt5.model.Users;

import java.io.IOException;
import java.net.http.HttpResponse;

public class AuthService {
    private final String endpointLogin = "auth/login";
    private final String endpointRegister = "auth/register";
    private ObjectMapper mapper = new ObjectMapper();

    public AuthResponse register(String nameInput,String emailInput, String passwordInput) throws IOException, InterruptedException {
        String jsonPayload = String.format("""
            {
                "username":"%s",
                "email":"%s",
                "password":"%s"
            }
            """, nameInput, emailInput, passwordInput);

        HttpResponse<String> response = ConnectionServer.post(endpointRegister, jsonPayload);
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
        String jsonPayload = String.format("{\"email\":\"%s\", \"password\":\"%s\"}", login, password);
        HttpResponse<String> response = ConnectionServer.post(endpointLogin, jsonPayload);
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
