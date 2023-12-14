package com.zhuk.weather.dto;

import lombok.Builder;
import lombok.Value;
import org.springframework.http.HttpStatus;

@Value
@Builder(toBuilder = true)
public class BaseExceptionDto {
    String message;
    HttpStatus httpStatus;
}
