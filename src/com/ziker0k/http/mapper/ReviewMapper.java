package com.ziker0k.http.mapper;

import com.ziker0k.http.dto.ReviewDto;
import com.ziker0k.http.entity.ReviewEntity;

public class ReviewMapper implements Mapper<ReviewEntity, ReviewDto>{
    private static final ReviewMapper INSTANCE = new ReviewMapper();

    private final UserMapper userMapper = UserMapper.getInstance();
    @Override
    public ReviewDto mapFrom(ReviewEntity from) {
        return ReviewDto.builder()
                .id(from.getId())
                .filmId(from.getFilmId())
                .user(userMapper.mapFrom(from.getUser()))
                .description(from.getDescription())
                .rating(from.getGrade())
                .build();
    }

    public static ReviewMapper getInstance() {
        return INSTANCE;
    }
}