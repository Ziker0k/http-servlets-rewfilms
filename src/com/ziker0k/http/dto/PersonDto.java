package com.ziker0k.http.dto;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDate;

@Value
@Builder
public class PersonDto {
    Long id;
    String fullName;
    LocalDate birthday;
}
