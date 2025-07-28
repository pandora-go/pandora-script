package com.github.pandora.exception;

public class ParseException extends RuntimeException {

    public ParseException() {
    }

    public ParseException(String message) {
        super(message);
    }

    public ParseException(Throwable cause) {
        super(cause);
    }
}
