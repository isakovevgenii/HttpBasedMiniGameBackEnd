package com.example.demo.server;

import com.example.demo.controller.HighScoreHandler;
import com.example.demo.controller.LoginHandler;
import com.example.demo.controller.ScoreHandler;
import com.sun.net.httpserver.HttpServer;

import java.net.InetSocketAddress;

public class Server {

    public static void start() throws Exception {
        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
        server.createContext("/login", new LoginHandler());                             //   /<userid>/login
        server.createContext("/score", new ScoreHandler());                             //   /<levelid>/score?sessionkey=<sessionkey>
        server.createContext("/highScoreList", new HighScoreHandler());                 //   /<levelid>/highscorelist
        server.setExecutor(null);
        server.start();
    }
}
