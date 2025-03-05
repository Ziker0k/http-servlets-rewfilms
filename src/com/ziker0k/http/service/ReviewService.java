package com.ziker0k.http.service;

import com.ziker0k.http.dao.ReviewDao;
import com.ziker0k.http.dto.CreateReviewDto;
import com.ziker0k.http.dto.ReviewDto;
import com.ziker0k.http.exception.ValidationException;
import com.ziker0k.http.mapper.CreateReviewMapper;
import com.ziker0k.http.mapper.ReviewMapper;
import com.ziker0k.http.validator.CreateReviewValidator;
import lombok.NoArgsConstructor;

import java.util.Comparator;
import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class ReviewService {
    private static final ReviewService INSTANCE = new ReviewService();

    private final ReviewDao reviewDao = ReviewDao.getInstance();
    private final CreateReviewMapper createReviewMapper = CreateReviewMapper.getInstance();
    private final ReviewMapper reviewMapper = ReviewMapper.getInstance();
    private final CreateReviewValidator createReviewValidator = CreateReviewValidator.getInstance();

    public Long create(CreateReviewDto createReviewDto) {
//        validation
        var validationResult = createReviewValidator.isValid(createReviewDto);
        if (!validationResult.isValid()) {
            throw new ValidationException(validationResult.getErrors());
        }
//        map
        var reviewEntity = createReviewMapper.mapFrom(createReviewDto);
//        reviewDao.save(reviewEntity)
        reviewDao.save(reviewEntity);
//        return id
        return reviewEntity.getId();
    }

    public List<ReviewDto> findAllByFilmId(Long filmId) {
        return reviewDao.findByFilmId(filmId).stream()
                .map(reviewMapper::mapFrom)
                .sorted(Comparator.comparingLong(ReviewDto::getId).reversed())
                .toList();
    }

    public List<ReviewDto> findAllByUserId(Long userId) {
        return reviewDao.findByUserId(userId).stream()
                .map(reviewEntity -> ReviewDto.builder()
                        .id(reviewEntity.getId())
                        .filmId(reviewEntity.getFilmId())
                        .description(reviewEntity.getDescription())
                        .rating(reviewEntity.getGrade())
                        .build())
                .toList();
    }

    public static ReviewService getInstance() {
        return INSTANCE;
    }
}
