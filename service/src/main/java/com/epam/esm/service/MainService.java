package com.epam.esm.service;

import java.util.List;

public interface MainService<T, K> {
    List<T> findAll();

    T findById(K k);

    T create(T t);

    boolean delete(K k);
}
