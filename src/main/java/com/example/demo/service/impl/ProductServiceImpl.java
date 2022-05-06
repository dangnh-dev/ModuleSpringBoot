package com.example.demo.service.impl;

import com.example.demo.entity.ProductEntity;
import com.example.demo.model.ProductModel;
import com.example.demo.repository.ICategoryRepository;
import com.example.demo.repository.IProductRepository;
import com.example.demo.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements IProductService {
    @Autowired
    IProductRepository iProductRepository;
    @Autowired
    ICategoryRepository iCategoryRepository;

    @Override
    public Page<ProductModel> getAll(Pageable pageable) {
        Page<ProductEntity> productEntityPage = iProductRepository.findAll(pageable);
        return productEntityPage.map(productEntity -> {
            return new ProductModel().fromEntityToModel(productEntity);
        });
    }

    @Override
    public List<ProductModel> findAll() {
        List<ProductEntity> productEntityList = iProductRepository.findAll();
        List<ProductModel> productModelList = new ArrayList<>();
        productEntityList.forEach(p -> {
            ProductModel productModel = new ProductModel().fromEntityToModel(p);
            productModelList.add(productModel);
        });
        return productModelList;
    }

    @Override
    public ProductModel findById(Long id) {
        ProductEntity productEntity = iProductRepository.getById(id);
        return new ProductModel().fromEntityToModel(productEntity);
    }

    @Override
    public Page<ProductModel> findByCategoryId(Long id, Pageable pageable) {
        Page<ProductEntity> productEntityPage = iProductRepository.findAllByCategoryId(id, pageable);
        return productEntityPage.map(productEntity -> {
            return new ProductModel().fromEntityToModel(productEntity);
        });
    }

    @Override
    public boolean save(ProductModel productModel) {
        try {
            ProductEntity productEntity = new ProductEntity();
            productEntity.setName(productModel.getName());
            productEntity.setImage(productModel.getImage());
            productEntity.setPrice(productModel.getPrice());
            productEntity.setSale(productModel.getSale());
            productEntity.setAmount(productModel.getAmount());
            productEntity.setCategory(iCategoryRepository.getById(productModel.getCategoryId()));
            iProductRepository.save(productEntity);
            return true;
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean edit(ProductModel productModel) {
        try {
            ProductEntity productEntity = new ProductEntity();
            productEntity.setId(productModel.getId());
            productEntity.setName(productModel.getName());
            productEntity.setImage(productModel.getImage());
            productEntity.setPrice(productModel.getPrice());
            productEntity.setSale(productModel.getSale());
            productEntity.setAmount(productModel.getAmount());
            productEntity.setCategory(iCategoryRepository.getById(productModel.getCategoryId()));
            productEntity.setAvgRate(productModel.getAvgRate());
            iProductRepository.save(productEntity);
            return true;
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(Long id) {
        try {
            iProductRepository.deleteById(id);
            return true;
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
}
