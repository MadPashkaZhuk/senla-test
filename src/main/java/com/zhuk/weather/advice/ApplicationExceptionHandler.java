package com.zhuk.weather.advice;

import com.zhuk.weather.dto.BaseExceptionDto;
import com.zhuk.weather.exception.BaseWeatherException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class ApplicationExceptionHandler {
    @ExceptionHandler(BaseWeatherException.class)
    public ResponseEntity<BaseExceptionDto> handleBaseWeatherApiException(BaseWeatherException exception) {
        BaseExceptionDto baseWeatherApiExceptionDTO = BaseExceptionDto.builder()
                .message(exception.getMessage())
                .httpStatus(exception.getHttpStatus())
                .build();
        log.info("BaseWeatherException: " + exception.getMessage());
        return new ResponseEntity<>(baseWeatherApiExceptionDTO, exception.getHttpStatus());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleInvalidArgument(MethodArgumentNotValidException exception) {
        Map<String, String> errors = new HashMap<>();
        exception.getBindingResult().getFieldErrors().forEach(x -> errors.put(x.getField(), x.getDefaultMessage()));
        log.info("MethodArgumentNotValidException: " + errors);
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Throwable.class)
    public ResponseEntity<BaseExceptionDto> handleUnexpectedExceptions(Throwable exception) {
        BaseExceptionDto baseExceptionDTO = BaseExceptionDto.builder()
                .message(exception.getMessage())
                .httpStatus(HttpStatus.INTERNAL_SERVER_ERROR)
                .build();
        log.info("Unknown exception: " + exception.getMessage());
        return new ResponseEntity<>(baseExceptionDTO, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
