package com.example.demo.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CartProduct {
    private int quantity;
    private ProductDTO product;
}
