package com.nc.unc.exception;

public class InvalidCustomerInputDate extends IllegalArgumentException {
    public InvalidCustomerInputDate() {
        super("Invalid Customer Input Date ");
    }

    public InvalidCustomerInputDate(Throwable cause) {
        super("Invalid Customer Input Date ", cause);
    }
}
