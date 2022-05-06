package com.example.demo.service.impl;

import com.example.demo.entity.CategoryEntity;
import com.example.demo.model.CategoryModel;
import com.example.demo.repository.ICategoryRepository;
import com.example.demo.service.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements ICategoryService {
    @Autowired
    ICategoryRepository iCategoryRepository;

    @Override
    public List<CategoryModel> getAll() {
        List<CategoryEntity> categoryEntityList = iCategoryRepository.findAll();
        List<CategoryModel> categoryModelList = new ArrayList<>();
        categoryEntityList.forEach(c -> {
            CategoryModel categoryModel = new CategoryModel().fromEntityToModel(c);
            categoryModelList.add(categoryModel);
        });
        return categoryModelList;
    }

    @Override
    public CategoryModel findById(Long id) {
        CategoryEntity categoryEntity = iCategoryRepository.getById(id);
        return new CategoryModel().fromEntityToModel(categoryEntity);
    }

    @Override
    public boolean save(CategoryModel categoryModel) {
        try {
            CategoryEntity categoryEntity = new CategoryEntity();
            Optional<CategoryEntity> categoryParent = iCategoryRepository.findById(categoryModel.getCategoryParentId());
            if (categoryParent.isPresent())
                categoryEntity.setCategoryParent(categoryParent.get());
            else
                categoryEntity.setCategoryParent(null);

            categoryEntity.setName(categoryModel.getName());
            iCategoryRepository.save(categoryEntity);
            return true;
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean edit(CategoryModel categoryModel) {
        try {
            CategoryEntity categoryEntity = new CategoryEntity();
            Optional<CategoryEntity> categoryParent = iCategoryRepository.findById(categoryModel.getCategoryParentId());
            if (categoryParent.isPresent())
                categoryEntity.setCategoryParent(categoryParent.get());
            else
                categoryEntity.setCategoryParent(null);

            categoryEntity.setId(categoryModel.getId());
            categoryEntity.setName(categoryModel.getName());
            iCategoryRepository.save(categoryEntity);
            return true;
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(Long id) {
        try {
            iCategoryRepository.deleteById(id);
            return true;
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
}
