package com.nc.unc.myDao.template;

import com.nc.unc.myDao.annotation.*;
import lombok.Getter;
import org.springframework.jdbc.support.KeyHolder;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

import static java.lang.Math.toIntExact;

public class EntityImpl<T> implements Entity<T> {

    private Class<T> entity;

    @Getter
    private Field fieldPrimaryKey;

    @Getter
    private PrimaryKey primaryKey;

    @Getter
    private Map<Field, Attribute> fieldEnumeratedMap = new HashMap<>();


    @Getter
    private Map<Field, Attribute> fieldAttributeMap = new HashMap<>();

    public EntityImpl(Class<T> entity){
        this.entity = entity;
        fillField();
    }

    private void fillField() {
        Field[] fields = entity.getDeclaredFields();
        for (Field field: fields){
            field.setAccessible(true);

            Attribute attribute = field.getAnnotation(Attribute.class);
            if(attribute != null){
                Enumerated enumerated = field.getAnnotation(Enumerated.class);
                if (enumerated != null){
                    fieldEnumeratedMap.put(field, attribute);
                } else {
                    fieldAttributeMap.put(field, attribute);
                }
            }


            PrimaryKey primaryKeyObj = field.getAnnotation(PrimaryKey.class);
            if (primaryKeyObj != null && primaryKey == null){
                this.primaryKey = primaryKeyObj;
                this.fieldPrimaryKey = field;
            }else if(primaryKeyObj != null && primaryKey != null){
                throw new IllegalArgumentException();
            }

        }
        if(fieldPrimaryKey == null){
            throw new IllegalArgumentException();
        }

    }

    @Override
    public Object[] resolveCreateParameters(T entity) {
        List<Object> objects = new ArrayList<>();
        addParams(objects, entity, fieldAttributeMap);
        return objects.toArray();
    }

    private void addParams(List<Object> objects, T entity, Map<Field, ?> currMapper) {
        objects.addAll(currMapper.keySet()
                .stream()
                .map((var) -> {
                    try {
                        return var.get(entity);
                    } catch (IllegalAccessException e) {
                        return null;
                    }
                })
                .collect(Collectors.toList()));
    }

    @Override
    public Object[] resolveUpdateParameters(T entity) {
        List<Object> objects = new ArrayList<>();
        addParams(objects, entity, fieldAttributeMap);
        addParams(objects, entity, fieldPrimaryKey);
        return objects.toArray();
    }

    @Override
    public Object[] resolvePrimaryKeyParameters(T entity) {
        List<Object> objects = new ArrayList<>();
        addParams(objects, entity, fieldPrimaryKey);
        return objects.toArray();
    }


    private void addParams(List<Object> objects, T entity, Field field) {
        try {
            objects.add(field.get(entity));
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

}
