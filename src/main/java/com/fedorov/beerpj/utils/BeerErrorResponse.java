package com.fedorov.beerpj.utils;

import lombok.Data;

@Data
public class BeerErrorResponse {


    private String message;

    private long timestamp;

    public BeerErrorResponse(String message, long timestamp) {
        this.message = message;
        this.timestamp = timestamp;
    }
}
