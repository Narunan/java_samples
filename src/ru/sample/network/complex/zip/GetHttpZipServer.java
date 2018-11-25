package ru.sample.network.complex.zip;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import ru.sample.logger.LoggerUtils;
import ru.sample.network.complex.utils.IOConsumer;
import ru.sample.network.complex.utils.RequestMethod;
import ru.sample.network.complex.utils.wrappers.Ctx;
import ru.sample.network.complex.utils.wrappers.Response;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

class GetHttpZipServer {

    private static final Path ROOT = Paths.get("res", "get_http_zip_server");
    private static final Logger LOGGER = LoggerUtils.getFormattedLogger(GetHttpZipServer.class.getCanonicalName());

    public static void main(String[] args) {
        new GetHttpZipServer().run();
    }

    private void run() {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        HttpServer httpServer;
        try {
            httpServer = createHttpServer(executorService);
        } catch (IOException e) {
            LOGGER.throwing("GetHttpZipServer", "run", e);
            return;
        }
        httpServer.start();
    }

    private HttpServer createHttpServer(Executor executor) throws IOException {
        HttpServer httpServer = HttpServer.create(
                new InetSocketAddress(8081), 10
        );
        httpServer.createContext("/", (exchange -> handle(exchange, this::doGet)));
        httpServer.createContext("/zip/simple", (exchange -> handle(exchange, this::doGetZip)));
        httpServer.createContext("/zip/streaming", (exchange -> handle(exchange, this::doGetZipStreaming)));
        httpServer.setExecutor(executor);
        return httpServer;
    }

    private void handle(HttpExchange httpExchange, IOConsumer<Ctx> handler) {
        String path = httpExchange.getHttpContext().getPath();
        boolean streaming = path.endsWith("streaming");
        String uri = null;

        try (Ctx ctx = new Ctx(httpExchange, streaming)) {
            uri = ctx.getURI().toString();
            LOGGER.info("Start: " + uri);
            RequestMethod method = ctx.getMethod();
            switch (method) {
                case GET:
                    handler.accept(ctx);
                    return;
                default:
                    doOther(ctx);
            }
        } catch (Exception e) {
            LOGGER.throwing("GetHttpZipServer", "handle", e);
        } finally {
            LOGGER.info("Finish: " + uri);
        }
    }

    private void doGet(Ctx ctx) throws IOException {
        Response response = ctx.getResponse();
        String uriPath = ctx.getURI().getPath();
        if (null == uriPath || uriPath.isEmpty() || "/".equals(uriPath)) {
            uriPath = "index.html";
        }
        Path filePath = ROOT.resolve(uriPath);
        if (Files.exists(filePath)) {
            response.setStatus(HttpURLConnection.HTTP_OK);
            response.setContentLength(Files.size(filePath));
            response.setContentType(Files.probeContentType(filePath));
            try (InputStream inputStream = Files.newInputStream(filePath)) {
                inputStream.transferTo(response.getOutputStream());
            }
        } else {
            response.setStatus(HttpURLConnection.HTTP_NOT_FOUND);
        }
    }

    private void doGetZip(Ctx ctx) throws IOException {
        int timeout = ZipState.TIMEOUT.getOrDefault(ctx);
        int count = ZipState.COUNT.getOrDefault(ctx);

        Response response = ctx.getResponse();

        createZip(response.getOutputStream(), count, timeout);

        setHeaders(response, "simple");
    }

    private void doGetZipStreaming(Ctx ctx) throws IOException {
        int timeout = ZipState.TIMEOUT.getOrDefault(ctx);
        int count = ZipState.COUNT.getOrDefault(ctx);

        Response response = ctx.getResponse();

        setHeaders(response, "streaming");
        ctx.startStreaming();

        createZip(response.getOutputStream(), count, timeout);
    }

    private void setHeaders(Response response, String zipFileName) {
        response.setHeader("Content-Disposition", "attachment; filename=" + zipFileName + ".zip");
        response.setContentType("application/zip");
        response.setStatus(HttpURLConnection.HTTP_OK);
    }

    private void doOther(Ctx ctx) {
        ctx.getResponse().setStatus(HttpURLConnection.HTTP_BAD_METHOD);
    }

    private void createZip(OutputStream outputStream, int count, int timeout) throws IOException {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        try (ZipOutputStream zipOutputStream = new ZipOutputStream(outputStream)) {
            for (int i = 0; i < count; i++) {
                String filename = "file_" + i + ".txt";
                ZipEntry zipEntry = new ZipEntry(filename);
                zipOutputStream.putNextEntry(zipEntry);
                int s = random.nextInt(600000);

                zipOutputStream.write(
                        random
                                .doubles(s)
                                .mapToObj(String::valueOf)
                                .collect(Collectors.toList())
                                .toString()
                                .getBytes(StandardCharsets.UTF_8)
                );
                zipOutputStream.closeEntry();
                zipOutputStream.flush();
                if (timeout > 0) {
                    try {
                        LOGGER.info("Sleep: timeout in sec = " + timeout + ", i = " + i + " from " + count);
                        TimeUnit.SECONDS.sleep(timeout);
                    } catch (InterruptedException e) {
                        LOGGER.throwing("GetHttpZipServer", "zip", e);
                        return;
                    }
                }
            }
            zipOutputStream.finish();
        }
    }

}
