package com.ziker0k.http.validator;

import com.ziker0k.http.dto.CreateFilmDto;
import com.ziker0k.http.entity.Country;
import com.ziker0k.http.entity.Genre;
import com.ziker0k.http.service.PersonService;
import com.ziker0k.http.util.LocalDateFormatter;
import com.ziker0k.http.util.StringToLongFormatter;

import java.util.Arrays;
import java.util.List;

public class CreateFilmValidator implements Validator<CreateFilmDto> {

    private static final CreateFilmValidator INSTANCE = new CreateFilmValidator();
    private static final PersonService personService = PersonService.getInstance();

    @Override
    public ValidationResult isValid(CreateFilmDto createFilmDto) {
        var validationResult = new ValidationResult();

        /* Нужно провалидировать фильм по следующим критериям
        * 1 - дата выхода
        * 2 - Страна
        * 3 - Жанр
        * 4 - Ids актеров
        * 5 - Ids режиссеров
        */

        if(!LocalDateFormatter.isValid(createFilmDto.getReleaseDate())) {
            validationResult.addError(Error.of("invalid.releaseDate", "Release date is invalid"));
        }
        if(Country.find(createFilmDto.getCountry()).isEmpty()) {
            validationResult.addError(Error.of("invalid.country", "Country is invalid"));
        }
        if(Genre.find(createFilmDto.getGenre()).isEmpty()) {
            validationResult.addError(Error.of("invalid.genre", "Genre is invalid"));
        }

        if(createFilmDto.getActors() == null || createFilmDto.getActors().length == 0) {
            validationResult.addError(Error.of("invalid.actors", "Actors is empty"));
        } else if(!StringToLongFormatter.isValidList(List.of(createFilmDto.getActors()))) {
            validationResult.addError(Error.of("invalid.actors", "Actors is invalid"));
        } else if(!personService.isExists(Arrays.stream(createFilmDto.getActors()).map(StringToLongFormatter::format).toArray(Long[]::new))) {
            validationResult.addError(Error.of("invalid.actors", "Actors is not exists"));
        }


        if(createFilmDto.getDirectors() == null || createFilmDto.getDirectors().length == 0) {
            validationResult.addError(Error.of("invalid.directors", "Directors is empty"));
        } else if(!StringToLongFormatter.isValidList(List.of(createFilmDto.getDirectors()))) {
            validationResult.addError(Error.of("invalid.directors", "Directors is invalid"));
        } else if(!personService.isExists(Arrays.stream(createFilmDto.getDirectors()).map(StringToLongFormatter::format).toArray(Long[]::new))) {
            validationResult.addError(Error.of("invalid.directors", "Directors is not exists"));
        }

        return validationResult;
    }

    public static CreateFilmValidator getInstance() {
        return INSTANCE;
    }
}
