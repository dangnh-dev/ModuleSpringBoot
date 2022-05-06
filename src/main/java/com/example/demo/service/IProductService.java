package com.example.demo.service;

import com.example.demo.model.ProductModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IProductService extends BaseService<ProductModel>{
    List<ProductModel> findAll();
    Page<ProductModel> findByCategoryId(Long id, Pageable pageable);
}
