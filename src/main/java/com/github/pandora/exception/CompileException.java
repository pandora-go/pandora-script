package com.github.pandora.exception;

public class CompileException extends RuntimeException {

    public CompileException() {
    }

    public CompileException(String message) {
        super(message);
    }

    public CompileException(Throwable cause) {
        super(cause);
    }
}
