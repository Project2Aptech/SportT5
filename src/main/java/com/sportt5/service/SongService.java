package com.sportt5.service;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sportt5.model.PageResponse;
import com.sportt5.model.Songs;

import java.io.IOException;
import java.util.List;

public class SongService {

    private final ObjectMapper mapper;

    public SongService() {
        mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.NONE);
        mapper.setVisibility(PropertyAccessor.SETTER, JsonAutoDetect.Visibility.PUBLIC_ONLY);
        mapper.setVisibility(PropertyAccessor.CREATOR, JsonAutoDetect.Visibility.ANY);
    }

    public List<Songs> getAll() throws IOException, InterruptedException {
        String json = ConnectionServer.get("songs");
        return parsePage(json);
    }

    public Songs getById(int id) throws IOException, InterruptedException {
        String json = ConnectionServer.get("songs/" + id);
        return mapper.readValue(json, Songs.class);
    }

    public List<Songs> getByGenre(int genreId) throws IOException, InterruptedException {
        String json = ConnectionServer.get("songs/genre/" + genreId);
        return parsePage(json);
    }

    public List<Songs> getByArtist(int artistId) throws IOException, InterruptedException {
        String json = ConnectionServer.get("songs/artist/" + artistId);
        return parsePage(json);
    }

    public List<Songs> getByAlbum(int albumId) throws IOException, InterruptedException {
        String json = ConnectionServer.get("songs/album/" + albumId);
        return parsePage(json);
    }

    public List<Songs> search(String title) throws IOException, InterruptedException {
        String json = ConnectionServer.get("songs/search?title=" + java.net.URLEncoder.encode(title, java.nio.charset.StandardCharsets.UTF_8));
        return parsePage(json);
    }

    private List<Songs> parsePage(String json) throws IOException {
        PageResponse<Songs> page = mapper.readValue(json,
                mapper.getTypeFactory().constructParametricType(PageResponse.class, Songs.class));
        return page.getContent();
    }
}
