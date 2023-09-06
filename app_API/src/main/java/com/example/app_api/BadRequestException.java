package com.example.app_api;


public class BadRequestException extends RuntimeException {

    public BadRequestException(String message) {
        super(message);
    }
}
