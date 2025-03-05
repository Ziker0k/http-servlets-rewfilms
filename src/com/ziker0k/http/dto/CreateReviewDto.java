package com.ziker0k.http.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CreateReviewDto {
    String filmId;
    UserDto user;
    String description;
    String rating;
}
