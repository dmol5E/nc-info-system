package com.nc.unc.exception;

public class BadRequestException extends IllegalArgumentException {
    public BadRequestException(){
        super("Bad Request Exception");
    }

    public BadRequestException(String string){
        super("Bad Request Exception " + string);
    }

    public BadRequestException(String str, Throwable cause){
        super("Bad Request Exception" + str, cause);
    }

    public BadRequestException(Throwable cause) {
        super(cause);
    }
}
