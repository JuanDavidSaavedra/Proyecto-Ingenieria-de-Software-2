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

    /**
     * Endpoint para modificar la cantidad de un único producto en el carrito.
     * Utiliza PATCH ya que es una actualización parcial del carrito.
     *
     * @param userId      Id del usuario (parámetro de consulta).
     * @param productId   Id del producto a modificar (parámetro de consulta).
     * @param quantity Nueva cantidad para el producto (parámetro de consulta).
     * @return ResponseEntity con la lista actualizada del carrito (200 OK),
     * o un estado de error (404 Not Found, 400 Bad Request, 500 Internal Server Error).
     */
    @PatchMapping("/product/quantity")
    public ResponseEntity<?> updateSingleProductQuantity(@RequestParam String userId,
                                                         @RequestParam Long productId,
                                                         @RequestParam Integer quantity) {
        try{
            List<Product> updatedProducts = cacheService.updateProductQuantity(userId, productId, quantity);
            return ResponseEntity.ok(updatedProducts);
        } catch (NoSuchElementException e) {
            // Ocurre si el carrito está vacío o el producto específico no se encuentra
            LOGGER.warn("PATCH /product/quantity - User: {}, Product: {}, Quantity: {} - Not Found: {}",
                    userId, productId, quantity, e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (IllegalArgumentException e) {
            // Ocurre si newQuantity es negativa o userId es inválido
            LOGGER.warn("PATCH /product/quantity - User: {}, Product: {}, Quantity: {} - Bad Request: {}",
                    userId, productId, quantity, e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            // Otros errores inesperados
            LOGGER.error("PATCH /product/quantity - User: {}, Product: {}, Quantity: {} - Internal Error: {}",
                    userId, productId, quantity, e.getMessage(), e); // Loguea el stack trace
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An internal server error occurred.");
        }
    }
}
