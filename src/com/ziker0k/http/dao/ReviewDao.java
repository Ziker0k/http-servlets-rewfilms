package com.ziker0k.http.dao;

import com.ziker0k.http.entity.Gender;
import com.ziker0k.http.entity.ReviewEntity;
import com.ziker0k.http.entity.Role;
import com.ziker0k.http.entity.UserEntity;
import com.ziker0k.http.util.ConnectionManager;
import lombok.SneakyThrows;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.sql.Statement.RETURN_GENERATED_KEYS;

public class ReviewDao implements Dao<Long, ReviewEntity> {
    private static final ReviewDao INSTANCE = new ReviewDao();

    private static final String SAVE_STATEMENT = "INSERT INTO review (film_id, user_id, description, grade) VALUES (?, ?, ?, ?)";
    private static final String FIND_BY_FILM_ID_STATEMENT = """
            SELECT r.id review_id, r.film_id, r.description, r.grade,
                   u.id user_id, u.name, u.birthday, u.email, u.image, u.password, u.role, u.gender
            FROM review r
                LEFT JOIN user_client u on r.user_id = u.id
            WHERE film_id = ?;
            """;
    private static final String FIND_BY_USER_ID = "SELECT id, film_id, user_id, description, grade, creation_date FROM review WHERE user_id = ?";

    @Override
    public List<ReviewEntity> findAll() {
        return List.of();
    }

    @Override
    public Optional<ReviewEntity> findById(Long id) {
        return Optional.empty();
    }

    @SneakyThrows
    public List<ReviewEntity> findByFilmId(Long filmId) {
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(FIND_BY_FILM_ID_STATEMENT)) {

            preparedStatement.setLong(1, filmId);
            var resultSet = preparedStatement.executeQuery();

            List<ReviewEntity> reviews = new ArrayList<>();

            while (resultSet.next()) {
                reviews.add(buildReview(resultSet));
            }
            return reviews;
        }
    }

    @SneakyThrows
    public List<ReviewEntity> findByUserId(Long userId) {
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(FIND_BY_USER_ID)) {

            preparedStatement.setLong(1, userId);
            var resultSet = preparedStatement.executeQuery();

            List<ReviewEntity> reviews = new ArrayList<>();

            while (resultSet.next()) {
                reviews.add(buildReviewWithoutUser(resultSet));
            }
            return reviews;
        }
    }

    @Override
    public boolean delete(Long id) {
        return false;
    }

    @Override
    public void update(ReviewEntity entity) {

    }

    @Override
    @SneakyThrows
    public ReviewEntity save(ReviewEntity entity) {
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(SAVE_STATEMENT, RETURN_GENERATED_KEYS)) {
            preparedStatement.setObject(1, entity.getFilmId());
            preparedStatement.setObject(2, entity.getUser().getId());
            preparedStatement.setObject(3, entity.getDescription());
            preparedStatement.setObject(4, entity.getGrade());
            preparedStatement.executeUpdate();

            var generatedKeys = preparedStatement.getGeneratedKeys();
            generatedKeys.next();
            entity.setId(generatedKeys.getObject("id", Long.class));

            return entity;
        }
    }

    public static ReviewDao getInstance() {
        return INSTANCE;
    }

    private ReviewEntity buildReview(ResultSet resultSet) throws SQLException {
        UserEntity userEntity = UserEntity.builder()
                .id(resultSet.getObject("user_id", Long.class))
                .name(resultSet.getObject("name", String.class))
                .birthday(resultSet.getObject("birthday", Date.class).toLocalDate())
                .email(resultSet.getObject("email", String.class))
                .password(resultSet.getObject("password", String.class))
                .role(Role.find(resultSet.getObject("role", String.class)).orElse(null))
                .gender(Gender.find(resultSet.getObject("gender", String.class)).orElse(null))
                .build();

        return ReviewEntity.builder()
                .id(resultSet.getObject("review_id", Long.class))
                .filmId(resultSet.getObject("film_id", Long.class))
                .user(userEntity)
                .description(resultSet.getObject("description", String.class))
                .grade(resultSet.getObject("grade", Integer.class))
                .build();
    }

    private ReviewEntity buildReviewWithoutUser(ResultSet resultSet) throws SQLException {
        return ReviewEntity.builder()
                .id(resultSet.getObject("id", Long.class))
                .filmId(resultSet.getObject("film_id", Long.class))
                .description(resultSet.getObject("description", String.class))
                .grade(resultSet.getObject("grade", Integer.class))
                .build();
    }
}
