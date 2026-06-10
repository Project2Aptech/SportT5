package com.sportt5.service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.sportt5.util.ApiClient;

import java.net.http.HttpResponse;

public class FollowService {

    private static final ObjectMapper mapper = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    /** Returns total follower count for the current artist. */
    public long getMyFollowerCount() {
        try {
            HttpResponse<String> response = ApiClient.get("artists/followers");
            if (response.statusCode() != 200) return 0;
            JsonNode root = mapper.readTree(response.body());
            return root.path("totalElements").asLong(0);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
}