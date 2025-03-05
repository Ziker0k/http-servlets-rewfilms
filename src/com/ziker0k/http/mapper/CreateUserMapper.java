package com.ziker0k.http.mapper;

import com.ziker0k.http.dto.CreateUserDto;
import com.ziker0k.http.entity.Gender;
import com.ziker0k.http.entity.Role;
import com.ziker0k.http.entity.UserEntity;
import com.ziker0k.http.util.LocalDateFormatter;

public class CreateUserMapper implements Mapper<CreateUserDto, UserEntity> {
    private static final String IMAGE_FOLDER = "users/";
    private static final String DEFAULT_IMAGE = "default.png";
    private static final CreateUserMapper INSTANCE = new CreateUserMapper();

    @Override
    public UserEntity mapFrom(CreateUserDto from) {
        String imagePath = from.getImage().getSize() == 0 ? DEFAULT_IMAGE : from.getImage().getSubmittedFileName();
        return UserEntity.builder()
                .name(from.getName())
                .birthday(LocalDateFormatter.format(from.getBirthday()))
                .email(from.getEmail())
                .image(IMAGE_FOLDER + imagePath)
                .password(from.getPassword())
                .role(Role.valueOf(from.getRole()))
                .gender(Gender.valueOf(from.getGender()))
                .build();
    }

    public static CreateUserMapper getInstance() {
        return INSTANCE;
    }
}
