package com.zhuk.weather.service;


import com.zhuk.weather.client.WeatherApiClient;
import com.zhuk.weather.dto.WeatherApiDTO;
import com.zhuk.weather.dto.WeatherDto;
import com.zhuk.weather.entity.WeatherEntity;
import com.zhuk.weather.mapper.WeatherMapper;
import com.zhuk.weather.repository.WeatherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WeatherService {
    private final WeatherRepository weatherRepository;
    private final WeatherApiClient weatherApiClient;
    private final WeatherMapper weatherMapper;
    public WeatherDto getCurrentWeather() {
        WeatherApiDTO weatherApiDTO = weatherApiClient.getDtoFromWeatherApi();
        return weatherMapper.fromWeatherApiDtoToWeatherDto(weatherApiDTO);
    }

    public WeatherDto saveCurrentWeather() {
        WeatherApiDTO weatherApiDTO = weatherApiClient.getDtoFromWeatherApi();
        WeatherEntity weatherEntity = weatherRepository
                .save(weatherMapper.fromWeatherApiDtoToWeatherEntity(weatherApiDTO));
        return weatherMapper.map(weatherEntity);
    }
}
