package com.nc.unc.myDao.impl;

import com.nc.unc.myDao.CrudDAO;
import com.nc.unc.myDao.mapper.AbstractMapper;
import com.nc.unc.myDao.template.Entity;
import com.nc.unc.myDao.template.EntityImpl;
import com.nc.unc.myDao.template.PostgreSQLTemplate;
import com.nc.unc.myDao.template.SQLTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j(topic = "CrudDaoImpl")
public abstract class CrudDaoImpl<T> implements CrudDAO<T> {

    private JdbcTemplate jdbcTemplate;
    private SQLTemplate postgreSQLTemplate;

    private AbstractMapper<T> abstractMapper;
    private Entity<T> entity;

    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Autowired
    public CrudDaoImpl(){
        Type t = getClass().getGenericSuperclass();
        ParameterizedType pt = (ParameterizedType) t;
        Class<T> entityClass = (Class<T>) pt.getActualTypeArguments()[0];
        entity = new EntityImpl<>(entityClass);

        postgreSQLTemplate = new PostgreSQLTemplate(entityClass,
                                            entity.getPrimaryKey(),
                                            entity.getFieldAttributeMap(),
                                            entity.getFieldEnumeratedMap());
        log.info("Entity getField ");
        this.abstractMapper = new AbstractMapper<>(entityClass,
                                                    entity);

    }

    private boolean isAlreadyExists(T obj) {
        Long count = jdbcTemplate.queryForObject(postgreSQLTemplate.getExistsSql(),
                Long.class,
                this.entity.resolvePrimaryKeyParameters(obj));
        if (count == null) {
            throw new RuntimeException();
        }
        return count > 0L;
    }

    @Override
    public Optional<T> find(Number id) {
        try {
            T obj = jdbcTemplate.queryForObject(postgreSQLTemplate.getSelectSQL(), abstractMapper, id);
            return Optional.ofNullable(obj);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public List<T> findIn(List<?> args) {
        if(args.size() == 0){
            return new ArrayList<>();
        };
        return jdbcTemplate.query(postgreSQLTemplate.assembleVariableSelectInSql(args.size()), args.toArray(), abstractMapper);
    }

    public void update(T t){
        jdbcTemplate.update(postgreSQLTemplate.getUpdateSQL(), entity.resolvePrimaryKeyParameters(t));
    }

    @Override
    public void delete(T t) {
        jdbcTemplate.update(postgreSQLTemplate.getDeleteSQL(), entity.resolvePrimaryKeyParameters(t));
    }

    @Override
    public void insert(T t) {
        jdbcTemplate.update(postgreSQLTemplate.getInsertSQL(), preparedStatement -> {
            int i = 1;
            for (Object obj: entity.resolveCreateParameters(t))
                preparedStatement.setObject(i++, obj);
        });
    }
    @Override
    public AbstractMapper<T> getAbstractMapper() { return abstractMapper; }

    @Override
    public List<T> getAll() {
        return jdbcTemplate.query(postgreSQLTemplate.getSelectAllSql(), abstractMapper);
    }

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    public void setAbstractMapper(AbstractMapper<T> abstractMapper) {
        this.abstractMapper = abstractMapper;
    }
}
