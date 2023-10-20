package com.example.leaveapplication;

public class model {
    String request;
    String name;

    public model(String request, String name) {
        this.request = request;
        this.name = name;
    }

    public String getRequest() {
        return request;
    }

    public String getName() {
        return name;
    }
}
