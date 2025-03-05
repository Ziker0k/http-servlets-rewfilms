package com.ziker0k.http.service;

import com.ziker0k.http.dao.UserDao;
import com.ziker0k.http.dto.CreateUserDto;
import com.ziker0k.http.dto.UserDto;
import com.ziker0k.http.exception.ValidationException;
import com.ziker0k.http.mapper.CreateUserMapper;
import com.ziker0k.http.mapper.UserMapper;
import com.ziker0k.http.validator.CreateUserValidator;
import com.ziker0k.http.validator.Error;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;

import java.util.List;
import java.util.Optional;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class UserService {
    private static final UserService INSTANCE = new UserService();

    private final CreateUserValidator createUserValidator = CreateUserValidator.getInstance();
    private final UserDao userDao = UserDao.getInstance();
    private final CreateUserMapper createUserMapper = CreateUserMapper.getInstance();
    private final UserMapper userMapper = UserMapper.getInstance();
    private final ImageService imageService = ImageService.getInstance();

    public Optional<UserDto> login(String email, String password) {
        return userDao.findByEmailAndPassword(email, password)
                .map(userMapper::mapFrom);
    }

    @SneakyThrows
    public Long create(CreateUserDto createUserDto) {
//        validation
        var validationResult = createUserValidator.isValid(createUserDto);
        if (!validationResult.isValid()) {
            throw new ValidationException(validationResult.getErrors());
        }
//        map
        var userEntity = createUserMapper.mapFrom(createUserDto);
//        userDao.save
        imageService.uploadImage(userEntity.getImage(), createUserDto.getImage().getInputStream());
        userDao.save(userEntity);
//        return id
        return userEntity.getId();
    }

    public UserDto findById(String id) {
        try {
            Long userId = Long.parseLong(id);
            return userDao.findById(userId).map(userMapper::mapFrom).orElse(null);
        } catch (NullPointerException e) {
            throw new ValidationException(List.of(Error.of("invalid.id", "Invalid id:" + id)));
        }
    }

    public static UserService getInstance() {
        return INSTANCE;
    }
}
