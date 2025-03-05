package com.ziker0k.http.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ReviewDto {
    Long id;
    Long filmId;
    UserDto user;
    String description;
    Integer rating;
}
