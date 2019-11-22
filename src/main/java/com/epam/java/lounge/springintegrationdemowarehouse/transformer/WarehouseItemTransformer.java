package com.epam.java.lounge.springintegrationdemowarehouse.transformer;

import com.epam.java.lounge.springintegrationdemowarehouse.model.Item;
import com.epam.java.lounge.springintegrationdemowarehouse.model.WarehouseItem;
import org.springframework.core.convert.ConversionService;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.GenericMessage;

public class WarehouseItemTransformer {

    private final ConversionService conversionService;

    public WarehouseItemTransformer(ConversionService conversionService) {
        this.conversionService = conversionService;
    }

    public Message<WarehouseItem> transform(Message<Item> message) {
        return new GenericMessage<>(conversionService.convert(message.getPayload(), WarehouseItem.class), message.getHeaders());
    }

}
