package com.junior.money.api.exception;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ValidationError {
    private String field;
    private String errorMessage;
    private String developerMessage;

    public ValidationError(String field, String errorMessage, String developerMessage) {
        this.field = field;
        this.errorMessage = errorMessage;
        this.developerMessage = developerMessage;
    }

    public ValidationError(String field, String errorMessage) {
        this.field = field;
        this.errorMessage = errorMessage;
    }

    public ValidationError(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getField() {
        return field;
    }

    public void setField(String message) {
        this.field = message;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getDeveloperMessage() {
        return developerMessage;
    }

    public void setDeveloperMessage(String developerMessage) {
        this.developerMessage = developerMessage;
    }
}
