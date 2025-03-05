package com.ziker0k.http.service;

import com.ziker0k.http.dao.FilmDao;
import com.ziker0k.http.dto.CreateFilmDto;
import com.ziker0k.http.dto.FilmDto;
import com.ziker0k.http.exception.ValidationException;
import com.ziker0k.http.mapper.CreateFilmMapper;
import com.ziker0k.http.mapper.FilmMapper;
import com.ziker0k.http.util.LocalDateFormatter;
import com.ziker0k.http.validator.CreateFilmValidator;
import com.ziker0k.http.validator.Error;
import lombok.SneakyThrows;

import java.util.List;

import static java.util.stream.Collectors.toList;

public class FilmService {
    private static final FilmService INSTANCE = new FilmService();

    private final CreateFilmValidator createFilmValidator = CreateFilmValidator.getInstance();
    private final CreateFilmMapper createFilmMapper = CreateFilmMapper.getInstance();
    private final FilmMapper filmMapper = FilmMapper.getInstance();
    private final FilmDao filmDao = FilmDao.getInstance();

    private FilmService() {
    }

    @SneakyThrows
    public Long create(CreateFilmDto filmDto) {
//        validation
        var validationResult = createFilmValidator.isValid(filmDto);
        if (!validationResult.isValid()) {
            throw new ValidationException(validationResult.getErrors());
        }
//        map
        var filmEntity = createFilmMapper.mapFrom(filmDto);

//        filmDao.save(filmEntity)
        filmDao.save(filmEntity);
//        return id
        return filmEntity.getId();
    }

    public List<FilmDto> findAll() {
        return filmDao.findAll().stream()
                .map(filmMapper::mapFrom)
                .collect(toList());
    }

    public FilmDto findById(Long id) {
        return filmDao.findById(id).map(filmMapper::mapFrom).orElse(null);
    }

    public List<FilmDto> findByActorId(Long actorId) {
        return filmDao.findByActorId(actorId).stream().map(filmMapper::mapFrom).toList();
    }

    public List<FilmDto> findByDirectorId(Long directorId) {
        return filmDao.findByDirectorId(directorId).stream().map(filmMapper::mapFrom).toList();
    }

    public List<FilmDto> findAllByReleaseYear(String year) {
        String startDate = year + "-01-01";
        String endDate = year + "-12-31";
        if (LocalDateFormatter.isValid(startDate) && LocalDateFormatter.isValid(endDate)) {
            return filmDao.findAllBetweenDates(LocalDateFormatter.format(startDate), LocalDateFormatter.format(endDate))
                    .stream()
                    .map(filmMapper::mapFrom)
                    .toList();
        } else {
            throw new ValidationException(List.of(Error.of("invalid.year", "Year is invalid")));
        }
    }

    public static FilmService getInstance() {
        return INSTANCE;
    }
}
