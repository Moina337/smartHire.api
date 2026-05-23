package com.moinammaoueni.smartHire.api.exception;


public class JobNotFoundException
        extends RuntimeException {

    public JobNotFoundException(String message) {
        super(message);
    }
}
