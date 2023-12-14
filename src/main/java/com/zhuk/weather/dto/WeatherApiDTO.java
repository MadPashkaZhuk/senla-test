package com.zhuk.weather.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

@Value
public class WeatherApiDTO {
    Location location;
    Current current;
    Error error;

    @Value
    public static class Location {
        String name;
        String localtime;
        @JsonCreator
        public Location(@JsonProperty("name") String name,
                        @JsonProperty("localtime") String localtime) {
            this.name = name;
            this.localtime = localtime;
        }
    }
    @Value
    public static class Current {
        double temperatureInCelsius;
        double windKph;
        double pressureMb;
        double humidity;
        Condition condition;
        @JsonCreator
        public Current(@JsonProperty("temp_c") double temperatureInCelsius,
                       @JsonProperty("condition") Condition condition,
                       @JsonProperty("wind_kph") double windKph,
                       @JsonProperty("pressure_mb") double pressureMb,
                       @JsonProperty("humidity") double humidity) {
            this.temperatureInCelsius = temperatureInCelsius;
            this.windKph = windKph;
            this.pressureMb = pressureMb;
            this.humidity = humidity;
            this.condition = condition;
        }
        @Value
        public static class Condition {
            String text;
            @JsonCreator
            public Condition(@JsonProperty("text") String text) {
                this.text = text;
            }
        }
    }
    @Value
    public static class Error {
        int code;
        String message;
    }
}
