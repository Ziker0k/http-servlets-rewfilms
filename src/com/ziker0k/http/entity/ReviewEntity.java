package com.ziker0k.http.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReviewEntity {
    private Long id;
    private Long filmId;
    private UserEntity user;
    private String description;
    private Integer grade;
}
