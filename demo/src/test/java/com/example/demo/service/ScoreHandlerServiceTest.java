package com.example.demo.service;

import com.example.demo.datastorage.DataStorage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ScoreHandlerServiceTest {

    @Test
    public void validationSessionKeyTest() {
        int userId = 1;
        String sessionKey1 = "1234abcd";
        String sessionKey2 = "abcd1234";
        LoginHandlerService.saveSessionKeyInDataStorage(userId, sessionKey1);
        Assertions.assertTrue(ScoreHandlerService.validationSessionKey(sessionKey1));
        Assertions.assertFalse(ScoreHandlerService.validationSessionKey(sessionKey2));
    }

    @Test
    public void findUserIdBySessionKeyTest() {
        int userId1 = 1;
        int userId2 = 2;
        String sessionKey = "1234abcd";
        LoginHandlerService.saveSessionKeyInDataStorage(userId1, sessionKey);
        Assertions.assertEquals(userId1, ScoreHandlerService.findUserIdBySessionKey(sessionKey));
        Assertions.assertNotEquals(userId2, ScoreHandlerService.findUserIdBySessionKey(sessionKey));
    }

    @Test
    public void saveScoreInDataStorageTest() {
        Integer levelId = 1;
        Integer userId = 1;
        Integer score = 200;
        ScoreHandlerService.saveScoreInDataStorage(levelId, userId, score);
        Assertions.assertEquals(score, DataStorage.getLevelAndScoreMap().get(levelId).get(userId));
    }
}
