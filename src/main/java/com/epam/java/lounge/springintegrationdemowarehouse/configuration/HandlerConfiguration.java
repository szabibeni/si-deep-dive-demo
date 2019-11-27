package com.epam.java.lounge.springintegrationdemowarehouse.configuration;

import com.epam.java.lounge.springintegrationdemowarehouse.aggregator.DeliveryReleaseStrategy;
import com.epam.java.lounge.springintegrationdemowarehouse.aggregator.OrderCorrelationStrategy;
import com.epam.java.lounge.springintegrationdemowarehouse.filter.WarehouseItemFilter;
import com.epam.java.lounge.springintegrationdemowarehouse.interceptor.LoggerInterceptor;
import com.epam.java.lounge.springintegrationdemowarehouse.transformer.DeliveryCreator;
import com.epam.java.lounge.springintegrationdemowarehouse.transformer.WarehouseItemTransformer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.integration.aggregator.AggregatingMessageHandler;
import org.springframework.integration.aggregator.DefaultAggregatingMessageGroupProcessor;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.annotation.Transformer;
import org.springframework.integration.handler.LoggingHandler;
import org.springframework.integration.handler.LoggingHandler.Level;
import org.springframework.integration.http.converter.SerializingHttpMessageConverter;
import org.springframework.integration.http.dsl.Http;
import org.springframework.integration.http.dsl.HttpRequestHandlerEndpointSpec;
import org.springframework.integration.store.MessageGroupStore;
import org.springframework.integration.store.SimpleMessageStore;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.support.GenericMessage;

import java.util.ArrayList;
import java.util.List;

import static com.epam.java.lounge.springintegrationdemowarehouse.configuration.Channels.*;

@Configuration
public class HandlerConfiguration {

    @Bean
    public HttpRequestHandlerEndpointSpec incomingMessageHandler(MessageChannel incomingMessageChannel) {
        HttpRequestHandlerEndpointSpec spec = Http.inboundChannelAdapter("/");

        List<HttpMessageConverter<?>> converters = new ArrayList<>();
        converters.add(new SerializingHttpMessageConverter());

        spec.get().setMessageConverters(converters);

        spec.get().setRequestChannel(incomingMessageChannel);
        spec.get().setRequestPayloadTypeClass(GenericMessage.class);
        return spec;
    }

    @Bean
    public LoggerInterceptor loggerInterceptor() {
        return new LoggerInterceptor();
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

    @Bean
    public WarehouseItemFilter itemFilter() {
        return new WarehouseItemFilter();
    }

    @Bean
    public DeliveryReleaseStrategy deliveryReleaseStrategy() {
        return new DeliveryReleaseStrategy();
    }

    @Bean
    public OrderCorrelationStrategy orderCorrelationStrategy() {
        return new OrderCorrelationStrategy();
    }

    @Bean
    public MessageGroupStore messageGroupStore() {
        return new SimpleMessageStore();
    }

    @Bean
    @ServiceActivator(inputChannel = FILTERED_MESSAGE_CHANNEL)
    public MessageHandler aggregator(DeliveryReleaseStrategy deliveryReleaseStrategy,
                                     OrderCorrelationStrategy orderCorrelationStrategy,
                                     MessageGroupStore messageGroupStore) {
        AggregatingMessageHandler aggregator =
                new AggregatingMessageHandler(new DefaultAggregatingMessageGroupProcessor(), messageGroupStore);

        aggregator.setOutputChannelName(AGGREGATED_ITEMS_CHANNEL);
        aggregator.setCorrelationStrategy(orderCorrelationStrategy);
        aggregator.setReleaseStrategy(deliveryReleaseStrategy);
        return aggregator;
    }

    @Bean
    @Transformer(inputChannel = AGGREGATED_ITEMS_CHANNEL, outputChannel = DELIVERY_CHANNEL)
    public DeliveryCreator deliveryCreator() {
        return new DeliveryCreator();
    }

    @Bean
    @ServiceActivator(inputChannel = Channels.DELIVERY_CHANNEL)
    public MessageHandler deliveryLogger() {
        LoggingHandler loggingHandler = new LoggingHandler(Level.INFO);
        loggingHandler.setLogExpressionString("'Delivery is created: ' + payload");
        return loggingHandler;
    }
}
