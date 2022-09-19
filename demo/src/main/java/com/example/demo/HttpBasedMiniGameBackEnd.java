package com.example.demo;

import com.example.demo.server.Server;

public class HttpBasedMiniGameBackEnd {

    public static void main(String[] args) throws Exception {
        Server.start();
        System.out.println("it is alive!");
    }
}
