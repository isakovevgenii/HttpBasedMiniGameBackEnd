package com.example.demo.entity.enums;

public enum HttpResponseStatusCodesEnum {

    OK(200),
    METHOD_NOT_ALLOWED(405),
    INTERNAL_SERVER_ERROR(500);

    private final int code;

    HttpResponseStatusCodesEnum(int code) {
        this.code = code;
    }

    public int getCode() {
        return this.code;
    }
}
