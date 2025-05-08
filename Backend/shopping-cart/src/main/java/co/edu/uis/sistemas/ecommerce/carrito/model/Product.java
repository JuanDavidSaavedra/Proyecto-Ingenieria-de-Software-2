package co.edu.uis.sistemas.ecommerce.carrito.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private Long id;
    private String name;
    private Double price;
    private Integer amount;
}
