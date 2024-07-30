package com.example.inventoryservice.service;

import com.example.inventoryservice.model.Inventory;
import com.example.inventoryservice.repository.InventoryRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class InventoryConsumerService {

    private final InventoryRepository inventoryRepository;

    @EventListener
    @KafkaListener(topics = "${spring.kafka.consumer.topic}", groupId = "${spring.kafka.consumer.group-id}")
    public void consumeTopic(String message)  throws Exception {
        JsonObject jsonObject = JsonParser.parseString(message)
                .getAsJsonObject();

        ObjectMapper mapper = new ObjectMapper();
        Map<String,Object> map = mapper.readValue(message, Map.class);

        Inventory inventory = new Inventory();
        inventory.setSkuCode(String.valueOf(map.get("skuCode")));
        inventory.setQuantity(100); //default

        inventoryRepository.save(inventory);
    }
}
