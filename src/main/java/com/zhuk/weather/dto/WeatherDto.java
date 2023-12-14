package com.zhuk.weather.dto;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;

@Value
@Builder
public class WeatherDto {
    double temperature;
    double wind;
    double pressure;
    double humidity;
    String city;
    LocalDateTime datetime;
    String condition;
}
