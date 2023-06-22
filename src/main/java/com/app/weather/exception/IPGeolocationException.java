package com.app.weather.exception;

public class IPGeolocationException extends RuntimeException {

    public IPGeolocationException(String message) {
        super(message);
    }

    public IPGeolocationException(String message, Throwable cause) {
        super(message, cause);
    }
}

