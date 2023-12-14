package com.zhuk.weather.utils;

import com.zhuk.weather.enums.WeatherMessageEnum;
import lombok.AllArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
@AllArgsConstructor
public class MessageSourceWrapper {
    private final MessageSource messageSource;
    public String getMessageCode(WeatherMessageEnum weatherMessageEnum) {
        return messageSource.getMessage(weatherMessageEnum.getCode(), null, Locale.getDefault());
    }
}
