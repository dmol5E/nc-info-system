package com.nc.unc.myDao;

import java.util.List;
import java.util.Optional;

public interface CrudDAO<T> {
    Optional<T> find(Number id);
    List<T> findIn(List<?> args);
    void delete(T t);
    void insert(T t);
}
