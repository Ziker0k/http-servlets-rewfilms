package com.ziker0k.http.mapper;

import com.ziker0k.http.dto.CreatePersonDto;
import com.ziker0k.http.entity.PersonEntity;
import com.ziker0k.http.util.LocalDateFormatter;

public class CreatePersonMapper implements Mapper<CreatePersonDto, PersonEntity> {
    private static final CreatePersonMapper INSTANCE = new CreatePersonMapper();

    @Override
    public PersonEntity mapFrom(CreatePersonDto from) {
        return PersonEntity.builder()
                .fullName(from.getFullName())
                .birthday(LocalDateFormatter.format(from.getBirthday()))
                .build();
    }

    public static CreatePersonMapper getInstance() {
        return INSTANCE;
    }
}
