package com.example.demo.service;

import com.example.demo.model.RatingModel;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IRateService{
    List<RatingModel> getAll();
    List<RatingModel> findAllByProductId(Long id);
    RatingModel findById(Long id);
    boolean save(RatingModel ratingModel);
}
