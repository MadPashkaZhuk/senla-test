package com.zhuk.weather.schedule;

import com.zhuk.weather.dto.WeatherDto;
import com.zhuk.weather.service.WeatherService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@EnableScheduling
@RequiredArgsConstructor
@Slf4j
public class WeatherScheduler {
    private final WeatherService weatherService;
    @Scheduled(cron = "${weather.update-schedule.cron}")
    public void saveWeatherInDatabase() {
        WeatherDto weatherDto = weatherService.saveCurrentWeather();
        log.info("Saving " + weatherDto + " at " + LocalDateTime.now());
    }
}
