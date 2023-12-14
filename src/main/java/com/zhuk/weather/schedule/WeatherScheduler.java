package com.zhuk.weather.schedule;

import com.zhuk.weather.dto.WeatherDto;
import com.zhuk.weather.service.WeatherService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
@RequiredArgsConstructor
public class WeatherScheduler {
    private final WeatherService weatherService;
    @Scheduled(cron = "${weather.update-schedule.cron}")
    public void saveWeatherInDatabase() {
       WeatherDto weatherDto = weatherService.saveCurrentWeather();
    }
}
