package com.epam.esm.exception;

public class CertificateNotFoundException extends CustomServiceException {

    private static final int CERTIFICATE_NOT_FOUND_ERROR_CODE = 103;
    private static final String CERTIFICATE_NOT_FOUND_MESSAGE = "The certificate was not found, id =";

    public CertificateNotFoundException(String message) {
        super(message);
    }

    @Override
    public int getErrorCode() {
        return CERTIFICATE_NOT_FOUND_ERROR_CODE;
    }

    @Override
    public String getErrorMessage() {
        return CERTIFICATE_NOT_FOUND_MESSAGE;
    }
}
