package ru.sample.network.complex.utils.wrappers;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sun.net.httpserver.HttpExchange;

public class Response {

    private static final int BODY_SIZE = 1024;
    private final Map<String, List<String>> headers = new HashMap<>();
    private final OutputStream outputStream;
    private int status = HttpURLConnection.HTTP_BAD_METHOD;
    private long contentLength;

    public Response(HttpExchange exchange, boolean streaming) {
        this.outputStream = streaming
                ? exchange.getResponseBody()
                : new ByteArrayOutputStream(BODY_SIZE);
    }

    public void setStatus(int status) {
        this.status = status;
    }

    int getStatus() {
        return status;
    }

    public void setContentLength(long contentLength) {
        this.contentLength = contentLength;
    }

    long getContentLength() {
        if (contentLength == 0 && this.outputStream instanceof ByteArrayOutputStream) {
            return ((ByteArrayOutputStream) this.outputStream).size();
        }
        return contentLength;
    }

    public void setContentType(String contentType) {
        setHeader("Content-Type", contentType);
    }

    public void setHeader(String key, String value) {
        headers.put(key, Collections.singletonList(value));
    }

    Map<String, List<String>> getHeaders() {
        return headers;
    }

    public OutputStream getOutputStream() {
        return outputStream;
    }

    void writeTo(OutputStream outputStream) throws IOException {
        if (this.outputStream instanceof ByteArrayOutputStream) {
            ((ByteArrayOutputStream) this.outputStream).writeTo(outputStream);
        }
    }

}
