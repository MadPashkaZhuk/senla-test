package com.zhuk.weather.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class BaseWeatherException extends RuntimeException {
    private final String message;
    private final HttpStatus httpStatus;

    public BaseWeatherException(String message, HttpStatus httpStatus) {
        super(message);
        this.message = message;
        this.httpStatus = httpStatus;
    }
}
