package com.example.demo.service;

import com.example.demo.datastorage.DataStorage;
import com.example.demo.entity.enums.HttpMethodEnum;
import com.example.demo.entity.enums.HttpResponseStatusCodesEnum;
import com.example.demo.entity.enums.ResponseMessageEnum;
import com.example.demo.entity.model.ResponseEntity;
import com.sun.net.httpserver.HttpExchange;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import static com.example.demo.service.UtilService.checkHttpMethod;

public class ScoreHandlerService {

    private static final int NUMBER_OF_PARAMS = 2;

    public static ResponseEntity validationRequestAndSaveScore(HttpExchange httpExchange) {
        Map<String, String> params = UtilService.queryToMap(httpExchange.getRequestURI().getQuery());

        if (!checkHttpMethod(httpExchange, HttpMethodEnum.POST.getMethod()))
            return new ResponseEntity(
                    HttpResponseStatusCodesEnum.METHOD_NOT_ALLOWED.getCode(),
                    ResponseMessageEnum.METHOD_NOT_ALLOWED.getMessage()
            );

        if (!UtilService.validationRequestByTheNumberOfVariables(params, NUMBER_OF_PARAMS))
            return new ResponseEntity(
                    HttpResponseStatusCodesEnum.INTERNAL_SERVER_ERROR.getCode(),
                    ResponseMessageEnum.PARAM_NUMBER_ERROR.getMessage() + params.size()
            );

        params.put("score", UtilService.readRequestBody(httpExchange));
        return validationNumberFormat(params);
    }

    private static ResponseEntity validationNumberFormat(Map<String, String> params) {
        int userId;
        int levelId = 0;
        String sessionKey;
        int score = 0;
        int rCode = 200;
        String response = "ok";

        if (!validationSessionKey(params.get("sessionKey")))
            return new ResponseEntity(
                    HttpResponseStatusCodesEnum.INTERNAL_SERVER_ERROR.getCode(),
                    ResponseMessageEnum.NOT_VALID_SESSION_KEY.getMessage()
            );

        try {
            levelId = Integer.parseInt(params.get("levelId"));
            if (UtilService.check31BitUnsignedIntegerNumber(levelId)) throw new NumberFormatException();

            score = Integer.parseInt(params.get("score"));
            if (UtilService.check31BitUnsignedIntegerNumber(score)) throw new NumberFormatException();
        } catch (NumberFormatException ex) {
            rCode = HttpResponseStatusCodesEnum.INTERNAL_SERVER_ERROR.getCode();
            response = ResponseMessageEnum.NUMBER_FORMAT_ERROR.getMessage() + params.get("userId");
        }

        sessionKey = params.get("sessionKey");
        userId = findUserIdBySessionKey(sessionKey);

        saveScoreInDataStorage(levelId, userId, score);

        return new ResponseEntity(rCode, response);
    }

    public static boolean validationSessionKey(String sessionKey) {
        LocalDateTime startLocalDateTime = DataStorage.getValidSessionKeyMap().get(sessionKey);
        if (startLocalDateTime == null) return false;
        return LocalDateTime.now().minusMinutes(10).isBefore(startLocalDateTime);
    }

    public static Integer findUserIdBySessionKey(String sessionKey) {
        return DataStorage.getSessionKeyMap().get(sessionKey);
    }

    public static void saveScoreInDataStorage(Integer levelId, Integer userId, Integer score) {
        if (DataStorage.getLevelAndScoreMap().get(levelId) == null) {
            Map<Integer, Integer> newLevelScore = new HashMap<>();
            newLevelScore.put(userId, score);
            DataStorage.getLevelAndScoreMap().put(levelId, newLevelScore);
        } else {
            DataStorage.getLevelAndScoreMap().get(levelId).put(userId, score);
        }
    }
}
