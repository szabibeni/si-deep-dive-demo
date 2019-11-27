package com.epam.java.lounge.springintegrationdemowarehouse.aggregator;

import org.springframework.integration.aggregator.ReleaseStrategy;
import org.springframework.integration.store.MessageGroup;

public class DeliveryReleaseStrategy implements ReleaseStrategy {
    @Override
    public boolean canRelease(MessageGroup group) {
        return group.size() == Integer.valueOf(String.valueOf(group.getOne().getHeaders().get("orderSize")));
    }
}
