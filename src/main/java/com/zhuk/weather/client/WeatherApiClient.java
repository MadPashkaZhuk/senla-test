package com.zhuk.weather.client;

import com.zhuk.weather.dto.WeatherApiDTO;
import com.zhuk.weather.enums.WeatherMessageEnum;
import com.zhuk.weather.exception.BaseWeatherException;
import com.zhuk.weather.exception.weatherapi.*;
import com.zhuk.weather.utils.MessageSourceWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Component
@RequiredArgsConstructor
public class WeatherApiClient {
    private final RestTemplate restTemplate;
    private final MessageSourceWrapper messageSource;
    private final WeatherApiProperties weatherApiProperties;

    public WeatherApiDTO getDtoFromWeatherApi() {
        ResponseEntity<WeatherApiDTO> responseEntity = getResponseFromWeatherApi();
        return responseEntity.getBody();
    }

    private ResponseEntity<WeatherApiDTO> getResponseFromWeatherApi() {
        ResponseEntity<WeatherApiDTO> responseEntity;
        try {
            responseEntity = restTemplate.exchange(
                    getDefaultUriForMinsk(),
                    HttpMethod.GET,
                    null,
                    WeatherApiDTO.class
            );
            return responseEntity;
        } catch (HttpStatusCodeException ex) {
            throw getWeatherApiExceptionFromHttpException(ex);
        } catch (Throwable ex) {
            throw new WeatherApiUnknownException(HttpStatus.INTERNAL_SERVER_ERROR,
                    messageSource.getMessageCode(WeatherMessageEnum.UNKNOWN_EXCEPTION));
        }
    }

    private BaseWeatherException getWeatherApiExceptionFromHttpException(HttpStatusCodeException ex) {
        BaseWeatherException exception;
        if(ex.getStatusCode() == HttpStatus.BAD_REQUEST) {
            exception = new WeatherApiIncorrectQueryException(HttpStatus.BAD_REQUEST,
                    messageSource.getMessageCode(WeatherMessageEnum.INVALID_URL));
        } else if(ex.getStatusCode() == HttpStatus.UNAUTHORIZED) {
            exception = new WeatherApiWrongKeyException(HttpStatus.UNAUTHORIZED,
                    messageSource.getMessageCode(WeatherMessageEnum.API_KEY_INVALID));
        } else if(ex.getStatusCode() == HttpStatus.FORBIDDEN) {
            exception = new WeatherApiDisabledKeyException(HttpStatus.FORBIDDEN,
                    messageSource.getMessageCode(WeatherMessageEnum.API_KEY_DISABLED));
        } else {
            exception = new WeatherApiUnknownException(HttpStatus.INTERNAL_SERVER_ERROR,
                    messageSource.getMessageCode(WeatherMessageEnum.UNKNOWN_EXCEPTION));
        }
        return exception;
    }

    private String getDefaultUriForMinsk() {
        return UriComponentsBuilder.fromUriString(weatherApiProperties.getUrl())
                .queryParam("key", weatherApiProperties.getKey())
                .queryParam("q", "Minsk")
                .queryParam("aqi", "no")
                .toUriString();
    }
}
