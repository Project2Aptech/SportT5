package com.sportt5.service;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sportt5.model.Genres;

import java.io.IOException;
import java.util.List;

public class GenreService {

    private final ObjectMapper mapper;

    public GenreService() {
        mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.NONE);
        mapper.setVisibility(PropertyAccessor.SETTER, JsonAutoDetect.Visibility.PUBLIC_ONLY);
        mapper.setVisibility(PropertyAccessor.CREATOR, JsonAutoDetect.Visibility.ANY);
    }

    public List<Genres> getAll() throws IOException, InterruptedException {
        String json = ConnectionServer.get("genres");
        return mapper.readValue(json,
                mapper.getTypeFactory().constructCollectionType(List.class, Genres.class));
    }

    public Genres getById(int id) throws IOException, InterruptedException {
        String json = ConnectionServer.get("genres/" + id);
        return mapper.readValue(json, Genres.class);
    }
}
