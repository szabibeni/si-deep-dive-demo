package com.epam.java.lounge.springintegrationdemowarehouse.service;

import java.util.UUID;
import org.springframework.stereotype.Component;

@Component
public class SerialNumberService {

    public String getSerialNumber() {
        return UUID.randomUUID().toString();
    }
}
