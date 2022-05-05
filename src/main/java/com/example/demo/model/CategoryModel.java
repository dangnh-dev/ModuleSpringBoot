package com.example.demo.model;

import com.example.demo.dto.CategoryDTO;
import com.example.demo.entity.CategoryEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CategoryModel {
    private Long id;
    private String name;
    private Long categoryParentId;

    public CategoryModel fromEntityToModel(CategoryEntity categoryEntity){
        return CategoryModel.builder()
                .id(categoryEntity.getId())
                .name(categoryEntity.getName())
                .categoryParentId(categoryEntity.getCategoryParent() == null ? null : categoryEntity.getCategoryParent().getId())
                .build();
    }

    public CategoryModel fromDTOToModel(CategoryDTO categoryDTO){
        return CategoryModel.builder()
                .id(categoryDTO.getId())
                .name(categoryDTO.getName())
                .categoryParentId(categoryDTO.getCategoryParentId())
                .build();
    }
}
