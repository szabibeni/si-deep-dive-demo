package com.epam.java.lounge.springintegrationdemowarehouse.model;

import java.util.List;
import lombok.Data;

@Data
public class Order {

    private long id;
    private List<WarehouseItem> items;
}
