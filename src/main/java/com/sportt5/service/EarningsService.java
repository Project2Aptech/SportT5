package com.sportt5.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.sportt5.model.ArtistEarnings;
import com.sportt5.util.ApiClient;

import java.net.http.HttpResponse;
import java.util.Collections;
import java.util.List;

public class EarningsService {

    private static final ObjectMapper mapper = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    /** Returns all earnings records for the currently authenticated artist. */
    public List<ArtistEarnings> getMyEarnings() {
        try {
            HttpResponse<String> response = ApiClient.get("earnings");
            if (response.statusCode() != 200) return Collections.emptyList();

            JsonNode root = mapper.readTree(response.body());
            JsonNode content = root.path("content");
            return mapper.readValue(
                    content.toString(),
                    new TypeReference<List<ArtistEarnings>>() {}
            );
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }
}