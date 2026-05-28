package com.sportt5.service;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sportt5.model.Users;

import java.io.IOException;

public class UserService {

    private final ObjectMapper mapper;

    public UserService() {
        mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.NONE);
        mapper.setVisibility(PropertyAccessor.SETTER, JsonAutoDetect.Visibility.PUBLIC_ONLY);
        mapper.setVisibility(PropertyAccessor.CREATOR, JsonAutoDetect.Visibility.ANY);
    }

    public Users getById(int id) throws IOException, InterruptedException {
        String json = ConnectionServer.getAuth("users/" + id);
        return mapper.readValue(json, Users.class);
    }

    public Users update(int id, String displayName, String bio, String avatarUrl) throws IOException, InterruptedException {
        String body = String.format(
                "{\"displayName\":\"%s\",\"bio\":\"%s\",\"avatarUrl\":\"%s\"}",
                displayName, bio, avatarUrl);
        String json = ConnectionServer.putAuth("users/" + id, body);
        return mapper.readValue(json, Users.class);
    }
}
