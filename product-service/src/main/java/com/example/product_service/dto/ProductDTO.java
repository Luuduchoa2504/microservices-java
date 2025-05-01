package com.example.product_service.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ProductDTO {
    private Long id;

//    @NotBlank(message = "Name is mandatory")
    private String name;

//    @Min(value = 0, message = "Price must be non-negative")
    private double price;

//    @Min(value = 0, message = "Quantity must be non-negative")
    private int quantity;
}
