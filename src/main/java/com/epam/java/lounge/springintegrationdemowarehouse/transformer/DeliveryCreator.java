package com.epam.java.lounge.springintegrationdemowarehouse.transformer;

import com.epam.common.model.Delivery;
import com.epam.common.model.WarehouseItem;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.GenericMessage;

import java.util.List;

public class DeliveryCreator {

    public Message<Delivery> transform(Message<List<WarehouseItem>> message) {
        Delivery delivery = new Delivery();
        delivery.setId(Long.valueOf(String.valueOf(message.getHeaders().get("orderId"))));
        delivery.setItems(message.getPayload());
        return new GenericMessage<>(delivery, message.getHeaders());
    }
}
