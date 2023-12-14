package com.zhuk.weather.enums;

import lombok.Getter;

@Getter
public enum WeatherMessageEnum {
    API_KEY_INVALID("weatherapi.wrong-key-message"),
    API_KEY_DISABLED("weatherapi.disabled-key-message"),
    INVALID_URL("weatherapi.invalid-url-message"),
    UNKNOWN_EXCEPTION("weatherapi.unknown-exception-message"),
    TOO_MANY_REQUESTS("weatherapi.too-many-requests-message"),
    INCORRECT_DATE_RANGE("weather.incorrect-date-range-message");
    private final String code;
    WeatherMessageEnum(String code) {
        this.code = code;
    }
}
