package com.zhuk.weather.controller;

import com.zhuk.weather.dto.AverageTempDto;
import com.zhuk.weather.dto.RangeForAverageTempDto;
import com.zhuk.weather.dto.WeatherDto;
import com.zhuk.weather.service.WeatherService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/weather")
@RequiredArgsConstructor
public class WeatherController {
    private final WeatherService weatherService;
    @GetMapping("/current")
    public ResponseEntity<WeatherDto> getCurrentWeather() {
        return ResponseEntity.ok(weatherService.getCurrentWeather());
    }
    @GetMapping("/average")
    public ResponseEntity<AverageTempDto> getAverageTemperature(@RequestBody RangeForAverageTempDto rangeDto) {
        return ResponseEntity.ok(weatherService.getAverageTempForPeriod(rangeDto));
    }
}
