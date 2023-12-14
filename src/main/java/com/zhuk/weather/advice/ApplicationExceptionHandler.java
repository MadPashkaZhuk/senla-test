package com.zhuk.weather.advice;

import com.zhuk.weather.dto.BaseExceptionDto;
import com.zhuk.weather.exception.BaseWeatherException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ApplicationExceptionHandler {
    @ExceptionHandler(BaseWeatherException.class)
    public ResponseEntity<BaseExceptionDto> handleBaseWeatherApiException(BaseWeatherException ex) {
        BaseExceptionDto baseWeatherApiExceptionDTO = BaseExceptionDto.builder()
                .message(ex.getMessage())
                .httpStatus(ex.getHttpStatus())
                .build();
        return new ResponseEntity<>(baseWeatherApiExceptionDTO, ex.getHttpStatus());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleInvalidArgument(MethodArgumentNotValidException exception) {
        Map<String, String> errors = new HashMap<>();
        exception.getBindingResult().getFieldErrors().forEach(x -> errors.put(x.getField(), x.getDefaultMessage()));
        return errors;
    }

    @ExceptionHandler(Throwable.class)
    public ResponseEntity<BaseExceptionDto> handleUnexpectedExceptions(Throwable exception) {
        BaseExceptionDto baseExceptionDTO = BaseExceptionDto.builder()
                .message(exception.getMessage())
                .httpStatus(HttpStatus.INTERNAL_SERVER_ERROR)
                .build();
        return new ResponseEntity<>(baseExceptionDTO, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
