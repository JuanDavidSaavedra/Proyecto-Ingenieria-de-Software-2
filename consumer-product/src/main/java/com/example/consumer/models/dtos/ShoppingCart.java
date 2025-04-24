package com.example.consumer.models.dtos;

import com.example.consumer.models.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShoppingCart implements Serializable {

    private String userId;
    private List<Product> products;

}
