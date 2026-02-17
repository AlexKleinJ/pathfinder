package org.example.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;

@RestControllerAdvice
public class ExceptionHandleAdvise {

    @ExceptionHandler(NoRouteException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleValidationErrors(NoRouteException ex) {
        return new ErrorResponse(HttpStatus.BAD_REQUEST.value(), "No route found");
    }
}
