package com.example.demo.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

public class HighScoreHandlerServiceTest {

    @Test
    void sortMapTest() {
        Map<Integer, Integer> unSortedMap = new HashMap<>();
        unSortedMap.put(1, 100);
        unSortedMap.put(2, 300);
        unSortedMap.put(3, 200);
        unSortedMap.put(4, 50);
        String expectedSortedMap = "{2=300, 3=200, 1=100, 4=50}";
        Assertions.assertEquals(expectedSortedMap, HighScoreHandlerService.sortMap(unSortedMap).toString());
    }

    @Test
    void trimMapTest() {
        int rightMapSize = 2;
        int noRightMapSize = 3;
        Map<Integer, Integer> unTrimMap = new HashMap<>();
        unTrimMap.put(1, 100);
        unTrimMap.put(2, 300);
        unTrimMap.put(3, 200);
        unTrimMap.put(4, 50);
        Map<Integer, Integer> trimMap = HighScoreHandlerService.trimMap(unTrimMap, rightMapSize);
        Assertions.assertEquals(rightMapSize, trimMap.size());
        Assertions.assertNotEquals(noRightMapSize, trimMap.size());
    }
}
