package com.example.demo.service;

import com.example.demo.model.RatingModel;

import java.util.List;

public interface IRateService extends BaseService<RatingModel>{
    List<RatingModel> findAllByProductId(Long id);
}
