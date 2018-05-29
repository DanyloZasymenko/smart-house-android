package com.danik.smarthouse.service.exceptions;

public class JsonParseException extends RuntimeException {
    private String prefix = "JsonParseException:[ ";
    private String message;

    public JsonParseException(String message) {
        this.message = prefix + message + " ]";
    }

    @Override
    public String getMessage() {
        return message;
    }
}
