package com.example.demo.controller;

import com.example.demo.entity.enums.HttpResponseStatusCodesEnum;
import com.example.demo.entity.model.ResponseEntity;
import com.example.demo.service.HighScoreHandlerService;
import com.example.demo.service.UtilService;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;

public class HighScoreHandler implements HttpHandler {

    @Override
    public synchronized void handle(HttpExchange httpExchange) throws IOException {

        ResponseEntity responseEntity = HighScoreHandlerService.validationRequest(httpExchange);

        if (responseEntity.getRCode() == HttpResponseStatusCodesEnum.OK.getCode())
            UtilService.writeResponseWithCsv(httpExchange, responseEntity);

        UtilService.writeResponse(httpExchange, responseEntity);
    }
}
