package com.example.demo.service;

import com.example.demo.model.CategoryModel;

import java.util.List;

public interface ICategoryService extends BaseService<CategoryModel>{
    List<CategoryModel> getAllCategory();
}
