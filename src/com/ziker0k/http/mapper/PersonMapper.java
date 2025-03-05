package com.ziker0k.http.mapper;

import com.ziker0k.http.dto.PersonDto;
import com.ziker0k.http.entity.PersonEntity;

public class PersonMapper implements Mapper<PersonEntity, PersonDto> {

    private static final PersonMapper INSTANCE = new PersonMapper();

    @Override
    public PersonDto mapFrom(PersonEntity from) {
        return PersonDto.builder()
                .id(from.getId())
                .fullName(from.getFullName())
                .birthday(from.getBirthday())
                .build();
    }

    public static PersonMapper getInstance() {
        return INSTANCE;
    }
}
