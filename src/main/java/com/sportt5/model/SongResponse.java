package com.sportt5.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SongResponse {
    private List<Songs> content;

    public SongResponse() {
    }

    public List<Songs> getContent() {
        return content;
    }

    public void setContent(List<Songs> content) {
        this.content = content;
    }
}
