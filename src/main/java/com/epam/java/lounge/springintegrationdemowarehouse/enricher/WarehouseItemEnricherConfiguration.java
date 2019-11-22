package com.epam.java.lounge.springintegrationdemowarehouse.enricher;

import com.epam.java.lounge.springintegrationdemowarehouse.configuration.Channels;
import com.epam.java.lounge.springintegrationdemowarehouse.service.SerialNumberService;
import com.epam.java.lounge.springintegrationdemowarehouse.transformer.SerialNumberTransformer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.transformer.ContentEnricher;

@Configuration
public class WarehouseItemEnricherConfiguration {

    @Bean
    @ServiceActivator(inputChannel = Channels.TRANSFORMED_MESSAGE_CHANNEL)
    public ContentEnricher contentEnricher() {
        ContentEnricher contentEnricher = new ContentEnricher();
        contentEnricher.setOutputChannelName(Channels.ENRICHED_MESSAGE_CHANNEL);
        contentEnricher.setRequestChannelName(Channels.ENRICH_REQUEST_MESSAGE_CHANNEL);
        contentEnricher.setReplyChannelName(Channels.ENRICH_REPLY_MESSAGE_CHANNEL);
        contentEnricher.setErrorChannelName(Channels.ENRICH_ERROR_MESSAGE_CHANNEL);

        return contentEnricher;
    }

    @Bean
    @ServiceActivator(inputChannel = Channels.ENRICH_REQUEST_MESSAGE_CHANNEL, outputChannel = Channels.ENRICH_REPLY_MESSAGE_CHANNEL)
    public SerialNumberTransformer serialNumberTransformer(SerialNumberService serialNumberService) {
        return new SerialNumberTransformer(serialNumberService);
    }
}
