package com.epam.java.lounge.springintegrationdemowarehouse.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.handler.LoggingHandler;
import org.springframework.integration.handler.LoggingHandler.Level;
import org.springframework.integration.http.dsl.Http;
import org.springframework.integration.http.dsl.HttpRequestHandlerEndpointSpec;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;

@Configuration
public class HandlerConfiguration {

    @Bean
    public HttpRequestHandlerEndpointSpec incomingMessageHandler(MessageChannel incomingMessageChannel) {
        HttpRequestHandlerEndpointSpec spec = Http.inboundGateway("/");

        spec.get().setRequestChannel(incomingMessageChannel);
        return spec;
    }

    @Bean
    @ServiceActivator(inputChannel = Channels.INCOMING_MESSAGE_CHANNEL)
    public MessageHandler incomingMessageLogger() {
        LoggingHandler loggingHandler = new LoggingHandler(Level.INFO);
        loggingHandler.setLogExpressionString("'Input event: ' + payload");
        return loggingHandler;
    }
}
