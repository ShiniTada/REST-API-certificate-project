package com.epam.esm.exception;

public class WrongIdException extends CustomServiceException {
    private static final int ID_INVALID_ERROR_CODE = 404;
    private static final String ID_INVALID_MESSAGE = "wrongIdError";
    private Long id;

    public WrongIdException(Long id) {
        this.id = id;
    }

    @Override
    public int getErrorCode() {
        return ID_INVALID_ERROR_CODE;
    }

    @Override
    public String getErrorMessage() {
        return ID_INVALID_MESSAGE;
    }

    public Long getId() {
        return id;
    }
}
