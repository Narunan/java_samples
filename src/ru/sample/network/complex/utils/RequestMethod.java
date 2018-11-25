package ru.sample.network.complex.utils;

public enum RequestMethod {
    GET,
    POST,

    UNDEFINED
    ;

    public static RequestMethod convert(String method) {
        if ("GET".equals(method)) {
            return GET;
        } else if ("POST".equals(method)) {
            return POST;
        } else {
            return UNDEFINED;
        }
    }
}
