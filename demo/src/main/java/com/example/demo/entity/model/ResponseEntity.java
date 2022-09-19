package com.example.demo.entity.model;

public class ResponseEntity {

    private int rCode;
    private String response;
    private int levelId;

    public ResponseEntity(int rCode, String response) {
        setRCode(rCode);
        setResponse(response);
    }

    public ResponseEntity(int rCode, String response, int levelId) {
        setRCode(rCode);
        setResponse(response);
        setLevelId(levelId);
    }

    public int getRCode() {
        return rCode;
    }

    private void setRCode(int rCode) {
        this.rCode = rCode;
    }

    public String getResponse() {
        return response;
    }

    private void setResponse(String response) {
        this.response = response;
    }

    public int getLevelId() {
        return levelId;
    }

    private void setLevelId(int levelId) {
        this.levelId = levelId;
    }

}