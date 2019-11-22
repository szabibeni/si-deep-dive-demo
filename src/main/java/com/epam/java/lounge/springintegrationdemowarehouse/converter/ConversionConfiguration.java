package com.epam.java.lounge.springintegrationdemowarehouse.converter;

import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.support.DefaultConversionService;

@Configuration
public class ConversionConfiguration {

    @Bean
    public ConversionService conversionService(List<Converter> converters) {
        DefaultConversionService conversionService = new DefaultConversionService();
        converters.forEach(conversionService::addConverter);
        return conversionService;
    }

    @Bean
    public ItemToWarehouseItemConverter itemToWarehouseItemConverter() {
        return new ItemToWarehouseItemConverter();
    }
}
