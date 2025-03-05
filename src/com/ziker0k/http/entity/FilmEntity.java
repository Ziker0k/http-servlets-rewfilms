package com.ziker0k.http.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FilmEntity {
    private Long id;
    private String name;
    private String description;
    private LocalDate releaseDate;
    private Country country;
    private Genre genre;
    private List<Long> actors;
    private List<Long> directors;
}
