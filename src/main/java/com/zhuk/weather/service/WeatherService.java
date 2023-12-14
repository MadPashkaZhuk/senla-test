package com.zhuk.weather.service;


import com.zhuk.weather.client.WeatherApiClient;
import com.zhuk.weather.dto.AverageTempDto;
import com.zhuk.weather.dto.RangeForAverageTempDto;
import com.zhuk.weather.dto.WeatherApiDTO;
import com.zhuk.weather.dto.WeatherDto;
import com.zhuk.weather.entity.WeatherEntity;
import com.zhuk.weather.enums.WeatherMessageEnum;
import com.zhuk.weather.exception.weather.IncorrectDateRangeException;
import com.zhuk.weather.mapper.WeatherMapper;
import com.zhuk.weather.repository.WeatherRepository;
import com.zhuk.weather.utils.MessageSourceWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WeatherService {
    private final WeatherRepository weatherRepository;
    private final WeatherApiClient weatherApiClient;
    private final WeatherMapper weatherMapper;
    private final MessageSourceWrapper messageSourceWrapper;
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

    public AverageTempDto getAverageTempForPeriod(RangeForAverageTempDto rangeDto) {
        if(rangeDto.getTo().isAfter(rangeDto.getFrom()) ||
                rangeDto.getFrom().isAfter(LocalDate.now())) {
            throw new IncorrectDateRangeException(messageSourceWrapper
                    .getMessageCode(WeatherMessageEnum.INCORRECT_DATE_RANGE),
                    HttpStatus.BAD_REQUEST);
        }
        List<WeatherEntity> weatherEntityList = weatherRepository
                .getWeatherEntitiesByDatetimeBetween(
                        rangeDto.getFrom().atStartOfDay(),
                        rangeDto.getTo().plusDays(1).atStartOfDay()
                );
        return AverageTempDto.builder()
                .averageTemp(
                        weatherEntityList.stream()
                                .mapToDouble(WeatherEntity::getTemperature)
                                .average()
                                .orElse(0.0)
                )
                .build();
    }
}
