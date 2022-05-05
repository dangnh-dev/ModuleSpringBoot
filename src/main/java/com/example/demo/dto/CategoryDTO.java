package com.example.demo.dto;

import com.example.demo.entity.CategoryEntity;
import com.example.demo.model.CategoryModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDTO {
    private Long id;
    private String name;
    private Long categoryParentId;

    public CategoryDTO toDTO(CategoryModel categoryModel){
        return CategoryDTO.builder()
                .id(categoryModel.getId())
                .name(categoryModel.getName())
                .categoryParentId(categoryModel.getCategoryParentId())
                .build();
    }
}
