package com.ziker0k.http.dao;

import java.util.List;
import java.util.Optional;

public interface Dao<K, T> {

    T save(T entity);

    List<T> findAll();

    Optional<T> findById(K id);

    void update(T entity);

    boolean delete(K id);
}
