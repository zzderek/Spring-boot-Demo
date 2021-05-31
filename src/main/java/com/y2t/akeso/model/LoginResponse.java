package com.y2t.akeso.model;

import com.y2t.akeso.entity.User;

public class LoginResponse {

    private User user;
    private String token;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public LoginResponse(User user, String token) {
        this.user = user;
        this.token = token;
    }
}
