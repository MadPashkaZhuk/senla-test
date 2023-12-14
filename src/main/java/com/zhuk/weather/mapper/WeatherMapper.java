package com.zhuk.weather.mapper;

import com.zhuk.weather.dto.WeatherApiDTO;
import com.zhuk.weather.dto.WeatherDto;
import com.zhuk.weather.entity.WeatherEntity;
import org.mapstruct.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface WeatherMapper {
    WeatherDto map(WeatherEntity weatherEntity);

    @Mapping(source = "location.name", target = "city")
    @Mapping(source = "location.localtime", target = "datetime", qualifiedByName = "stringToLocalDateTime")
    @Mapping(source = "current.temperatureInCelsius", target = "temperature")
    @Mapping(source = "current.windKph", target = "wind")
    @Mapping(source = "current.pressureMb", target = "pressure")
    @Mapping(source = "current.humidity", target = "humidity")
    @Mapping(source = "current.condition.text", target = "condition")
    WeatherEntity fromWeatherApiDtoToWeatherEntity(WeatherApiDTO weatherApiDTO);

    @Mapping(source = "location.name", target = "city")
    @Mapping(source = "location.localtime", target = "datetime", qualifiedByName = "stringToLocalDateTime")
    @Mapping(source = "current.temperatureInCelsius", target = "temperature")
    @Mapping(source = "current.windKph", target = "wind")
    @Mapping(source = "current.pressureMb", target = "pressure")
    @Mapping(source = "current.humidity", target = "humidity")
    @Mapping(source = "current.condition.text", target = "condition")
    WeatherDto fromWeatherApiDtoToWeatherDto(WeatherApiDTO weatherApiDTO);

    @Named("stringToLocalDateTime")
    default LocalDateTime mapStringToLocalDateTime(String localtime) {
        return LocalDateTime.parse(localtime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }
}
