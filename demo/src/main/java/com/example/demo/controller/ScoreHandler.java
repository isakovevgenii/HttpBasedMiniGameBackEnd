package com.example.demo.controller;

import com.example.demo.entity.model.ResponseEntity;
import com.example.demo.service.ScoreHandlerService;
import com.example.demo.service.UtilService;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;

public class ScoreHandler implements HttpHandler {

    @Override
    public synchronized void handle(HttpExchange httpExchange) throws IOException {
        ResponseEntity responseEntity = ScoreHandlerService.validationRequestAndSaveScore(httpExchange);
        UtilService.writeResponse(httpExchange, responseEntity);
    }
}