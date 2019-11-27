package com.epam.java.lounge.springintegrationdemowarehouse.configuration;

import com.epam.java.lounge.springintegrationdemowarehouse.interceptor.LoggerInterceptor;
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
    public MessageChannel transformedMessageChannel() {
        return new PublishSubscribeChannel();
    }

    @Bean
    public MessageChannel enrichedMessageChannel() {
        return new PublishSubscribeChannel();
    }

    @Bean
    public MessageChannel enrichRequestMessageChannel() {
        return new PublishSubscribeChannel();
    }

    @Bean
    public MessageChannel enrichErrorMessageChannel() {
        return new PublishSubscribeChannel();
    }

    @Bean
    public MessageChannel enrichReplyMessageChannel() {
        return new PublishSubscribeChannel();
    }

    @Bean
    public MessageChannel filteredMessageChannel(LoggerInterceptor loggerInterceptor) {
        PublishSubscribeChannel channel = new PublishSubscribeChannel();
        channel.addInterceptor(loggerInterceptor);
        return channel;
    }

    @Bean
    public MessageChannel aggregatedItemsChannel() {
        return new PublishSubscribeChannel();
    }

    @Bean
    public MessageChannel deliveryChannel() {
        return new PublishSubscribeChannel();
    }
}
