package com.fedorov.beerpj.utils;

import lombok.Data;

@Data
public class ErrorResponse {


    private String message;

    private long timestamp;

    public ErrorResponse(String message, long timestamp) {
        this.message = message;
        this.timestamp = timestamp;
    }
}
