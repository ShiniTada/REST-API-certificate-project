package com.epam.esm.exception;

import org.springframework.validation.BindingResult;

public class WrongDataFormException extends CustomServiceException {
    private static final String MESSAGE = "invalidData";
    private static final int ERROR_CODE = 400;
    private BindingResult bindingResult;

    public WrongDataFormException(BindingResult bindingResult) {
        this.bindingResult = bindingResult;
    }

    @Override
    public int getErrorCode() {
        return ERROR_CODE;
    }

    @Override
    public String getErrorMessage() {
        return MESSAGE;
    }

    public BindingResult getBindingResult() {
        return bindingResult;
    }
}
