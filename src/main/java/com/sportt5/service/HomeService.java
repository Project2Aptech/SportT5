package com.sportt5.service;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.sportt5.model.Albums;
import com.sportt5.model.PageResponse;
import com.sportt5.model.Songs;
import com.sportt5.util.ApiClient;

import java.io.IOException;
import java.net.http.HttpResponse;
import java.util.List;


public class HomeService {
    private final ObjectMapper mapper = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

    public List<Albums> getHomeAlbums() throws IOException, InterruptedException {
        HttpResponse<String> response = ApiClient.get("albums?size=10");
        if (response.statusCode() == 200) {
            JavaType type = mapper.getTypeFactory().constructParametricType(PageResponse.class, Albums.class);
            PageResponse<Albums> page = mapper.readValue(response.body(), type);
            return page.getContent();
        }
        throw new RuntimeException(mapper.readTree(response.body()).get("message").asText());
    }

    public List<Songs> getHomeSingles() throws IOException, InterruptedException {
        HttpResponse<String> response = ApiClient.get("songs?size=10");
        System.out.println("Body   = " + response.body());
        if (response.statusCode() == 200) {
            JavaType type = mapper.getTypeFactory().constructParametricType(PageResponse.class, Songs.class);
            PageResponse<Songs> page = mapper.readValue(response.body(), type);
            return page.getContent();
        }
        throw new RuntimeException(mapper.readTree(response.body()).get("message").asText());
    }
}
