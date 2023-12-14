package com.zhuk.weather.exception.weatherapi;

import com.zhuk.weather.exception.BaseWeatherException;
import org.springframework.http.HttpStatus;

public class WeatherApiDisabledKeyException extends BaseWeatherException {
    public WeatherApiDisabledKeyException(HttpStatus status, String exceptionMessage) {
        super(exceptionMessage, status);
    }
}
