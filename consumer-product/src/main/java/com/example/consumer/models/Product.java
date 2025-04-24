package com.example.consumer.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product implements Serializable {

    private Long id;
    private String name;
    private Double price;
    private Integer amount;
}
