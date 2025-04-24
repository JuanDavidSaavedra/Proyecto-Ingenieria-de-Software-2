package com.example.consumer.services;


import com.example.consumer.models.Product;
import com.example.consumer.models.dtos.ProductRequest;
import com.example.consumer.models.dtos.ShoppingCart;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
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
     * Creamos un Map para la búsqueda de producto de manera eficuente.
     * La clave será el id del producto y el valor el producto como tal,
     *
     * Obtenemos la lista de productos del usuario y lo convertimos a un strema
     * <blockquote><pre>
     * cart.getProducts().stream()
     *</blockquote>
     *
     * <p>
     *     Y utilizando el {@code .collect()} creamos una colección. Creamos un Map
     *     con {@code Collectors.toMap()} dentro de los parametros le pasamos
     *     la key y el value que van a tener.
     * </p>
     * <p>
     *     La expresión {@code Product::getId} hace referencia a una expresión lambda
     *     que sería lo mismo escribir {@code product -> product.getID()} solo que
     *     es una versión más simplificada.
     * </p>
     *<p>
     *     La expresión {@code Function.identity()} hace refenrecia a que estamos asigando
     *     cada uno de los productos del parametro {@code cart} al valor del Map.
     *     Que sería lo mismo colocar {@code product -> product}.
     *</p>
     * @param cart
     * @return
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
     * Crear una key única para usuario tanto anonimos como logeados
     * Si el usuario es anonimo, dentro del id del objecto producto
     * va a venir un id algo como "anonymous:DNLD13456461" para asigarle
     * ese id como clave
     * @param product Producto que llega desde el microservicio de Catalogo
     * @return Una llave única para los usuarios tanto anonimos como logeados.
     */
    private String getKey(String userId) {
        return "cart:" + userId;
    }

    private List<Product> getProducts(String key) {
        List<Product> products = redisTemplate.opsForValue().get(key);
        return (products == null) ? new ArrayList<>() : products;
    }
}
