package com.epam.java.lounge.springintegrationdemowarehouse.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.ChannelInterceptor;

public class LoggerInterceptor implements ChannelInterceptor {

    private static Logger LOGGER = LoggerFactory.getLogger(LoggerInterceptor.class);

    @Override
    public void postSend(Message<?> message, MessageChannel channel, boolean sent) {
        LOGGER.info("The sending of message {} was {} to channel: {}", message, sent ? "successful" : "not successful", channel);
    }
}
