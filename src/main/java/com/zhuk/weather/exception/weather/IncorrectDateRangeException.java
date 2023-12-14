package com.zhuk.weather.exception.weather;

import com.zhuk.weather.exception.BaseWeatherException;
import org.springframework.http.HttpStatus;

public class IncorrectDateRangeException extends BaseWeatherException {
    public IncorrectDateRangeException(String message, HttpStatus httpStatus) {
        super(message, httpStatus);
    }
}
