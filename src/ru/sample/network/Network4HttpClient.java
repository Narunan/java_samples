package ru.sample.network;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by Nechaev Mikhail
 * Since 09/11/2018.
 */
class Network4HttpClient {

    public static void main(String[] args) throws Exception {
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest httpRequest = HttpRequest
                .newBuilder(
                        new URI("https://upload.wikimedia.org/wikipedia/ru/thumb/3/39/Java_logo.svg/1200px-Java_logo.svg.png")
                )
                .GET()
                .headers("User-Agent", "UA", "key1", "value1")
                .headers("key2", "value2")
                .build();
        HttpResponse<Path> response =
                httpClient.send(
                        httpRequest,
                        HttpResponse.BodyHandlers.ofFile(
                                Paths.get("Java_logo.png")
                        )
                );
        System.out.println("Method: " + httpRequest.method());
        System.out.println("Status:  " + response.statusCode());
        System.out.println("Headers:");
        response.headers().map().forEach(
                (key, value) -> System.out.println(key + " " + value)
        );
    }
}
