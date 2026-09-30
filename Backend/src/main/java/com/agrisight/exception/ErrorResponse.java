package com.agrisight.exception;

import java.time.Instant;
import java.util.Map;

public class ErrorResponse {
    private Instant timestamp;
    private int status;
    private String code;
    private String message;
    private String path;
    private Map<String, String> validationErrors;

    public ErrorResponse() {
        this.timestamp = Instant.now();
    }

    public ErrorResponse(int status, String code, String message, String path) {
        this.timestamp = Instant.now();
        this.status = status;
        this.code = code;
        this.message = message;
        this.path = path;
    }

    public ErrorResponse(int status, String code, String message, String path, Map<String, String> validationErrors) {
        this.timestamp = Instant.now();
        this.status = status;
        this.code = code;
        this.message = message;
        this.path = path;
        this.validationErrors = validationErrors;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public int getStatus() {
        return status;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public String getPath() {
        return path;
    }

    public Map<String, String> getValidationErrors() {
        return validationErrors;
    }
}
