package com.epam.java.lounge.springintegrationdemowarehouse.filter;

import com.epam.java.lounge.springintegrationdemowarehouse.configuration.Channels;
import com.epam.common.model.WarehouseItem;
import org.springframework.integration.annotation.Filter;
import org.springframework.util.StringUtils;

public class WarehouseItemFilter {

    @Filter(inputChannel = Channels.ENRICHED_MESSAGE_CHANNEL, outputChannel = Channels.FILTERED_MESSAGE_CHANNEL)
    public boolean validItemsOnly(WarehouseItem item) {
        return !StringUtils.isEmpty(item.getSerialNumber());
    }
}
