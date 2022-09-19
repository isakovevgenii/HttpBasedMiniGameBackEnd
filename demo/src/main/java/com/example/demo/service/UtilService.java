package com.example.demo.service;

import com.example.demo.entity.model.ResponseEntity;
import com.sun.net.httpserver.HttpExchange;

import java.io.*;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

public class UtilService {

    private static final String CHARSET_NAME = "utf-8";

    public static void writeResponse(HttpExchange httpExchange, ResponseEntity responseEntity) throws IOException {
        httpExchange.sendResponseHeaders(responseEntity.getRCode(), responseEntity.getResponse().length());
        OutputStream os = httpExchange.getResponseBody();
        os.write(responseEntity.getResponse().getBytes());
        os.close();
    }

    public static void writeResponseWithCsv(HttpExchange httpExchange, ResponseEntity responseEntity) throws IOException {
        File file = new File(
                System.getProperty("user.dir")
                        + "\\demo\\src\\main\\resources\\csvDirectory\\highScoreLevel" + responseEntity.getLevelId() + "List.csv"
        );
        int rCode = responseEntity.getRCode();
        byte[] bytes = Files.readAllBytes(file.toPath());
        httpExchange.getResponseHeaders().set("Content-Disposition", "attachment; filename=highScoreLevel1List.csv");
        httpExchange.sendResponseHeaders(rCode, bytes.length);
        httpExchange.getResponseBody().write(bytes);
    }

    public static Map<String, String> queryToMap(String query) {
        Map<String, String> result = new HashMap<String, String>();
        for (String param : query.split("&")) {
            String pair[] = param.split("=");
            if (pair.length > 1) {
                result.put(pair[0], pair[1]);
            } else {
                result.put(pair[0], "");
            }
        }
        return result;
    }

    public static boolean checkHttpMethod(HttpExchange httpExchange, String httpMethod) {
        return httpExchange.getRequestMethod().equals(httpMethod);
    }

    public static boolean validationRequestByTheNumberOfVariables(Map<String, String> params, int numberOfParams) {
        return params.size() == numberOfParams;
    }

    public static boolean check31BitUnsignedIntegerNumber(int number) {
        return number < 0;
    }

    //todo: Exception ignore!!!
    public static String readRequestBody(HttpExchange httpExchange) {
        String requestBody = null;
        try {
            InputStreamReader isr = new InputStreamReader(httpExchange.getRequestBody(), CHARSET_NAME);
            BufferedReader br = new BufferedReader(isr);
            requestBody = br.readLine();
        } catch (Exception ignore) {

        }
        return requestBody;
    }
}
