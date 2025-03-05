package com.ziker0k.http.validator;

import com.ziker0k.http.dto.CreateReviewDto;
import com.ziker0k.http.service.FilmService;

public class CreateReviewValidator implements Validator<CreateReviewDto> {

    private static final CreateReviewValidator INSTANCE = new CreateReviewValidator();

    private final FilmService filmService = FilmService.getInstance();
    @Override
    public ValidationResult isValid(CreateReviewDto createReviewDto) {
        var validationResult = new ValidationResult();
//        провалидировать фильм
        if (!isLong(createReviewDto.getFilmId()) || filmService.findById(Long.parseLong(createReviewDto.getFilmId())) == null) {
            validationResult.addError(Error.of("invalid.film", "Film not found"));
        }
//        провалидировать пользователя
//        провалидировать введенный рейтинг
        if (!isInteger(createReviewDto.getRating()) || !isInRange(createReviewDto.getRating())) {
            validationResult.addError(Error.of("invalid.rating", "Rating must be between 0 and 10"));
        }
        return validationResult;
    }

    public static CreateReviewValidator getInstance() {
        return INSTANCE;
    }

    private boolean isInteger (String value) {
        try {
            Integer.parseInt(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private boolean isLong (String value) {
        try {
            Long.parseLong(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private boolean isInRange(String value) {
        int val = Integer.parseInt(value);
        return val >= 0 && val <= 10;
    }
}
