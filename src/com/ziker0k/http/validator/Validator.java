package com.ziker0k.http.validator;

public interface Validator<T> {
    ValidationResult isValid(T t);
}
