package ru.sample.network.complex.utils.wrappers;

import com.sun.net.httpserver.HttpExchange;
import ru.sample.network.complex.utils.RequestMethod;

import java.io.Closeable;
import java.net.URI;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Ctx implements Closeable {

    private final HttpExchange exchange;
    private final Map<String, String> queryAttributes;
    private final RequestMethod requestMethod;
    private final Response response;
    private final boolean streaming;
    private boolean streamStarted;

    public Ctx(HttpExchange exchange, boolean streaming) {
        this.exchange = exchange;
        this.queryAttributes = parseQuery(exchange);
        this.requestMethod = RequestMethod.convert(exchange.getRequestMethod());
        this.streaming = streaming;
        this.response = new Response(exchange, streaming);
    }

    public String getAttribute(String key) {
        return queryAttributes.get(key);
    }

    public URI getURI() {
        return exchange.getRequestURI();
    }

    public RequestMethod getMethod() {
        return this.requestMethod;
    }

    public Response getResponse() {
        return response;
    }

    public void startStreaming() {
        if (!streaming) {
            throw new IllegalArgumentException("It's not a streaming context");
        }
        if (streamStarted) {
            throw new IllegalArgumentException("Stream is already starter");
        }
        sendHeaders();
        streamStarted = true;
    }

    @Override
    public void close() {
        try {
            if (!streamStarted) {
                sendHeaders();
            }
            response.writeTo(exchange.getResponseBody());
            exchange.close();
        } catch (Exception e) {
            /* empty */
            e.printStackTrace();
        }
    }

    private void sendHeaders() {
        try {
            exchange.getResponseHeaders().putAll(response.getHeaders());
            exchange.sendResponseHeaders(response.getStatus(), response.getContentLength());
        } catch (Exception e) {
            /* empty */
        }
    }

    private Map<String, String> parseQuery(HttpExchange httpExchange) {
        String query = httpExchange.getRequestURI().getQuery();
        if (null != query && !query.isEmpty()) {
            String[] pairs = query.split("[=&]");
            Map<String, String> map = new HashMap<>(pairs.length / 2);
            for (int i = 0; i < pairs.length - 1; i += 2) {
                map.put(pairs[i], pairs[i + 1]);
            }
            return map;
        } else {
            return Collections.emptyMap();
        }
    }
}
