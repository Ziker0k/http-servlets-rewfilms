package com.ziker0k.http.mapper;

import com.ziker0k.http.dto.FilmDto;
import com.ziker0k.http.entity.FilmEntity;

public class FilmMapper implements Mapper <FilmEntity, FilmDto> {
    private static final FilmMapper INSTANCE = new FilmMapper();

    @Override
    public FilmDto mapFrom(FilmEntity from) {
        return FilmDto.builder()
                .id(from.getId())
                .name(from.getName())
                .description(from.getDescription())
                .build();
    }

    public static FilmMapper getInstance() {
        return INSTANCE;
    }
}
