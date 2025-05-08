package com.example.consumer.services;


import com.example.consumer.models.Product;
import com.example.consumer.models.dtos.ProductRequest;
import com.example.consumer.models.dtos.ShoppingCart;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductCacheService {

    private final RedisTemplate<String, List<Product>> redisTemplate;
    private static final Logger LOGGER = LoggerFactory.getLogger(ProductCacheService.class);

    public void saveProduct(ProductRequest product) {
        String key = getKey(product.getUserId());
        List<Product> products = getProducts(key);

        Product p = Product.builder()
                .price(product.getPrice())
                .name(product.getName())
                .amount(product.getAmount())
                .id(product.getId())
                .build();

        products.add(p);
        redisTemplate.opsForValue().set(key, products);
        LOGGER.info("Product saved to redis: {}", product);
    }

    public List<Product> getAllProducts(String userId) {
        String key = getKey(userId);
        List<Product> products = getProducts(key);
        if(products.isEmpty()) {
            throw new NoSuchElementException("Shopping cart is empty for user: " + userId);
        }
        LOGGER.info("Shopping cart found for user: {}", userId);
        return products;
    }

    public void deleteProductById(String userId, Long productId) {
        String key = getKey(userId);
        List<Product> products = getProducts(key);

        products.removeIf(product -> product.getId().equals(productId));
        redisTemplate.opsForValue().set(key, products);
    }

    public void emptyCart(String userId) {
        redisTemplate.delete(getKey(userId));
    }

    /**
     * <h1>Actualizar productos del carrito</h1>
     * Este método actualiza los productos de un usuario de una forma
     * más optimizada de la planteada al comienzo.
     *
     * <h2>Creación del Map<K, V></h2>
     * Creamos un Map para la búsqueda de producto de manera eficiente.
     * La clave será Id del producto y el valor el producto como tal,
     * Obtenemos la lista de productos del usuario y lo convertimos a un strema
     * <blockquote><pre>
     * cart.getProducts().stream()
     *</blockquote>
     *
     * <p>
     *     Y utilizando el {@code .collect()} creamos una colección. Creamos un Map
     *     con {@code Collectors.toMap()} dentro de los parámetros le pasamos
     *     la key y el value que van a tener.
     * </p>
     * <p>
     *     La expresión {@code Product::getId} hace referencia a una expresión lambda
     *     que sería lo mismo escribir {@code product -> product.getID()} solo que
     *     es una versión más simplificada.
     * </p>
     *<p>
     *     La expresión {@code Function.identity()} hace referencia a que estamos asignando
     *     cada uno de los productos del parámetro {@code cart} al valor del Map.
     *     Que sería lo mismo colocar {@code product -> product}.
     *</p>
     * @param cart carrito a actualizar.
     * @return la lista de productos actualizada del carrito.
     */
    public List<Product> updateAmount(ShoppingCart cart) {
        String key = getKey(cart.getUserId());
        List<Product> products = getProducts(key);

        if(products.isEmpty()) {
            throw new NoSuchElementException("Shopping cart is empty");
        }

        Map<Long, Product> updatedProducts = cart.getProducts().stream()
                        .collect(Collectors.toMap(Product::getId, Function.identity()));

        for(Product p: products) {
            Product oldProduct = updatedProducts.get(p.getId());
            if(oldProduct != null && !oldProduct.getAmount().equals(p.getAmount())) {
                p.setAmount(oldProduct.getAmount());
            }
        }

        redisTemplate.opsForValue().set(key, products);
        return products;
    }

    /**
     * <h1>Modificar la cantidad de un producto específico en el carrito</h1>
     * Busca un producto por su ID en el carrito del usuario y actualiza su cantidad a la especificada.
     *
     * @param userId      Identificador del usuario.
     * @param productId   Id del producto cuya cantidad se modificará.
     * @param quantity La nueva cantidad para el producto (debe ser >= 0).
     * @return La lista actualizada de productos en el carrito.
     */
    public List<Product> updateProductQuantity(String userId, Long productId, Integer quantity) {
        // Validar la cantidad
        if(quantity < 0) {
            throw new IllegalArgumentException("Quantity cannot be negative");
        } else if (quantity == 0) {
            deleteProductById(userId, productId);
            return getAllProducts(userId);
        }

        String key = getKey(userId);
        List<Product> products = getProducts(key);
        if(products.isEmpty()) {
            throw new NoSuchElementException("Shopping cart is empty for user: " + userId);
        }

        // Buscar producto específico en la lista
        Optional<Product> productToUpdate = products.stream()
                .filter(p -> p.getId().equals(productId))
                .findFirst();
        if(productToUpdate.isPresent()) {
            Product product = productToUpdate.get();
            if(!product.getAmount().equals(quantity)) {
                product.setAmount(quantity);
                LOGGER.info("Updated quantity for product {} to {} for user {}", productId, quantity, userId);
            } else {
                LOGGER.info("Product {} already has quantity {}. No update needed for user {}.", productId, quantity, userId);
            }
        } else{ // Si el producto no se encontró en el carrito
            throw new NoSuchElementException("Product with ID " + productId + " not found in cart for user: " + userId);
        }
        return products;
    }

    /**
     * Crear una key única para usuario tanto anónimo como logueado
     * Si el usuario es anónimo, dentro del Id del objeto producto
     * va a venir Id algo como "anonymous:DNLD13456461" para asignarle
     * ese ID como clave
     * @param userId identificador del usuario. Producto que llega desde el microservicio de Catalogo
     * @return Una llave única para los usuarios tanto anónimos como logueados.
     */
    private String getKey(String userId) {
        return "cart:" + userId;
    }

    /**
     * Obtiene la lista de productos para una clave dada desde Redis.
     * Devuelve una lista vacía (nueva instancia) si no se encuentran productos
     * o si la clave no existe, evitando NullPointerException.
     * @param key La clave de Redis (e.g., "cart:userId123").
     * @return Una Lista de Productos (nunca null).
     */
    private List<Product> getProducts(String key) {
        List<Product> products = redisTemplate.opsForValue().get(key);
        return (products == null) ? new ArrayList<>() : products;
    }
}
