package com.zhuk.weather.client;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "weatherapi")
public class WeatherApiProperties {
    private String key;
    private String url;
}
