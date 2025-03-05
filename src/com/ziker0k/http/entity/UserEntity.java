package com.ziker0k.http.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserEntity {
    private Long id;
    private String name;
    private LocalDate birthday;
    private String email;
    private String image;
    private String password;
    private Role role;
    private Gender gender;
}
