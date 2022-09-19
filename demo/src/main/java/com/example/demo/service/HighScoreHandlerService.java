package com.example.demo.service;

import com.example.demo.datastorage.DataStorage;
import com.example.demo.entity.enums.HttpMethodEnum;
import com.example.demo.entity.enums.HttpResponseStatusCodesEnum;
import com.example.demo.entity.enums.ResponseMessageEnum;
import com.example.demo.entity.model.ResponseEntity;
import com.sun.net.httpserver.HttpExchange;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import static com.example.demo.service.UtilService.checkHttpMethod;

public class HighScoreHandlerService {

    private static final int NUMBER_OF_PARAMS = 1;
    private static final int HIGH_SCORE_LIST_SIZE = 15;

    public static ResponseEntity validationRequest(HttpExchange httpExchange) {
        Map<String, String> params = UtilService.queryToMap(httpExchange.getRequestURI().getQuery());

        if (!checkHttpMethod(httpExchange, HttpMethodEnum.GET.getMethod()))
            return new ResponseEntity(
                    HttpResponseStatusCodesEnum.METHOD_NOT_ALLOWED.getCode(),
                    ResponseMessageEnum.METHOD_NOT_ALLOWED.getMessage()
            );

        if (!UtilService.validationRequestByTheNumberOfVariables(params, NUMBER_OF_PARAMS))
            return new ResponseEntity(
                    HttpResponseStatusCodesEnum.INTERNAL_SERVER_ERROR.getCode(),
                    ResponseMessageEnum.PARAM_NUMBER_ERROR.getMessage() + params.size()
            );

        return validationNumberFormatAndCreateCsv(params);
    }

    private static ResponseEntity validationNumberFormatAndCreateCsv(Map<String, String> params) {
        int levelId = 0;
        int rCode = 200;
        String response = "ok";

        try {
            levelId = Integer.parseInt(params.get("levelId"));
            if (UtilService.check31BitUnsignedIntegerNumber(levelId)) throw new NumberFormatException();
        } catch (NumberFormatException ex) {
            rCode = HttpResponseStatusCodesEnum.INTERNAL_SERVER_ERROR.getCode();
            response = ResponseMessageEnum.NUMBER_FORMAT_ERROR.getMessage() + params.get("userId");
        }

        HighScoreHandlerService.writeHashMapToCsv(levelId);
        return new ResponseEntity(rCode, response, levelId);
    }

    private static void writeHashMapToCsv(int levelId) {
        Map<Integer, Integer> map = HighScoreHandlerService.sortMap(DataStorage.getLevelAndScoreMap().get(levelId));
        Map<Integer, Integer> trimMap = HighScoreHandlerService.sortMap(HighScoreHandlerService.trimMap(map, HIGH_SCORE_LIST_SIZE));
        try (Writer writer = new FileWriter(
                System.getProperty("user.dir") + "\\demo\\src\\main\\resources\\csvDirectory\\highScoreLevel" + levelId + "List.csv")
        ) {
            for (Map.Entry<Integer, Integer> entry : trimMap.entrySet()) {
                writer.append(String.valueOf(entry.getKey()))
                        .append('=')
                        .append(String.valueOf(entry.getValue()))
                        .append(',');
            }
        } catch (IOException ex) {
            ex.printStackTrace(System.err);
        }
    }

    public static Map<Integer, Integer> sortMap(Map<Integer, Integer> unSortedMap) {
        LinkedHashMap<Integer, Integer> reverseSortedMap = new LinkedHashMap<>();

        unSortedMap.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .forEachOrdered(x -> reverseSortedMap.put(x.getKey(), x.getValue()));

        return reverseSortedMap;
    }

    public static Map<Integer, Integer> trimMap(final Map<Integer, Integer> map, int mapSize) {
        int i = 0;
        final Map<Integer, Integer> tripMap = new HashMap<>();
        for (final Map.Entry<Integer, Integer> entry : map.entrySet()) {
            if (i >= mapSize) break;
            tripMap.put(entry.getKey(), entry.getValue());
            i++;
        }
        return tripMap;
    }
}
