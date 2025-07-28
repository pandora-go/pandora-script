package com.github.pandora.exception;

public class AssertException extends RuntimeException {

    public AssertException() {
    }

    public AssertException(String message) {
        super(message);
    }

    public AssertException(Throwable cause) {
        super(cause);
    }
}
