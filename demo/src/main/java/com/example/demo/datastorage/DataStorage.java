package com.example.demo.datastorage;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class DataStorage {

    private static Map<String, Integer> sessionKeyMap = new HashMap<>();

    public static Map<String, Integer> getSessionKeyMap() {
        return sessionKeyMap;
    }

    private static Map<String, LocalDateTime> validSessionKeyMap = new HashMap<>();

    public static Map<String, LocalDateTime> getValidSessionKeyMap() {
        return validSessionKeyMap;
    }

    public static Map<Integer, Map<Integer, Integer>> levelAndScoreMap = new HashMap<>();

    public static Map<Integer, Map<Integer, Integer>> getLevelAndScoreMap() {
        return levelAndScoreMap;
    }

}
