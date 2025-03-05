package com.ziker0k.http.mapper;

import com.ziker0k.http.dto.CreateReviewDto;
import com.ziker0k.http.entity.ReviewEntity;

public class CreateReviewMapper implements Mapper<CreateReviewDto, ReviewEntity> {
    private static final CreateReviewMapper INSTANCE = new CreateReviewMapper();
    private final UserMapper userMapper = UserMapper.getInstance();

    @Override
    public ReviewEntity mapFrom(CreateReviewDto from) {
        return ReviewEntity.builder()
                .filmId(Long.parseLong(from.getFilmId()))
                .user(userMapper.mapTo(from.getUser()))
                .description(from.getDescription())
                .grade(Integer.parseInt(from.getRating()))
                .build();
    }

    public static CreateReviewMapper getInstance() {
        return INSTANCE;
    }
}
