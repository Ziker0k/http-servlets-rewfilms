package com.ziker0k.http.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CreateFilmDto {
    String name;
    String description;
    String releaseDate;
    String country;
    String genre;
    String[] actors;
    String[] directors;
}
