package org.example.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.http.HttpStatus;

import java.time.Instant;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ErrorResponse(
        int status,
        String error,
        String message,
        Instant timestamp
) {
    public ErrorResponse(int status, String message) {
        this(status, HttpStatus.valueOf(status)
                .getReasonPhrase(), message, Instant.now());
    }
}