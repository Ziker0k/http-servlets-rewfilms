package com.ziker0k.http.dao;

import com.ziker0k.http.entity.Country;
import com.ziker0k.http.entity.FilmEntity;
import com.ziker0k.http.entity.Genre;
import com.ziker0k.http.exception.DaoException;
import com.ziker0k.http.util.ConnectionManager;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.sql.Statement.RETURN_GENERATED_KEYS;

public class FilmDao implements Dao<Long, FilmEntity> {
    private static final FilmDao INSTANCE = new FilmDao();

    private static final String FIND_ALL = """
            SELECT id, name, description, release_date, country, genre
            FROM film
            """;
    private static final String FIND_BY_ID = """
            SELECT id, name, description, release_date, country, genre
            FROM film
            WHERE id = ?
            """;
    private static final String FIND_BY_ACTOR_ID = """
            SELECT DISTINCT id, name, description, release_date, country, genre
            FROM film
                     LEFT JOIN actor_in_film aif on film.id = aif.film_id
            WHERE actor_id = ?;
            """;
    private static final String FIND_BY_DIRECTOR_ID = """
            SELECT DISTINCT id, name, description, release_date, country, genre
            FROM film
                     LEFT JOIN director_in_film dif on film.id = dif.film_id
            WHERE director_id = ?;
            """;
    private static final String FIND_BY_RELEASE_YEAR = """
            SELECT id, name, description, release_date, country, genre
            FROM film
            WHERE release_date <@ daterange(?, ?);
            """;
    private static final String SAVE = """
            INSERT INTO film (name, description, release_date, country, genre) VALUES (?, ?, ?, ?, ?);
            """;
    private static final String INSERT_ACTORS_INTO_FILM = """
            INSERT INTO actor_in_film (film_id, actor_id)
            SELECT ? id, x
            FROM unnest(?) x;
            """;
    private static final String INSERT_DIRECTORS_INTO_FILM = """
            INSERT INTO director_in_film (film_id, director_id)
            SELECT ? id, x
            FROM unnest(?) x;
            """;

    private FilmDao() {
    }

    @Override
    public FilmEntity save(FilmEntity entity) {
        try {
            var connection = ConnectionManager.get();
            var preparedStatement1 = connection.prepareStatement(SAVE, RETURN_GENERATED_KEYS);
            var preparedStatement2 = connection.prepareStatement(INSERT_ACTORS_INTO_FILM);
            var preparedStatement3 = connection.prepareStatement(INSERT_DIRECTORS_INTO_FILM);
            try {
                connection.setAutoCommit(false);

                preparedStatement1.setObject(1, entity.getName());
                preparedStatement1.setObject(2, entity.getDescription());
                preparedStatement1.setObject(3, entity.getReleaseDate());
                preparedStatement1.setObject(4, entity.getCountry().name());
                preparedStatement1.setObject(5, entity.getGenre().name());
                preparedStatement1.executeUpdate();

                var generatedKeys = preparedStatement1.getGeneratedKeys();
                generatedKeys.next();
                entity.setId(generatedKeys.getObject("id", Long.class));

                var actorsArray = connection.createArrayOf("Long", entity.getActors().toArray());
                var directorsArray = connection.createArrayOf("Long", entity.getDirectors().toArray());

                preparedStatement2.setObject(1, entity.getId());
                preparedStatement2.setObject(2, actorsArray);
                preparedStatement2.executeUpdate();

                preparedStatement3.setObject(1, entity.getId());
                preparedStatement3.setArray(2, directorsArray);
                preparedStatement3.executeUpdate();


                connection.commit();
                return entity;
            } catch (SQLException sqlException){
                connection.rollback();
                throw new DaoException(sqlException);
            } finally {
                connection.setAutoCommit(true);
                preparedStatement1.close();
                preparedStatement2.close();
                preparedStatement3.close();
                connection.close();
            }
        } catch (SQLException exception) {
            throw new DaoException(exception);
        }
    }

    @Override
    public List<FilmEntity> findAll() {
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(FIND_ALL)) {
            var resultSet = preparedStatement.executeQuery();
            List<FilmEntity> films = new ArrayList<>();
            while (resultSet.next()) {
                films.add(buildFilm(resultSet));
            }
            return films;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<FilmEntity> findAllBetweenDates(LocalDate from, LocalDate to) {
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(FIND_BY_RELEASE_YEAR)) {
            preparedStatement.setObject(1, from);
            preparedStatement.setObject(2, to);
            var resultSet = preparedStatement.executeQuery();
            List<FilmEntity> films = new ArrayList<>();
            while (resultSet.next()) {
                films.add(buildFilm(resultSet));
            }
            return films;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<FilmEntity> findById(Long id) {
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(FIND_BY_ID)) {
            preparedStatement.setObject(1, id);

            Optional<FilmEntity> filmEntity = Optional.empty();

            var resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                filmEntity = Optional.of(buildFilm(resultSet));
            }
            return filmEntity;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public List<FilmEntity> findByActorId(Long actorId) {
        return findByIdWithSQL(actorId, FIND_BY_ACTOR_ID);
    }

    public List<FilmEntity> findByDirectorId(Long directorId) {
        return findByIdWithSQL(directorId, FIND_BY_DIRECTOR_ID);
    }

    @Override
    public boolean delete(Long id) {
        return false;
    }

    @Override
    public void update(FilmEntity entity) {

    }

    public static FilmDao getInstance() {
        return INSTANCE;
    }

    private List<FilmEntity> findByIdWithSQL(Long id, String sql) {
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, id);

            var resultSet = preparedStatement.executeQuery();
            List<FilmEntity> films = new ArrayList<>();
            while (resultSet.next()) {
                films.add(buildFilm(resultSet));
            }
            return films;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private FilmEntity buildFilm(ResultSet resultSet) throws SQLException {
        return FilmEntity.builder()
                .id(resultSet.getObject("id", Long.class))
                .name(resultSet.getObject("name", String.class))
                .description(resultSet.getObject("description", String.class))
                .releaseDate(resultSet.getObject("release_date", LocalDate.class))
                .country(Country.valueOf(resultSet.getObject("country", String.class)))
                .genre(Genre.valueOf(resultSet.getObject("genre", String.class)))
                .build();
    }
}
