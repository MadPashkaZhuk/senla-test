package com.zhuk.weather.controller;

import com.zhuk.weather.dto.WeatherDto;
import com.zhuk.weather.service.WeatherService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/current")
@RequiredArgsConstructor
public class WeatherController {
    private final WeatherService weatherService;

    @GetMapping
    public ResponseEntity<WeatherDto> getCurrentWeather() {
        return ResponseEntity.ok(weatherService.getCurrentWeather());
    }
}
