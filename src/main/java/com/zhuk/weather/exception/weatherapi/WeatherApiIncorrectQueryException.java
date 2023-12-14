package com.zhuk.weather.exception.weatherapi;

import com.zhuk.weather.exception.BaseWeatherException;
import org.springframework.http.HttpStatus;

public class WeatherApiIncorrectQueryException extends BaseWeatherException {
    public WeatherApiIncorrectQueryException(HttpStatus status, String exceptionMessage) {
        super(exceptionMessage, status);
    }
}
