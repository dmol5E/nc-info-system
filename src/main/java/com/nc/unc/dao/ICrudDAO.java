package com.nc.unc.dao;

import com.nc.unc.dao.mapper.AbstractMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Optional;

public interface ICrudDAO<T> {
    /**
     * CRUD methods
     * */
    Optional<T> find(Number id);
    List<T> findIn(List<?> args);
    void delete(T t);
    Number insert(T t);
    List<T> getAll();
    void update(T t);
    void saveAll(Iterable<T> t);
    boolean isAlreadyExists(T t);
    /**
     * util methods
     * */

    AbstractMapper<T> getAbstractMapper();
    void setAbstractMapper(AbstractMapper<T> abstractMapper);
    JdbcTemplate getJdbcTemplate();
}
