package com.epam.java.lounge.springintegrationdemowarehouse.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.channel.PublishSubscribeChannel;
import org.springframework.messaging.MessageChannel;

@Configuration
public class ChannelConfiguration {

    @Bean
    public MessageChannel incomingMessageChannel() {
        return new PublishSubscribeChannel();
    }

    @Bean
    public MessageChannel loggingMessageChannel() {
        return new PublishSubscribeChannel();
    }
}
