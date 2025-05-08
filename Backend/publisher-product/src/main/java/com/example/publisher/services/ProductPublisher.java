package com.example.publisher.services;

import com.example.publisher.persistence.models.DTOs.ProductResponse;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductPublisher {

    private static final String EXCHANGE_NAME = "product.exchange";
    private static final String ROUTING_KEY = "product.key";

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void sendProduct(ProductResponse productDTO) {
        System.out.println("Sending product " + productDTO.getName() + " to exchange " + EXCHANGE_NAME);
        rabbitTemplate.convertAndSend(EXCHANGE_NAME, ROUTING_KEY, productDTO);
    }

}
