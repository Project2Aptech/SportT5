package com.sportt5.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.sportt5.model.SongResponse;
import com.sportt5.model.UserResponse;
import com.sportt5.model.Users;
import com.sportt5.util.ApiClient;

import java.io.IOException;
import java.net.http.HttpResponse;

public class AdminService {
    private final ObjectMapper mapper = new ObjectMapper();
    {
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }


    public boolean updateUserRole(int userId,String accountType, String role) throws IOException, InterruptedException {
        String endpoint = String.format("admin/users/%d/role", userId);
        String jsonPayload = String.format("""
            {
                "role":"%s",
                "accountType":"%s"
            }
            """,role, accountType);

        HttpResponse<String> response = ApiClient.patchBuilder(endpoint,jsonPayload);
        return response.statusCode() == 200;
    }


    public boolean updateDeactivateUser(int userId,String action) throws IOException, InterruptedException {
        String endpoint = String.format("admin/users/%d/%s", userId,action);
        HttpResponse<String> response = ApiClient.patch(endpoint);

        return response.statusCode() == 200;
    }

    public boolean updateStatusSong(int songId) throws IOException, InterruptedException {
        String endpoint = String.format("admin/songs/%d/publish", songId);
        HttpResponse<String> response = ApiClient.patch(endpoint);

        return response.statusCode() == 204;
    }

    public boolean deleteUser(int userId) throws IOException, InterruptedException {
        String endpoint = String.format("admin/users/%d", userId);
        HttpResponse<String> response = ApiClient.delete(endpoint);

        return response.statusCode() == 204;
    }
    public boolean deleteSong(int songId) throws IOException, InterruptedException {
        String endpoint = String.format("admin/songs/%d", songId);
        HttpResponse<String> response = ApiClient.delete(endpoint);
        System.out.println("Delete Song Response: "+response);
        return response.statusCode() == 204;
    }

    public UserResponse getUser() throws IOException, InterruptedException {
        String endpoint = "admin/users";
        HttpResponse<String> response = ApiClient.get(endpoint);
        JsonNode node = mapper.readTree(response.body());

        System.out.println(response.statusCode());
        System.out.println(response.body());

        if(response.statusCode() == 200){
            if (node == null) {
                throw new RuntimeException("User data not found");
            }

            return mapper.treeToValue(node, UserResponse.class);
        }
        else {
            String message = node.get("message").asText();
            throw new RuntimeException(message);
        }
    }
    public SongResponse getSong() throws IOException, InterruptedException {
        String endpoint = "songs";
        HttpResponse<String> response = ApiClient.get(endpoint);
        JsonNode node = mapper.readTree(response.body());


        if(response.statusCode() == 200){
            if (node == null) {
                throw new RuntimeException("Song data not found");
            }

            return mapper.treeToValue(node, SongResponse.class);
        }
        else {
            throw new RuntimeException("Error loading data songs");
        }
    }
}
