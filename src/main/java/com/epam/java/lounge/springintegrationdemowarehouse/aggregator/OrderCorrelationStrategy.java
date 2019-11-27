package com.epam.java.lounge.springintegrationdemowarehouse.aggregator;

import org.springframework.integration.aggregator.CorrelationStrategy;
import org.springframework.messaging.Message;

public class OrderCorrelationStrategy implements CorrelationStrategy {
    @Override
    public Object getCorrelationKey(Message<?> message) {
        return message.getHeaders().get("orderId");
    }
}
