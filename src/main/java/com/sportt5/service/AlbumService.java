package com.sportt5.service;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.sportt5.model.Albums;
import com.sportt5.model.PageResponse;
import com.sportt5.util.ApiClient;

import java.io.IOException;
import java.net.URLEncoder;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class AlbumService {

    private final ObjectMapper mapper = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

    public Albums getById(int id) throws IOException, InterruptedException {
        HttpResponse<String> response = ApiClient.get("albums/" + id);
        if (response.statusCode() == 200) {
            return mapper.readValue(response.body(), Albums.class);
        }
        throw new RuntimeException(mapper.readTree(response.body()).get("message").asText());
    }

    public List<Albums> getByArtist(int artistId) throws IOException, InterruptedException {
        HttpResponse<String> response = ApiClient.get("albums/artist/" + artistId + "?size=50");
        if (response.statusCode() == 200) {
            JavaType type = mapper.getTypeFactory()
                    .constructParametricType(PageResponse.class, Albums.class);
            PageResponse<Albums> page = mapper.readValue(response.body(), type);
            return page.getContent();
        }
        throw new RuntimeException(mapper.readTree(response.body()).get("message").asText());
    }

    public List<Albums> search(String title) throws IOException, InterruptedException {
        String encoded = URLEncoder.encode(title, StandardCharsets.UTF_8);
        HttpResponse<String> response = ApiClient.get("albums/search?title=" + encoded + "&size=50");
        if (response.statusCode() == 200) {
            JavaType type = mapper.getTypeFactory()
                    .constructParametricType(PageResponse.class, Albums.class);
            PageResponse<Albums> page = mapper.readValue(response.body(), type);
            return page.getContent();
        }
        throw new RuntimeException(mapper.readTree(response.body()).get("message").asText());
    }
}
