package com.epam.esm.controller;

import com.epam.esm.entity.ErrorResponse;
import com.epam.esm.exception.CertificateNotFoundException;
import com.epam.esm.exception.WrongIdException;
import com.epam.esm.exception.WrongDataFormException;
import com.epam.esm.exception.TagNotFoundException;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Locale;

@ControllerAdvice
@ResponseBody
@AllArgsConstructor
public class ExceptionManager extends ResponseEntityExceptionHandler {
    private final MessageSource messageSource;

    private static final int SERVER_ERROR_CODE = 100;
    private static final String SERVER_ERROR_TEXT = "serverError";
    private static final String DELIMITER = " ";
    private static final String COMMA_DELIMITER = ",";
    @ExceptionHandler(CertificateNotFoundException.class)
    public ResponseEntity<ErrorResponse> controllerNotFoundException(CertificateNotFoundException exception, Locale locale) {

        String errorMessage = messageSource.getMessage(exception.getErrorMessage(), new Object[]{}, locale);
        ErrorResponse response = new ErrorResponse();
        response.setErrorMessage(errorMessage + DELIMITER + exception.getMessage());
        response.setErrorCode(exception.getErrorCode());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
/*
    @ExceptionHandler(TagNotFoundException.class)
    public ResponseEntity<ErrorResponse> tagNotFoundException(TagNotFoundException exception, Locale locale) {

        String message = messageSource.getMessage(exception.getErrorMessage(), new Object[]{}, locale);
        String errorMessage = message + DELIMITER + exception.getMessage();
        return new ResponseEntity<>(createErrorResponse(errorMessage, exception.getErrorCode()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(WrongDataFormException.class)
    public ResponseEntity<Object> invalidDataFormException(WrongDataFormException exception, Locale locale) {

        StringBuilder errorMessage = new StringBuilder();
        errorMessage.append(messageSource.getMessage(exception.getErrorMessage(), new Object[]{}, locale));
        List<ObjectError> errors = exception.getBindingResult().getAllErrors();
        errors.stream().filter(FieldError.class::isInstance)
                .forEach(objectError -> errorMessage
                        .append(DELIMITER)
                        .append(messageSource.getMessage(objectError, locale))
                        .append(COMMA_DELIMITER));
        errorMessage.deleteCharAt(errorMessage.length() - 1);
        return new ResponseEntity<>(createErrorResponse(errorMessage.toString(), exception.getErrorCode()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Object> internalServerError(RuntimeException exception, Locale locale) {
        String errorMessage = messageSource.getMessage(SERVER_ERROR_TEXT, new Object[]{}, locale);
        return new ResponseEntity<>(createErrorResponse(errorMessage, SERVER_ERROR_CODE), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(WrongIdException.class)
    public ResponseEntity<Object> handleIdInvalidException(WrongIdException exception, Locale locale) {
        String message = messageSource.getMessage(exception.getErrorMessage(), new Object[]{}, locale);
        String errorMessage = message + DELIMITER + exception.getId();
        return new ResponseEntity<>(createErrorResponse(errorMessage, exception.getErrorCode()), HttpStatus.BAD_REQUEST);
    }

    private ErrorResponse createErrorResponse(String errorMessage, int errorCode) {
        ErrorResponse response = new ErrorResponse();
        response.setErrorMessage(errorMessage);
        response.setErrorCode(errorCode);
        return response;
    }

 */
}
