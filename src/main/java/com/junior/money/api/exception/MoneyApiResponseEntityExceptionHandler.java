package com.junior.money.api.exception;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class MoneyApiResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
            HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        ApiErrorMessage apiErrorMessage = new ApiErrorMessage(status, new ValidationError("Invalid request body"));
        return handleExceptionInternal(ex, apiErrorMessage, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
            HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        List<ValidationError> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(err -> new ValidationError(err.getField(), err.getDefaultMessage()))
                .collect(Collectors.toList());

        ApiErrorMessage apiErrorMessage = new ApiErrorMessage(status, errors);

        return handleExceptionInternal(ex, apiErrorMessage, headers, status, request);
    }

    @ExceptionHandler({EmptyResultDataAccessException.class})
    public ResponseEntity<Object> handleResultDataAccessException(EmptyResultDataAccessException ex, WebRequest request){
        ApiErrorMessage apiErrorMessage = new ApiErrorMessage(HttpStatus.NOT_FOUND, new ValidationError("The resource was not found"));
        return handleExceptionInternal(ex, apiErrorMessage, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler({DataIntegrityViolationException.class})
    public ResponseEntity<Object> handleDataIntegrityViolationException(DataIntegrityViolationException ex, WebRequest request){
        ApiErrorMessage apiErrorMessage = new ApiErrorMessage(HttpStatus.BAD_REQUEST, new ValidationError("Operation not allowed. Review the data sent and try again."));
        return handleExceptionInternal(ex, apiErrorMessage, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler({PersonInactiveOrMissingException.class})
    public ResponseEntity<Object> handlePersonInactiveOrMissingException(PersonInactiveOrMissingException ex, WebRequest request){
        ApiErrorMessage apiErrorMessage = new ApiErrorMessage(HttpStatus.BAD_REQUEST, new ValidationError("The person is inactive or missing."));
        return handleExceptionInternal(ex, apiErrorMessage, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler({UserAuthenticationException.class})
    public ResponseEntity<Object> handleUserAuthenticationException(UserAuthenticationException ex, WebRequest request){
        ApiErrorMessage apiErrorMessage = new ApiErrorMessage(HttpStatus.BAD_REQUEST, new ValidationError(ex.getMessage()));
        return handleExceptionInternal(ex, apiErrorMessage, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

}
