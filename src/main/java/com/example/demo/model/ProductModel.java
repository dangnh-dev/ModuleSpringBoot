package com.example.demo.model;

import com.example.demo.dto.ProductDTO;
import com.example.demo.entity.ProductEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductModel {
    private Long id;
    private String name;
    private String image;
    private Double price;
    private Integer sale;
    private Integer amount;
    private Long categoryId;
    private float avgRate;

    public ProductModel fromEntityToModel(ProductEntity productEntity){
        return ProductModel.builder()
                .id(productEntity.getId())
                .name(productEntity.getName())
                .image(productEntity.getImage())
                .price(productEntity.getPrice())
                .sale(productEntity.getSale())
                .amount(productEntity.getAmount())
                .categoryId(productEntity.getCategory().getId())
                .avgRate(productEntity.getAvgRate())
                .build();
    }

    public ProductModel fromDTOToModel(ProductDTO productDTO){
        return ProductModel.builder()
                .id(productDTO.getId())
                .name(productDTO.getName())
                .image(productDTO.getImage())
                .price(productDTO.getPrice())
                .sale(productDTO.getSale())
                .amount(productDTO.getAmount())
                .categoryId(productDTO.getCategoryId())
                .avgRate(productDTO.getAvgRate())
                .build();
    }
}
