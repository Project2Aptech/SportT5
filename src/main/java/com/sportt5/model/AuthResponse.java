package com.sportt5.model;

public class AuthResponse {
    private String token;
    private Users user;

    public AuthResponse(String token, Users user) {
        this.token = token;
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public Users getUser() {
        return user;
    }

}
