package com.sportt5.service;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sportt5.model.PageResponse;
import com.sportt5.model.PlayHistory;

import java.io.IOException;
import java.util.List;

public class HistoryService {

    private final ObjectMapper mapper;

    public HistoryService() {
        mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.NONE);
        mapper.setVisibility(PropertyAccessor.SETTER, JsonAutoDetect.Visibility.PUBLIC_ONLY);
        mapper.setVisibility(PropertyAccessor.CREATOR, JsonAutoDetect.Visibility.ANY);
    }

    public List<PlayHistory> getRecent() throws IOException, InterruptedException {
        String json = ConnectionServer.getAuth("history");
        PageResponse<PlayHistory> page = mapper.readValue(json,
                mapper.getTypeFactory().constructParametricType(PageResponse.class, PlayHistory.class));
        return page.getContent();
    }

    public void record(int songId, int secondsPlayed) throws IOException, InterruptedException {
        String body = String.format("{\"secondsPlayed\":%d,\"deviceType\":\"DESKTOP\"}", secondsPlayed);
        ConnectionServer.postAuth("history/songs/" + songId, body);
    }

    public void clearAll() throws IOException, InterruptedException {
        ConnectionServer.deleteAuth("history");
    }
}
