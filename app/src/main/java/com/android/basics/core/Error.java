package com.android.basics.core;

public class Error {

    private final Exception exception;

    public Error(Exception exception) {
        this.exception = exception;
    }

    public Exception getException() {
        return exception;
    }

}
