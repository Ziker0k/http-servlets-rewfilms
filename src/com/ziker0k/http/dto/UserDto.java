package com.ziker0k.http.dto;

import com.ziker0k.http.entity.Gender;
import com.ziker0k.http.entity.Role;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDate;

@Value
@Builder
public class UserDto {
    Long id;
    String name;
    LocalDate birthday;
    String email;
    String image;
    Role role;
    Gender gender;
}
