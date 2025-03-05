package com.ziker0k.http.service;

import com.ziker0k.http.dao.PersonDao;
import com.ziker0k.http.dto.CreatePersonDto;
import com.ziker0k.http.dto.PersonDto;
import com.ziker0k.http.exception.ValidationException;
import com.ziker0k.http.mapper.CreatePersonMapper;
import com.ziker0k.http.mapper.PersonMapper;
import com.ziker0k.http.validator.CreatePersonValidator;
import com.ziker0k.http.validator.Error;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;

import static java.util.stream.Collectors.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PersonService {
    private static final PersonService INSTANCE = new PersonService();

    private final CreatePersonValidator createPersonValidator = CreatePersonValidator.getInstance();
    private final PersonDao personDao = PersonDao.getInstance();
    private final CreatePersonMapper createPersonMapper = CreatePersonMapper.getInstance();
    private final PersonMapper personMapper = PersonMapper.getInstance();

    public Long create(CreatePersonDto createPersonDto) {
//        validation
        var validationResult = createPersonValidator.isValid(createPersonDto);
        if (!validationResult.isValid()) {
            throw new ValidationException(validationResult.getErrors());
        }
//        map
        var actorDirectorEntity = createPersonMapper.mapFrom(createPersonDto);
//        actorDirectorDao.save
        personDao.save(actorDirectorEntity);
//        return id
        return actorDirectorEntity.getId();
    }

    public List<PersonDto> findAll() {
        return personDao.findAll().stream()
                .map(personMapper::mapFrom)
                .collect(toList());
    }

    public PersonDto findById(String id) {
        try {
            Long personId = Long.parseLong(id);
            return personDao.findById(personId).map(personMapper::mapFrom).orElse(null);
        } catch (NullPointerException e) {
            throw new ValidationException(List.of(Error.of("invalid.id", "Invalid id:" + id)));
        }
    }

    public List<PersonDto> findAllActorsByFilmId(Long filmId) {
        return personDao.findActorsByFilmId(filmId).stream()
                .map(personMapper::mapFrom)
                .collect(toList());
    }

    public List<PersonDto> findAllDirectorsByFilmId(Long filmId) {
        return personDao.findDirectorsByFilmId(filmId).stream()
                .map(personMapper::mapFrom)
                .collect(toList());
    }

    public boolean isExists(Long[] actorsIds) {
        return personDao.ifExists(actorsIds);
    }

    public static PersonService getInstance() {
        return INSTANCE;
    }
}
