package com.sportt5.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UserResponse {

    private List<Users> content;

    public UserResponse() {
    }

    public List<Users> getContent() {
        return content;
    }

    public void setContent(List<Users> content) {
        this.content = content;
    }
}