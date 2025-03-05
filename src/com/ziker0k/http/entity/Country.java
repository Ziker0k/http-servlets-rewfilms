package com.ziker0k.http.entity;

import java.util.Arrays;
import java.util.Optional;

public enum Country {
    RUSSIA,
    USA,
    CHINA,
    GERMANY;

    public static Optional<Country> find(String country) {
        return Arrays.stream(values())
                .filter(it -> it.name().equals(country))
                .findFirst();
    }
}
