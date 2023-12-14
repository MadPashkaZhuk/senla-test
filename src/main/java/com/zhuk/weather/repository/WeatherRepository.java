package com.zhuk.weather.repository;

import com.zhuk.weather.entity.WeatherEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface WeatherRepository extends JpaRepository<WeatherEntity, UUID> {
    List<WeatherEntity> getWeatherEntitiesByDatetimeBetween(LocalDateTime from, LocalDateTime to);
}
