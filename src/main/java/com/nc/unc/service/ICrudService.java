package com.nc.unc.service;

import java.util.List;
import java.util.Optional;

public interface ICrudService<T> {
    /**
     * CRUD methods
     * */
    Optional<T> find(Number id);
    List<T> findIn(List<?> args);
    void delete(T t);
    Number insert(T t);
    List<T> getAll();
    void update(T t);
}
