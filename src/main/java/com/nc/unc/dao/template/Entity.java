package com.nc.unc.dao.template;

import com.nc.unc.dao.annotation.Attribute;
import com.nc.unc.dao.annotation.PrimaryKey;

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
