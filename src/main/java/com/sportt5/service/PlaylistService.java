package com.sportt5.service;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.sportt5.model.PageResponse;
import com.sportt5.model.Playlists;
import com.sportt5.util.ApiClient;

import java.io.IOException;
import java.net.http.HttpResponse;
import java.util.List;

public class PlaylistService {

    private final ObjectMapper mapper = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

    public List<Playlists> getMyPlaylists() throws IOException, InterruptedException {
        HttpResponse<String> response = ApiClient.get("playlists/me?size=50");
        if (response.statusCode() == 200) {
            JavaType type = mapper.getTypeFactory()
                    .constructParametricType(PageResponse.class, Playlists.class);
            PageResponse<Playlists> page = mapper.readValue(response.body(), type);
            return page.getContent();
        }
        throw new RuntimeException(mapper.readTree(response.body()).get("message").asText());
    }
}
