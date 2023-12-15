package com.zhuk.weather.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zhuk.weather.dto.AverageTempDto;
import com.zhuk.weather.dto.RangeForAverageTempDto;
import com.zhuk.weather.dto.WeatherDto;
import com.zhuk.weather.exception.weather.IncorrectDateRangeException;
import com.zhuk.weather.service.WeatherService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(WeatherController.class)
@AutoConfigureMockMvc(addFilters = false)
public class WeatherControllerTest {
    @Autowired
    MockMvc mockMvc;
    @MockBean
    WeatherService weatherService;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void getAllWeather_ShouldReturnAllWeather_WhenDataExists() throws Exception {
        WeatherDto weatherDto1 = WeatherDto.builder()
                .city("Minsk")
                .temperature(10.1)
                .pressure(15)
                .wind(20)
                .humidity(25)
                .condition("Fog")
                .datetime(LocalDateTime.of(2023, 11, 5, 18, 45))
                .build();
        when(weatherService.getCurrentWeather()).thenReturn(weatherDto1);
        mockMvc.perform(get("/api/weather/current"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(weatherDto1)));
    }

    @Test
    public void getAverageTempForPeriod_ShouldThrowBadRequest_WhenFromIsAfterToInDto() throws Exception {
        RangeForAverageTempDto requestBody = new RangeForAverageTempDto(LocalDate.of(2023, 11,2),
                LocalDate.of(2023, 11,30));
        when(weatherService.getAverageTempForPeriod(requestBody))
                .thenThrow(new IncorrectDateRangeException("MESSAGE", HttpStatus.BAD_REQUEST));
        mockMvc.perform(get("/api/weather/average")
                        .content(objectMapper.writeValueAsString(requestBody))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void getAverageTempForPeriod_ShouldReturnAverage_WhenHappyPath() throws Exception {
    AverageTempDto response = AverageTempDto.builder()
            .averageTemp(100).build();
    RangeForAverageTempDto requestBody = new RangeForAverageTempDto(LocalDate.of(2023, 11,1),
            LocalDate.of(2023, 11,1));
    when(weatherService.getAverageTempForPeriod(requestBody))
            .thenReturn(response);
    mockMvc.perform(get("/api/weather/average")
                    .content(objectMapper.writeValueAsString(requestBody))
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().json(objectMapper.writeValueAsString(response)));
    }
}
