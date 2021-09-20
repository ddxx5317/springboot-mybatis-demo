package com.winter.controller;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;

/**
 * @Description:
 * @Author: DDxx
 * @Date: 2021/9/20
 */
public class HttpClient11Test {
    public static void main(String[] args) throws Exception{
        String url="http://localhost:1111/test/index";
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest httpRequest = HttpRequest.newBuilder(URI.create(url)).build();
        final HttpResponse.BodyHandler<String> bodyHandler = HttpResponse.BodyHandlers.ofString();
        //final HttpResponse<String> httpResponse = httpClient.send(httpRequest, bodyHandler);
        //final String body = httpResponse.body();

        CompletableFuture<HttpResponse<String>> completableFuture = httpClient.sendAsync(httpRequest, bodyHandler);
        final String body = completableFuture.get().body();
        System.out.println(body);
    }
}
