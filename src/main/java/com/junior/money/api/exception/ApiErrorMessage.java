package com.junior.money.api.exception;

import java.util.List;

import org.springframework.http.HttpStatusCode;

public class ApiErrorMessage {

    private HttpStatusCode status;
    private List<ValidationError> errors;

    public ApiErrorMessage(HttpStatusCode status, List<ValidationError> errors) {
        this.status = status;
        this.errors = errors;
    }

    public ApiErrorMessage(HttpStatusCode status, ValidationError error) {
        this.status = status;
        this.errors = List.of(error);
    }

    public HttpStatusCode getStatus() {
        return status;
    }

    public void setStatus(HttpStatusCode status) {
        this.status = status;
    }

    public List<ValidationError> getErrors() {
        return errors;
    }

    public void setErrors(List<ValidationError> errors) {
        this.errors = errors;
    }
}
