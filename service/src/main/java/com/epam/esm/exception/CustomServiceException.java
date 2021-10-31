package com.epam.esm.exception;

public abstract class CustomServiceException extends RuntimeException {

    protected CustomServiceException() {
    }

    protected CustomServiceException(String message) {
        super(message);
    }

    protected abstract int getErrorCode();

    protected abstract String getErrorMessage();
}
