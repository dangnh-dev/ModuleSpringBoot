package com.example.demo.dto;

import com.example.demo.model.ProductModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {
    private Long id;
    private String name;
    private String image;
    private Double price;
    private Integer sale;
    private Integer amount;
    private Long categoryId;
    private float avgRate;

    public ProductDTO toDTO(ProductModel productModel){
        return ProductDTO.builder()
                .id(productModel.getId())
                .name(productModel.getName())
                .image(productModel.getImage())
                .price(productModel.getPrice())
                .sale(productModel.getSale())
                .amount(productModel.getAmount())
                .categoryId(productModel.getCategoryId())
                .avgRate(productModel.getAvgRate())
                .build();
    }
}
