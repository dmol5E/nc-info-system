package com.nc.unc.myDao.impl;

import com.nc.unc.myDao.CrudDAO;
import com.nc.unc.myDao.mapper.AbstractMapper;
import com.nc.unc.myDao.template.Entity;
import com.nc.unc.myDao.template.EntityImpl;
import com.nc.unc.myDao.template.PostgreSQLTemplate;
import com.nc.unc.myDao.template.SQLTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
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

    public CrudDaoImpl(DataSource dataSource){
        Type t = getClass().getGenericSuperclass();
        ParameterizedType pt = (ParameterizedType) t;
        Class<T> entityClass = (Class<T>) pt.getActualTypeArguments()[0];
        entity = new EntityImpl<>(entityClass);

        jdbcTemplate = new JdbcTemplate(dataSource);
        postgreSQLTemplate = new PostgreSQLTemplate(entityClass,
                                            entity.getPrimaryKey(),
                                            entity.getFieldAttributeMap(),
                                            entity.getFieldEnumeratedMap());
        log.info("Entity getField ");
        this.abstractMapper = new AbstractMapper<>(entityClass,
                                                    entity.getFieldPrimaryKey(),
                                                    entity.getFieldAttributeMap(),
                                                    entity.getFieldEnumeratedMap());

    }
    @Override
    public Optional<T> find(Number id) {
        try {
            T entity = jdbcTemplate.queryForObject(postgreSQLTemplate.getSelectSQL(), abstractMapper, id);
            return Optional.ofNullable(entity);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public List<T> findIn(List<?> args) {
        if(args.size() == 0){
            return new ArrayList<>();
        };
        return jdbcTemplate.query(postgreSQLTemplate.assembleVariableSelectInSql(args.size()), abstractMapper, args.toArray());
    }

    @Override
    public void delete(T t) {
        jdbcTemplate.update(postgreSQLTemplate.getDeleteSQL(), entity.resolvePrimaryKeyParameters(t));
    }

    @Override
    public void insert(T t) {
        jdbcTemplate.update(postgreSQLTemplate.getUpdateSQL(), entity.resolvePrimaryKeyParameters(t));
    }
}
