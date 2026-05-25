package com.moinammaoueni.smartHire.api.exception;

public class CandidatNotFoundException
        extends RuntimeException {

    public CandidatNotFoundException(String message) {
        super(message);
    }
}