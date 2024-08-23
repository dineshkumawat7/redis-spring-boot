package com.example.redis.utils;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ErrorResponse {
    private LocalDateTime timesTemp;
    private Integer statusCode;
    private String status;
    private String message;
}
