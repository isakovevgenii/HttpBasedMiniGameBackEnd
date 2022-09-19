package com.example.demo.entity.enums;

public enum HttpMethodEnum {

    GET("GET"),
    POST("POST");

    private final String method;

    HttpMethodEnum(String method) {
        this.method = method;
    }

    public String getMethod() {
        return this.method;
    }
}
