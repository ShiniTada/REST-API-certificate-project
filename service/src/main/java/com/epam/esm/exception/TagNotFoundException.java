package com.epam.esm.exception;

public class TagNotFoundException extends CustomServiceException {
    private static final int TAG_NOT_FOUND_ERROR_CODE = 104;
    private static final String TAG_NOT_FOUND_MESSAGE = "The tag was not found, id = ";

    public TagNotFoundException(String message) {
        super(message);
    }

    @Override
    public int getErrorCode() {
        return TAG_NOT_FOUND_ERROR_CODE;
    }

    @Override
    public String getErrorMessage() {
        return TAG_NOT_FOUND_MESSAGE;
    }
}
