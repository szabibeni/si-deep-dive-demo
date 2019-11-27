package com.epam.common.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class WarehouseItem {

    private long id;
    private String itemName;
    private double cost;
    private String serialNumber;
    private long orderId;
}
