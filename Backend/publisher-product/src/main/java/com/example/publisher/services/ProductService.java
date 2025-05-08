package com.example.publisher.services;

import com.example.publisher.persistence.models.DTOs.ProductResponse;
import com.example.publisher.persistence.models.Product;
import com.example.publisher.persistence.respotitories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductPublisher productPublisher;

    public Product save(Product product) {
        return productRepository.save(product);
    }

    public void sendProduct(Long id, Integer amount, String userId) {
        Product product = productRepository.getById(id);
        System.out.println(product);
        ProductResponse productDTO = ProductResponse.builder()
                .id(product.getId())
                .userId(userId)
                .name(product.getName())
                .price(product.getPrice())
                .amount(amount)
                .build();
        productPublisher.sendProduct(productDTO);
    }

}
