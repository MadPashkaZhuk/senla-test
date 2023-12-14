package com.zhuk.weather.exception.weatherapi;

import com.zhuk.weather.exception.BaseWeatherException;
import org.springframework.http.HttpStatus;

public class WeatherApiTooManyRequestsException extends BaseWeatherException {
    public WeatherApiTooManyRequestsException(HttpStatus status, String exceptionMessage) {
        super(exceptionMessage, status);
    }
}
