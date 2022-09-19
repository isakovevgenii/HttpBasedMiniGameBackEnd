package com.example.demo.service;

import com.example.demo.datastorage.DataStorage;
import com.example.demo.entity.enums.HttpMethodEnum;
import com.example.demo.entity.enums.HttpResponseStatusCodesEnum;
import com.example.demo.entity.enums.ResponseMessageEnum;
import com.example.demo.entity.model.ResponseEntity;
import com.sun.net.httpserver.HttpExchange;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Random;

public class LoginHandlerService {

    private static final int NUMBER_OF_PARAMS = 1;

    public static ResponseEntity validationRequestAndGenerateSessionKey(HttpExchange httpExchange) {
        Map<String, String> params = UtilService.queryToMap(httpExchange.getRequestURI().getQuery());

        if (!UtilService.checkHttpMethod(httpExchange, HttpMethodEnum.GET.getMethod()))
            return new ResponseEntity(
                    HttpResponseStatusCodesEnum.METHOD_NOT_ALLOWED.getCode(),
                    ResponseMessageEnum.METHOD_NOT_ALLOWED.getMessage()
            );

        if (!UtilService.validationRequestByTheNumberOfVariables(params, NUMBER_OF_PARAMS)) {
            return new ResponseEntity(
                    HttpResponseStatusCodesEnum.INTERNAL_SERVER_ERROR.getCode(),
                    ResponseMessageEnum.PARAM_NUMBER_ERROR.getMessage() + params.size()
            );
        }

        ResponseEntity responseEntity = validationNumberFormat(params);
        saveSessionKeyInDataStorage(Integer.parseInt(params.get("userId")), responseEntity.getResponse());

        return responseEntity;
    }

    private static ResponseEntity validationNumberFormat(Map<String, String> params) {
        int userId;
        int rCode = 200;
        String response = generatingRandomAlphanumericString();

        try {
            userId = Integer.parseInt(params.get("userId"));
            if (UtilService.check31BitUnsignedIntegerNumber(userId)) throw new NumberFormatException();
        } catch (NumberFormatException ex) {
            rCode = HttpResponseStatusCodesEnum.INTERNAL_SERVER_ERROR.getCode();
            response = ResponseMessageEnum.NUMBER_FORMAT_ERROR.getMessage() + params.get("userId");
        }

        return new ResponseEntity(rCode, response);
    }

    private static String generatingRandomAlphanumericString() {
        int leftLimit = 48;
        int rightLimit = 122;
        int targetStringLength = 10;
        Random random = new Random();

        return random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }

    //todo: delete old sessionKey
    public static void saveSessionKeyInDataStorage(int userId, String sessionKey) {
        DataStorage.getSessionKeyMap().put(sessionKey, userId);
        DataStorage.getValidSessionKeyMap().put(sessionKey, LocalDateTime.now());
    }
}
