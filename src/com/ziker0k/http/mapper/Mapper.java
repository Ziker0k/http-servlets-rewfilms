package com.ziker0k.http.mapper;

public interface Mapper<F, T> {
    T mapFrom(F from);
}
