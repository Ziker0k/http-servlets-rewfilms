package com.ziker0k.http.validator;

import com.ziker0k.http.dto.CreateUserDto;
import com.ziker0k.http.entity.Gender;
import com.ziker0k.http.entity.Role;
import com.ziker0k.http.util.LocalDateFormatter;

public class CreateUserValidator implements Validator<CreateUserDto> {

    private static final CreateUserValidator INSTANCE = new CreateUserValidator();

    @Override
    public ValidationResult isValid(CreateUserDto createUserDto) {
        var validationResult = new ValidationResult();
        if(!LocalDateFormatter.isValid(createUserDto.getBirthday())) {
            validationResult.addError(Error.of("invalid.birthday", "Birthday is invalid"));
        }
        if(Gender.find(createUserDto.getGender()).isEmpty()) {
            validationResult.addError(Error.of("invalid.gender", "Gender is invalid"));
        }
        if(Role.find(createUserDto.getRole()).isEmpty()) {
            validationResult.addError(Error.of("invalid.role", "Role is invalid"));
        }

        return validationResult;
    }

    public static CreateUserValidator getInstance() {
        return INSTANCE;
    }
}
