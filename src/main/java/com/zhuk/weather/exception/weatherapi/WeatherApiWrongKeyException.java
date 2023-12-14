package com.zhuk.weather.exception.weatherapi;

import com.zhuk.weather.exception.BaseWeatherException;
import org.springframework.http.HttpStatus;

public class WeatherApiWrongKeyException extends BaseWeatherException {
    public WeatherApiWrongKeyException(HttpStatus status, String exceptionMessage) {
        super(exceptionMessage, status);
    }
}
