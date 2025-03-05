package com.ziker0k.http.mapper;

import com.ziker0k.http.dto.CreateFilmDto;
import com.ziker0k.http.entity.Country;
import com.ziker0k.http.entity.FilmEntity;
import com.ziker0k.http.entity.Genre;
import com.ziker0k.http.util.LocalDateFormatter;
import com.ziker0k.http.util.StringToLongFormatter;

import java.util.Arrays;

public class CreateFilmMapper implements Mapper<CreateFilmDto, FilmEntity> {
    private static final CreateFilmMapper INSTANCE = new CreateFilmMapper();

    @Override
    public FilmEntity mapFrom(CreateFilmDto from) {
        return FilmEntity.builder()
                .name(from.getName())
                .description(from.getDescription())
                .releaseDate(LocalDateFormatter.format(from.getReleaseDate()))
                .country(Country.valueOf(from.getCountry()))
                .genre(Genre.valueOf(from.getGenre()))
                .actors(Arrays.stream(from.getActors()).map(StringToLongFormatter::format).toList())
                .directors(Arrays.stream(from.getDirectors()).map(StringToLongFormatter::format).toList())
                .build();
    }

    public static CreateFilmMapper getInstance() {
        return INSTANCE;
    }
}
