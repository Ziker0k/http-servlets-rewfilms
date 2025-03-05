package com.ziker0k.http.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CreatePersonDto {
    String fullName;
    String birthday;
}
