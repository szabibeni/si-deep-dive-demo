package com.epam.common.model;

import java.util.List;
import lombok.Data;

@Data
public class Delivery {

    private long id;
    private List<WarehouseItem> items;
}
