package com.ziker0k.http.mapper;

import com.ziker0k.http.dto.UserDto;
import com.ziker0k.http.entity.UserEntity;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class UserMapper implements Mapper<UserEntity, UserDto> {

    private static final UserMapper INSTANCE = new UserMapper();

    @Override
    public UserDto mapFrom(UserEntity from) {
        return UserDto.builder()
                .id(from.getId())
                .name(from.getName())
                .birthday(from.getBirthday())
                .email(from.getEmail())
                .image(from.getImage())
                .role(from.getRole())
                .gender(from.getGender())
                .build();
    }

    public UserEntity mapTo(UserDto from) {
        return UserEntity.builder()
                .id(from.getId())
                .name(from.getName())
                .birthday(from.getBirthday())
                .email(from.getEmail())
                .image(from.getImage())
                .role(from.getRole())
                .gender(from.getGender())
                .build();
    }

    public static UserMapper getInstance() {
        return INSTANCE;
    }
}
