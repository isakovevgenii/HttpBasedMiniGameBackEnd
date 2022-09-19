package com.example.demo.service;

import com.example.demo.entity.enums.HttpMethodEnum;
import com.sun.net.httpserver.HttpExchange;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.HashMap;
import java.util.Map;

public class UnitServiceTest {

    @Test
    public void queryToMapTest() {
        String query = "sessionKey=5N6YzvGOwD&levelId=3";
        Map<String, String> result = UtilService.queryToMap(query);
        Assertions.assertEquals(2, result.size());
        Assertions.assertTrue(result.containsKey("sessionKey"));
        Assertions.assertTrue(result.containsKey("levelId"));
        Assertions.assertTrue(result.containsValue("5N6YzvGOwD"));
        Assertions.assertTrue(result.containsValue("3"));
    }

    @Test
    public void validationRequestByTheNumberOfVariablesTest() {
        Map<String, String> params = new HashMap<>();
        params.put("sessionKey", "5N6YzvGOwD");
        params.put("levelId", "3");
        int goodNumberOfParams = 2;
        int badNumberOfParams = 3;
        Assertions.assertTrue(UtilService.validationRequestByTheNumberOfVariables(params, goodNumberOfParams));
        Assertions.assertFalse(UtilService.validationRequestByTheNumberOfVariables(params, badNumberOfParams));
    }

    @Test
    public void check31BitUnsignedIntegerNumberTest() {
        int unsignedIntegerNumber = 2;
        int noUnsignedIntegerNumber = -2;
        Assertions.assertTrue(UtilService.check31BitUnsignedIntegerNumber(noUnsignedIntegerNumber));
        Assertions.assertFalse(UtilService.check31BitUnsignedIntegerNumber(unsignedIntegerNumber));
    }

    @Test
    public void checkHttpMethodTest() {
        HttpExchange httpExchange = Mockito.mock(HttpExchange.class);
        Mockito.when(httpExchange.getRequestMethod()).thenReturn(HttpMethodEnum.POST.getMethod());
        Assertions.assertTrue(UtilService.checkHttpMethod(httpExchange, HttpMethodEnum.POST.getMethod()));
        Assertions.assertFalse(UtilService.checkHttpMethod(httpExchange, HttpMethodEnum.GET.getMethod()));
    }
}
