package com.zhuk.weather.exception.weatherapi;

import com.zhuk.weather.exception.BaseWeatherException;
import org.springframework.http.HttpStatus;

public class WeatherApiUnknownException extends BaseWeatherException {
    public WeatherApiUnknownException(HttpStatus status, String exceptionMessage) {
        super(exceptionMessage, status);
    }
}
