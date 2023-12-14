package com.zhuk.weather.dto;

import lombok.Value;
import java.time.LocalDate;

@Value
public class RangeForAverageTempDto {
    LocalDate from;
    LocalDate to;
}
