package com.example.demo.service.impl;

import com.example.demo.entity.ProductEntity;
import com.example.demo.entity.RatingEntity;
import com.example.demo.model.RatingModel;
import com.example.demo.repository.IProductRepository;
import com.example.demo.repository.IRateRepository;
import com.example.demo.service.IRateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RateServiceImpl implements IRateService {
    @Autowired
    IRateRepository iRateRepository;
    @Autowired
    IProductRepository iProductRepository;

    @Override
    public List<RatingModel> getAll() {
        List<RatingEntity> ratingEntityList = iRateRepository.findAll();
        List<RatingModel> ratingModelList = new ArrayList<>();
        ratingEntityList.forEach(r -> {
            RatingModel ratingModel = new RatingModel().fromEntityToModel(r);
            ratingModelList.add(ratingModel);
        });
        return ratingModelList;
    }

    @Override
    public RatingModel findById(Long id) {
        RatingEntity ratingEntity = iRateRepository.getById(id);
        return new RatingModel().fromEntityToModel(ratingEntity);
    }

    @Override
    public List<RatingModel> findAllByProductId(Long id) {
        List<RatingEntity> ratingEntityList = iRateRepository.findAllByProductEntityId(id);
        List<RatingModel> ratingModelList = new ArrayList<>();
        ratingEntityList.forEach(r -> {
            RatingModel ratingModel = new RatingModel().fromEntityToModel(r);
            ratingModelList.add(ratingModel);
        });
        return ratingModelList;
    }

    @Override
    public boolean save(RatingModel ratingModel) {
        try{
            RatingEntity ratingEntity = new RatingEntity();
            ProductEntity productEntity = iProductRepository.getById(ratingModel.getProductEntityId());
            ratingEntity.setDate(ratingModel.getDate());
            ratingEntity.setStar(ratingModel.getStar());
            ratingEntity.setComment(ratingModel.getComment());
            ratingEntity.setProductEntity(productEntity);
            iRateRepository.save(ratingEntity);
            return true;
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean edit(RatingModel ratingModel) {
        return false;
    }

    @Override
    public boolean delete(Long id) {
        return false;
    }
}
