package com.ziker0k.http.dao;

import com.ziker0k.http.entity.PersonEntity;
import com.ziker0k.http.exception.DaoException;
import com.ziker0k.http.util.ConnectionManager;
import lombok.SneakyThrows;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.sql.Statement.RETURN_GENERATED_KEYS;

public class PersonDao implements Dao<Long, PersonEntity> {

    private final static PersonDao INSTANCE = new PersonDao();

    private static final String SAVE = "INSERT INTO person(full_name, birth_date) VALUES (?, ?)";
    private static final String FIND_BY_ID = "SELECT id, full_name, birth_date FROM person WHERE id = ?";
    private static final String FIND_ALL = "SELECT id, full_name, birth_date FROM person";
    private static final String FIND_ACTORS_BY_FILM_ID = """
            SELECT p.id, p.full_name, p.birth_date
            FROM person p
                     LEFT JOIN actor_in_film aif on p.id = aif.actor_id
            WHERE aif.film_id = ?;
            """;
    private static final String FIND_DIRECTORS_BY_FILM_ID = """
            SELECT p.id, p.full_name, p.birth_date
            FROM person p
                     LEFT JOIN director_in_film dif on p.id = dif.director_id
            WHERE dif.film_id = ?;
            """;
    private static final String IS_PERSONS_EXISTS = """
            SELECT
                (SELECT array(SELECT id FROM person)) @> ?;
            """;

    @SneakyThrows
    @Override
    public List<PersonEntity> findAll() {
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(FIND_ALL)) {
            var resultSet = preparedStatement.executeQuery();
            List<PersonEntity> persons = new ArrayList<>();
            while (resultSet.next()) {
                persons.add(buildPerson(resultSet));
            }
            return persons;
        }
    }

    @SneakyThrows
    public List<PersonEntity> findActorsByFilmId(Long filmId) {
        return findByFilmId(filmId, FIND_ACTORS_BY_FILM_ID);
    }

    public List<PersonEntity> findDirectorsByFilmId(Long filmId) {
        return findByFilmId(filmId, FIND_DIRECTORS_BY_FILM_ID);
    }

    @SneakyThrows
    public boolean ifExists(Long[] ids) {
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(IS_PERSONS_EXISTS)) {
            preparedStatement.setObject(1, ids);
            preparedStatement.executeQuery();

            var resultSet = preparedStatement.getResultSet();
            resultSet.next();

            return resultSet.getObject(1, Boolean.class);
        }
    }

    @Override
    public Optional<PersonEntity> findById(Long id) {
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(FIND_BY_ID)) {
            preparedStatement.setObject(1, id);

            Optional<PersonEntity> personEntity = Optional.empty();

            var resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                personEntity = Optional.of(buildPerson(resultSet));
            }
            return personEntity;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public boolean delete(Long id) {
        return false;
    }

    @Override
    public void update(PersonEntity entity) {

    }

    @SneakyThrows
    @Override
    public PersonEntity save(PersonEntity entity) {
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(SAVE, RETURN_GENERATED_KEYS)) {
            preparedStatement.setObject(1, entity.getFullName());
            preparedStatement.setObject(2, entity.getBirthday());
            preparedStatement.executeUpdate();

            var generatedKeys = preparedStatement.getGeneratedKeys();
            generatedKeys.next();
            entity.setId(generatedKeys.getObject("id", Long.class));

            return entity;
        }
    }

    public static PersonDao getInstance() {
        return INSTANCE;
    }

    @SneakyThrows
    private List<PersonEntity> findByFilmId(Long filmId, String sql) {
        try (Connection connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setObject(1, filmId);
            var resultSet = preparedStatement.executeQuery();

            List<PersonEntity> persons = new ArrayList<>();

            while (resultSet.next()) {
                persons.add(buildPerson(resultSet));
            }
            return persons;
        }
    }

    private PersonEntity buildPerson(ResultSet resultSet) throws SQLException {
        return PersonEntity.builder()
                .id(resultSet.getObject("id", Long.class))
                .fullName(resultSet.getObject("full_name", String.class))
                .birthday(resultSet.getObject("birth_date", LocalDate.class))
                .build();
    }
}
