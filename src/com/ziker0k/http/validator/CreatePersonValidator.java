package com.ziker0k.http.validator;

import com.ziker0k.http.dto.CreatePersonDto;
import com.ziker0k.http.util.LocalDateFormatter;

public class CreatePersonValidator implements Validator<CreatePersonDto> {

    private static final CreatePersonValidator INSTANCE = new CreatePersonValidator();

    @Override
    public ValidationResult isValid(CreatePersonDto createPersonDto) {
        var validationResult = new ValidationResult();
        if(!LocalDateFormatter.isValid(createPersonDto.getBirthday()))
            validationResult.addError(Error.of("invalid.birthday", "Birthday is invalid"));
        return validationResult;
    }

    public static CreatePersonValidator getInstance() {
        return INSTANCE;
    }
}
