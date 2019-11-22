package com.epam.java.lounge.springintegrationdemowarehouse.transformer;

import com.epam.java.lounge.springintegrationdemowarehouse.model.WarehouseItem;
import com.epam.java.lounge.springintegrationdemowarehouse.service.SerialNumberService;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.GenericMessage;

public class SerialNumberTransformer {

    private SerialNumberService serialNumberService;

    public SerialNumberTransformer(SerialNumberService serialNumberService) {
        this.serialNumberService = serialNumberService;
    }

    public Message<WarehouseItem> transform(Message<WarehouseItem> message) {
        WarehouseItem item = message.getPayload();
        //todo put order id in message headers
        //item.setOrderId(Long.valueOf(String.valueOf(message.getHeaders().get("orderId"))));
        item.setSerialNumber(serialNumberService.getSerialNumber());
        return new GenericMessage<>(item, message.getHeaders());
    }
}
