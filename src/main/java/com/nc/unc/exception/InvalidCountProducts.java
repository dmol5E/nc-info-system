package com.nc.unc.exception;

public class InvalidCountProducts extends IllegalArgumentException {

    public InvalidCountProducts() {
        super("Invalid Count Products ");
    }

    public InvalidCountProducts(Throwable cause) {
        super("Invalid Count Products ", cause);
    }
}
