package com.example.demo.model;

import com.example.demo.dto.RatingDTO;
import com.example.demo.entity.RatingEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RatingModel {
    private Long id;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime date;
    private float star;
    private String comment;
    private Long productEntityId;

    public RatingModel fromEntityToModel(RatingEntity ratingEntity){
        return RatingModel.builder()
                .id(ratingEntity.getId())
                .date(ratingEntity.getDate())
                .star(ratingEntity.getStar())
                .comment(ratingEntity.getComment())
                .productEntityId(ratingEntity.getProductEntity().getId())
                .build();
    }

    public RatingModel fromDTOToModel(RatingDTO ratingDTO){
        return RatingModel.builder()
                .id(ratingDTO.getId())
                .date(ratingDTO.getDate())
                .star(ratingDTO.getStar())
                .comment(ratingDTO.getComment())
                .productEntityId(ratingDTO.getProductEntityId())
                .build();
    }
}
