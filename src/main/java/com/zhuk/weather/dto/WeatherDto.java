package com.zhuk.weather.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;

@Value
@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class WeatherDto {
    double temperature;
    double wind;
    double pressure;
    double humidity;
    String city;
    LocalDateTime datetime;
    String condition;
}
