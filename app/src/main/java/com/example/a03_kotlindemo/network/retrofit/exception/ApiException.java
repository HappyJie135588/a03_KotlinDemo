package com.example.a03_kotlindemo.network.retrofit.exception;

public class ApiException extends Exception {
    public int code;
    public String displayMessage;

    public ApiException(int code, String displayMessage) {
        this.code = code;
        this.displayMessage = displayMessage;
    }

    public ApiException(int code, String message, String displayMessage) {
        super(message);
        this.code = code;
        this.displayMessage = displayMessage;
    }
}