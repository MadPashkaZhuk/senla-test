package com.zhuk.weather.service;

import com.zhuk.weather.client.WeatherApiClient;
import com.zhuk.weather.dto.AverageTempDto;
import com.zhuk.weather.dto.RangeForAverageTempDto;
import com.zhuk.weather.dto.WeatherApiDTO;
import com.zhuk.weather.dto.WeatherDto;
import com.zhuk.weather.entity.WeatherEntity;
import com.zhuk.weather.exception.weather.IncorrectDateRangeException;
import com.zhuk.weather.mapper.WeatherMapper;
import com.zhuk.weather.repository.WeatherRepository;
import com.zhuk.weather.utils.MessageSourceWrapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class WeatherServiceTest {
    @Mock
    private WeatherRepository weatherRepository;
    @Mock
    private WeatherApiClient weatherApiClient;
    @Mock
    private WeatherMapper weatherMapper;
    @Mock
    private MessageSourceWrapper messageSourceWrapper;
    @InjectMocks
    private WeatherService weatherService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getCurrentWeather_ShouldReturnWeather_WhenHappyPath() {
        WeatherApiDTO weatherApiDTO = getWeatherApiDTO("Minsk","2023-10-28 14:30", 5,
                10, 15, 20, "Fog");
        WeatherDto mappedDto = WeatherDto.builder()
                .city("Minsk")
                .temperature(5)
                .wind(10)
                .pressure(15)
                .humidity(20)
                .datetime(LocalDateTime.of(2023,10,28,14,30))
                .condition("Fog")
                .build();
        when(weatherApiClient.getDtoFromWeatherApi()).thenReturn(weatherApiDTO);
        when(weatherMapper.fromWeatherApiDtoToWeatherDto(weatherApiDTO)).thenReturn(mappedDto);
        WeatherDto result = weatherService.getCurrentWeather();
        assertNotNull(result);
        verify(weatherApiClient, times(1)).getDtoFromWeatherApi();
        verify(weatherMapper, times(1)).fromWeatherApiDtoToWeatherDto(weatherApiDTO);
    }

    @Test
    void saveCurrentWeather_ShouldSaveWeather_WhenHappyPath() {
        WeatherApiDTO weatherApiDTO = getWeatherApiDTO("Minsk","2023-10-28 14:30", 5,
                10, 15, 20, "Fog");
        WeatherDto mappedDto = WeatherDto.builder()
                .city("Minsk")
                .temperature(5)
                .wind(10)
                .pressure(15)
                .humidity(20)
                .datetime(LocalDateTime.of(2023,10,28,14,30))
                .condition("Fog")
                .build();
        WeatherEntity weatherEntity = new WeatherEntity();
        when(weatherApiClient.getDtoFromWeatherApi()).thenReturn(weatherApiDTO);
        when(weatherMapper.fromWeatherApiDtoToWeatherEntity(weatherApiDTO)).thenReturn(weatherEntity);
        when(weatherRepository.save(weatherEntity)).thenReturn(weatherEntity);
        when(weatherMapper.map(weatherEntity)).thenReturn(mappedDto);

        WeatherDto result = weatherService.saveCurrentWeather();

        assertNotNull(result);
        verify(weatherApiClient, times(1)).getDtoFromWeatherApi();
        verify(weatherMapper, times(1)).fromWeatherApiDtoToWeatherEntity(weatherApiDTO);
        verify(weatherRepository, times(1)).save(weatherEntity);
        verify(weatherMapper, times(1)).map(weatherEntity);
    }

    @Test
    void getAverageTempForPeriod_ShouldThrowException_whenInvalidDateRange() {
        RangeForAverageTempDto rangeDto = new RangeForAverageTempDto(LocalDate.now(), LocalDate.now().minusDays(1));
        WeatherEntity weatherEntity = new WeatherEntity();
        when(weatherRepository.getWeatherEntitiesByDatetimeBetween(any(), any())).thenReturn(Collections.singletonList(weatherEntity));
        assertThrows(IncorrectDateRangeException.class, () -> weatherService.getAverageTempForPeriod(rangeDto));
    }

    @Test
    void getAverageTempForPeriod_ShouldReturnDto_whenValidDateRange() {
        RangeForAverageTempDto rangeDto = new RangeForAverageTempDto(LocalDate.now().minusDays(1), LocalDate.now());
        WeatherEntity weatherEntity = new WeatherEntity();
        when(weatherRepository.getWeatherEntitiesByDatetimeBetween(any(), any()))
                .thenReturn(List.of(weatherEntity));
        AverageTempDto result = weatherService.getAverageTempForPeriod(rangeDto);
        assertNotNull(result);
        verify(weatherRepository, times(1)).getWeatherEntitiesByDatetimeBetween(any(), any());
    }

    private WeatherApiDTO getWeatherApiDTO(String regionName, String dateTime, double temp, double wind,
                                           double pressure, double humidity, String condition) {
        return new WeatherApiDTO(
                new WeatherApiDTO.Location(regionName, dateTime),
                new WeatherApiDTO.Current(temp,
                        new WeatherApiDTO.Current.Condition(condition)
                        , wind, pressure, humidity),
                null
        );
    }
}
