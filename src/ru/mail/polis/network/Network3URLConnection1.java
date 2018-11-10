package ru.mail.polis.network;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Nechaev Mikhail
 * Since 09/11/2018.
 */
public class Network3URLConnection1 {

    public static void main(String args[]) throws IOException {
        URL url = new URL("https://bash.im");
        URLConnection urlConnection = url.openConnection();
        urlConnection.setRequestProperty(
                "User-Agent", "Network3URLConnection1"
        );
        System.out.println("Date: " + new Date(urlConnection.getDate()));
        System.out.println("Content-Type: " + urlConnection.getContentType());
        System.out.println("Expires: " + new Date(urlConnection.getExpiration()));
        System.out.println("Last-Modified: " + new Date(urlConnection.getLastModified()));
        System.out.println("Content-Length: " + urlConnection.getContentLengthLong());
        System.out.println("Headers:");
        Map<String,List<String>> headers = urlConnection.getHeaderFields();
        for (Map.Entry<String, List<String>> entry : headers.entrySet()) {
            System.out.println(entry.getKey() + " = " + entry.getValue());
        }
        try (InputStream inputStream = urlConnection.getInputStream();
             OutputStream outputStream = Files.newOutputStream(Paths.get("index.html"))
        ) {
            inputStream.transferTo(outputStream);
        }
        //
        HttpURLConnection httpURLConnection = (HttpURLConnection) urlConnection;
        System.out.println("Request method: " + httpURLConnection.getRequestMethod());
        System.out.println("Response code: " + httpURLConnection.getResponseCode());
        System.out.println("Response Message: " + httpURLConnection.getResponseMessage());
    }
}
