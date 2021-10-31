package com.epam.esm.dao;

import java.util.List;
import java.util.Optional;

public interface BaseDAO<T, K> {
    List<T> findAll();

    Optional<T> findById(K k);

    T create(T t);

    boolean delete(K k);
}
