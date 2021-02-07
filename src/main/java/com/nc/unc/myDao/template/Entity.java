package com.nc.unc.myDao.template;

import com.nc.unc.myDao.annotation.Attribute;
import com.nc.unc.myDao.annotation.PrimaryKey;
import org.springframework.jdbc.support.KeyHolder;

import java.lang.reflect.Field;
import java.util.Map;

public interface Entity<T> {

    Object[] resolveCreateParameters(T entity);

    Object[] resolveUpdateParameters(T entity);

    Object[] resolvePrimaryKeyParameters(T entity);

    PrimaryKey getPrimaryKey();

    Field getFieldPrimaryKey();

    Map<Field, Attribute> getFieldAttributeMap();

    Map<Field, Attribute> getFieldEnumeratedMap();
}
