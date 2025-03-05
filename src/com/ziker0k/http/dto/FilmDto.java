package com.ziker0k.http.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class FilmDto {
    Long id;
    String name;
    String description;
}