package com.junior.money.api.infra.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.junior.money.api.helper.NullAwareBeanUtils;

@Configuration
public class AppConfig {

    @Bean
    NullAwareBeanUtils nullAwareBeanUtils() {
        return new NullAwareBeanUtils();
    }
}
