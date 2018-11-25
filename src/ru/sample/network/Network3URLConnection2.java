package ru.sample.network;

import com.sun.net.httpserver.HttpServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

/**
 * Created by Nechaev Mikhail
 * Since 09/11/2018.
 */
class Network3URLConnection2 {

    private void run() throws IOException, InterruptedException {
        HttpServer httpServer = createHttpServer();
        httpServer.start();

        Timer timer = new Timer();
        timer.schedule(new CleverTimer(),
                TimeUnit.SECONDS.toMillis(1),
                TimeUnit.SECONDS.toMillis(2)
        );
        TimeUnit.SECONDS.sleep(15);

        timer.cancel();
        httpServer.stop(3); //seconds
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        new Network3URLConnection2().run();
    }

    private class CleverTimer extends TimerTask {

        private int runCount = 0;

        @Override
        public void run() {
            runCount++;
            try {
                URLConnection out = new URL("http://localhost:2222").openConnection();
                out.setRequestProperty("User-Agent", "Network3URLConnection2");
                out.setRequestProperty("Content-Type", "text/html");
                out.setRequestProperty("Cache-Control", "max-age=0, must-revalidate, no-cache, no-store");
                out.setDoOutput(true);
                try (PrintWriter writer = new PrintWriter(out.getOutputStream())) {
                    writer.println("Hello HttpServer " + runCount + "!");
                }
                System.out.println("Client accept: " + new String(out.getInputStream().readAllBytes()));
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private HttpServer createHttpServer() throws IOException {
        HttpServer httpServer = HttpServer.create(
                new InetSocketAddress(2222), 10
        );
        httpServer.createContext("/", httpExchange -> {
            List<String> userAgents = httpExchange.getRequestHeaders().get("User-Agent");
            List<String> hosts = httpExchange.getRequestHeaders().get("Host");
            List<String> forwarded = httpExchange.getRequestHeaders().get("X-forwarded-for");
            try (BufferedReader bufferedReader = new BufferedReader(
                        new InputStreamReader(httpExchange.getRequestBody())
                    );
                 OutputStream outputStream = httpExchange.getResponseBody()
            ) {
                String requestBody = bufferedReader.readLine(); //== null if GET
                System.out.println();
                System.out.println("Server accept: <" + requestBody + "> from " + hosts + " by "+ userAgents
                        + (forwarded != null ? ". X-forwarded-for " + forwarded : "")
                );

                byte[] responseBody = ("accept in " + new Date() + ": " + requestBody).getBytes();
                httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, responseBody.length);
                outputStream.write(responseBody);
            } finally {
                httpExchange.close();
            }
        });
        return httpServer;
    }
}
