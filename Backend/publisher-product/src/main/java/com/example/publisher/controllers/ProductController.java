package com.example.publisher.controllers;

import com.example.publisher.persistence.models.Product;
import com.example.publisher.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/publisher")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping("/create")
    public Product postProduct(@RequestBody Product product) {
        return productService.save(product);
    }

    @PostMapping("/{productId}")
    public void sendProduct(@PathVariable Long productId,
                            @RequestParam Integer amount,
                            @RequestParam String userId) {
        productService.sendProduct(productId, amount, userId);
    }

}
