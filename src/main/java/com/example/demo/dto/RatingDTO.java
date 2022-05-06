package com.example.demo.dto;

import com.example.demo.model.RatingModel;
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
public class RatingDTO {
    private Long id;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime date;
    private float star;
    private String comment;
    private Long productEntityId;

    public RatingDTO toDTO(RatingModel ratingModel){
        return RatingDTO.builder()
                .id(ratingModel.getId())
                .date(ratingModel.getDate())
                .star(ratingModel.getStar())
                .comment(ratingModel.getComment())
                .productEntityId(ratingModel.getProductEntityId())
                .build();
    }
}
