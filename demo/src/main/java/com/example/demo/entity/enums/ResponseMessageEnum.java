package com.example.demo.entity.enums;

public enum ResponseMessageEnum {

    METHOD_NOT_ALLOWED("HTTP Error 405 – Method Not Allowed"),
    NOT_VALID_SESSION_KEY("Session key not valid"),
    NUMBER_FORMAT_ERROR("userId must be 31 bit unsigned integer number. Now userId = "),
    PARAM_NUMBER_ERROR("There should be one element in this array, now there are ");

    private final String message;

    ResponseMessageEnum(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }
}
