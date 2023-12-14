package com.zhuk.weather.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@Table(name = "weather")
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class WeatherEntity {
    @Id
    @UuidGenerator
    private UUID id;
    private String city;
    @Column(name = "date_time")
    private LocalDateTime datetime;
    private String condition;
    private double temperature;
    private double wind;
    private double pressure;
    private double humidity;
}
