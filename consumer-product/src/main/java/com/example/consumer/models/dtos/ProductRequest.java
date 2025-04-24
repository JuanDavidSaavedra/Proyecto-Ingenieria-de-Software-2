package com.example.consumer.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequest implements Serializable {

    private Long id;
    private String userId;
    private String name;
    private Double price;
    private Integer amount;
}
