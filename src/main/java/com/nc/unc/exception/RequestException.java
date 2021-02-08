package com.nc.unc.exception;

import org.springframework.http.HttpStatus;

public class RequestException extends RuntimeException {

    private HttpStatus httpStatus;

    public RequestException(){}

    public RequestException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }

    public RequestException(String message, Throwable cause) {
        super(message, cause);
    }

    public RequestException(Throwable cause) {
        super(cause);
    }

    protected RequestException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
