package com.projetoslabex.model;

public class LoginResponse {
    private String token;
    private boolean Response;
    private int id;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public boolean getResponse() {
        return Response;
    }

    public void setResponse(boolean response) {
        Response = response;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
