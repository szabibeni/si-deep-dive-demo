package com.epam.java.lounge.springintegrationdemowarehouse.converter;

import com.epam.common.model.Item;
import com.epam.common.model.WarehouseItem;
import org.springframework.core.convert.converter.Converter;

public class ItemToWarehouseItemConverter implements Converter<Item, WarehouseItem> {

    @Override
    public WarehouseItem convert(Item item) {
        return WarehouseItem.builder()
            .id(item.getId())
            .cost(item.getPrice())
            .itemName(item.getName())
            .build();
    }
}
