package com.example.consumer.controllers;

import com.example.consumer.models.Product;
import com.example.consumer.models.dtos.ShoppingCart;
import com.example.consumer.services.ProductCacheService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/shopping_cart")
@RequiredArgsConstructor
public class ProductCacheController {

    private final ProductCacheService cacheService;
    private static final Logger LOGGER = LoggerFactory.getLogger(ProductCacheController.class);

    @GetMapping
    public ResponseEntity<?> getAllProducts(@RequestParam String userId) {
        try {
            return ResponseEntity.ok(cacheService.getAllProducts(userId));
        } catch (NoSuchElementException e) {
            LOGGER.warn(e.getMessage());
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(e.getMessage());
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @DeleteMapping("/empty")
    public ResponseEntity<Void> emptyShoppingCart(@RequestParam String userId) {
        cacheService.emptyCart(userId);
        LOGGER.info("Shopping cart was empty for user {}", userId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteProduct(@RequestParam String userId,
                                              @RequestParam Long productId) {
        cacheService.deleteProductById(userId, productId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping
    public ResponseEntity<?> updateProducts(@RequestBody ShoppingCart shoppingCart) {
        try {
            return ResponseEntity.ok(cacheService.updateAmount(shoppingCart));
        } catch (NoSuchElementException e) {
            LOGGER.warn(e.getMessage());
            return ResponseEntity.badRequest().body("User does not exist");
        }
    }


}
