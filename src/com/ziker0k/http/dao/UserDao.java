package com.ziker0k.http.dao;

import com.ziker0k.http.entity.Gender;
import com.ziker0k.http.entity.Role;
import com.ziker0k.http.entity.UserEntity;
import com.ziker0k.http.util.ConnectionManager;
import lombok.SneakyThrows;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static java.sql.PreparedStatement.RETURN_GENERATED_KEYS;

public class UserDao implements Dao<Long, UserEntity> {

    private static final UserDao INSTANCE = new UserDao();

    private static final String SAVE_SQL = "INSERT INTO user_client (name, birthday, email, password, role, gender, image) Values (?, ?, ?, ?, ?, ?, ?)";
    private static final String GET_BY_EMAIL_AND_PASSWORD_SQL = """
            SELECT id, name, birthday, email, password, role, gender, image
            FROM user_client
            WHERE email = ? AND password = ?
            ;""";
    private static final String FIND_BY_ID = "SELECT id, name, birthday, email, image, password, role, gender FROM user_client WHERE id = ?";

    @Override
    public UserEntity save(UserEntity entity) {
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(SAVE_SQL, RETURN_GENERATED_KEYS)) {
            preparedStatement.setObject(1, entity.getName());
            preparedStatement.setObject(2, Date.valueOf(entity.getBirthday()));
            preparedStatement.setObject(3, entity.getEmail());
            preparedStatement.setObject(4, entity.getPassword());
            preparedStatement.setObject(5, entity.getRole().name());
            preparedStatement.setObject(6, entity.getGender().name());
            preparedStatement.setObject(7, entity.getImage());
            preparedStatement.executeUpdate();

            var generatedKeys = preparedStatement.getGeneratedKeys();
            generatedKeys.next();
            entity.setId(generatedKeys.getObject("id", Long.class));

            return entity;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<UserEntity> findAll() {
        return List.of();
    }

    @SneakyThrows
    public Optional<UserEntity> findByEmailAndPassword(String email, String password) {
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(GET_BY_EMAIL_AND_PASSWORD_SQL)) {
            preparedStatement.setObject(1, email);
            preparedStatement.setObject(2, password);

            var resultSet = preparedStatement.executeQuery();
            UserEntity user = null;
            if (resultSet.next()) {
                user = buildEntity(resultSet);
            }

            return Optional.ofNullable(user);
        }
    }

    @SneakyThrows
    @Override
    public Optional<UserEntity> findById(Long id) {
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(FIND_BY_ID)) {
            preparedStatement.setObject(1, id);

            var resultSet = preparedStatement.executeQuery();
            UserEntity user = null;
            if (resultSet.next()) {
                user = buildEntity(resultSet);
            }

            return Optional.ofNullable(user);
        }
    }

    @Override
    public void update(UserEntity entity) {
    }

    @Override
    public boolean delete(Long id) {
        return false;
    }

    public static UserDao getInstance() {
        return INSTANCE;
    }

    private static UserEntity buildEntity(ResultSet resultSet) throws SQLException {
        return UserEntity.builder()
                .id(resultSet.getObject("id", Long.class))
                .name(resultSet.getObject("name", String.class))
                .birthday(resultSet.getObject("birthday", Date.class).toLocalDate())
                .email(resultSet.getObject("email", String.class))
                .password(resultSet.getObject("password", String.class))
                .role(Role.find(resultSet.getObject("role", String.class)).orElse(null))
                .gender(Gender.find(resultSet.getObject("gender", String.class)).orElse(null))
                .build();
    }
}
