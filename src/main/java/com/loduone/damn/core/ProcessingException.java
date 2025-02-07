package com.loduone.damn.core;

public class ProcessingException extends RuntimeException {
    private int errorCode;

    public ProcessingException(int errorCode, Throwable cause) {
        super(cause);
        this.errorCode = errorCode;
    }

    public ProcessingException(int errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public int getErrorCode() {
        return this.errorCode;
    }

    public String toString() {
        return "ProcessingException{errorCode=" + this.errorCode + '}' + super.toString();
    }
}