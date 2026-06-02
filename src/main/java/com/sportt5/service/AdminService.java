package com.sportt5.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.sportt5.model.UserResponse;
import com.sportt5.model.Users;
import com.sportt5.util.ApiClient;

import java.io.IOException;
import java.net.http.HttpResponse;

public class AdminService {
    private ObjectMapper mapper = new ObjectMapper();
    {
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    public UserResponse getUser() throws IOException, InterruptedException {
        String endpoint = String.format("admin/users");
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
}
