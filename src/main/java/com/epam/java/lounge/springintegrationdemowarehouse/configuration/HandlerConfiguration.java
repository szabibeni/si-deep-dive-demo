package com.epam.java.lounge.springintegrationdemowarehouse.configuration;

import com.epam.java.lounge.springintegrationdemowarehouse.model.Item;
import com.epam.java.lounge.springintegrationdemowarehouse.model.WarehouseItem;
import com.epam.java.lounge.springintegrationdemowarehouse.transformer.WarehouseItemTransformer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.ConversionService;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.annotation.Transformer;
import org.springframework.integration.handler.LoggingHandler;
import org.springframework.integration.handler.LoggingHandler.Level;
import org.springframework.integration.http.dsl.Http;
import org.springframework.integration.http.dsl.HttpRequestHandlerEndpointSpec;
import org.springframework.integration.transformer.ContentEnricher;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;

@Configuration
public class HandlerConfiguration {

    @Bean
    public HttpRequestHandlerEndpointSpec incomingMessageHandler(MessageChannel incomingMessageChannel) {
        HttpRequestHandlerEndpointSpec spec = Http.inboundChannelAdapter("/");

        spec.get().setRequestChannel(incomingMessageChannel);
        spec.get().setRequestPayloadTypeClass(Item.class);
        return spec;
    }

    @Bean
    @ServiceActivator(inputChannel = Channels.INCOMING_MESSAGE_CHANNEL)
    public MessageHandler incomingMessageLogger() {
        LoggingHandler loggingHandler = new LoggingHandler(Level.INFO);
        loggingHandler.setLogExpressionString("'Input event: ' + payload");
        return loggingHandler;
    }

    @Bean
    @Transformer(inputChannel = Channels.INCOMING_MESSAGE_CHANNEL, outputChannel = Channels.TRANSFORMED_MESSAGE_CHANNEL)
    public WarehouseItemTransformer warehouseItemTransformer(ConversionService conversionService) {
        return new WarehouseItemTransformer(conversionService);
    }

    @Bean
    @ServiceActivator(inputChannel = Channels.TRANSFORMED_MESSAGE_CHANNEL)
    public MessageHandler transformedMessageLogger() {
        LoggingHandler loggingHandler = new LoggingHandler(Level.INFO);
        loggingHandler.setLogExpressionString("'Message transformed successfully: ' + payload");
        return loggingHandler;
    }

    @Bean
    @ServiceActivator(inputChannel = Channels.ENRICHED_MESSAGE_CHANNEL)
    public MessageHandler enrichedMessageLogger() {
        LoggingHandler loggingHandler = new LoggingHandler(Level.INFO);
        loggingHandler.setLogExpressionString("'Enriched warehouse item: ' + payload");
        return loggingHandler;
    }

    @Bean
    @ServiceActivator(inputChannel = Channels.ENRICH_ERROR_MESSAGE_CHANNEL)
    public MessageHandler enrichErrorMessageLogger() {
        LoggingHandler loggingHandler = new LoggingHandler(Level.ERROR);
        loggingHandler.setLogExpressionString("'Error during message handling: ' + payload");
        return loggingHandler;
    }
}
