package com.sportt5.service;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sportt5.model.Albums;
import com.sportt5.model.PageResponse;

import java.io.IOException;
import java.util.List;

public class AlbumService {

    private final ObjectMapper mapper;

    public AlbumService() {
        mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.NONE);
        mapper.setVisibility(PropertyAccessor.SETTER, JsonAutoDetect.Visibility.PUBLIC_ONLY);
        mapper.setVisibility(PropertyAccessor.CREATOR, JsonAutoDetect.Visibility.ANY);
    }

    public Albums getById(int id) throws IOException, InterruptedException {
        String json = ConnectionServer.get("albums/" + id);
        return mapper.readValue(json, Albums.class);
    }

    public List<Albums> getByArtist(int artistId) throws IOException, InterruptedException {
        String json = ConnectionServer.get("albums/artist/" + artistId);
        PageResponse<Albums> page = mapper.readValue(json,
                mapper.getTypeFactory().constructParametricType(PageResponse.class, Albums.class));
        return page.getContent();
    }
}
