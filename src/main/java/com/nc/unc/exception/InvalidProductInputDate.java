package com.nc.unc.exception;

public class InvalidProductInputDate extends IllegalArgumentException {

    public InvalidProductInputDate() {
        super("Invalid Product Input Date ");
    }

    public InvalidProductInputDate(Throwable cause) {
        super("Invalid Product Input Date ", cause);
    }

}
