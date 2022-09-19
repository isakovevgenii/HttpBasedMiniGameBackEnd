package com.example.demo.service;

import com.example.demo.datastorage.DataStorage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

public class LoginHandlerServiceTest {

    @Test
    public void saveSessionKeyInDataStorageTest() {
        int userId = 1;
        String sessionKey = "1234abcd";
        LoginHandlerService.saveSessionKeyInDataStorage(userId, sessionKey);
        Assertions.assertEquals(userId, DataStorage.getSessionKeyMap().get("1234abcd"));
        Assertions.assertNotEquals(LocalDateTime.now(), DataStorage.getValidSessionKeyMap().get("1234abcd"));
    }
}
