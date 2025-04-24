package com.example.consumer.services;

import com.example.consumer.models.Product;
import com.example.consumer.models.dtos.ProductRequest;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductConsumer {

    private final ProductCacheService cacheService;
    private static final Logger LOGGER = LoggerFactory.getLogger(ProductConsumer.class);

    @RabbitListener(queues = "product.queue")
    public void receiveProduct(ProductRequest product) {
        LOGGER.info("Product received {}", product);
        cacheService.saveProduct(product);
    }

}
