package com.example.titoa_000.darkskyapi.events;

public class ErrorEvent {
    private final String errorMessage;

    public ErrorEvent(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
