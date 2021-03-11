package com.nc.unc.dao.template;

import com.nc.unc.dao.annotation.*;
import lombok.Getter;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

public class EntityImpl<T> implements Entity<T> {

    private Class<T> entity;

    @Getter
    private Field fieldPrimaryKey;

    @Getter
    private PrimaryKey primaryKey;

    @Getter
    private Map<Field, Attribute> fieldEnumeratedMap = new HashMap<>();

    @Getter
    private Map<Field, Attribute> createdDateMap = new HashMap<>();

    @Getter
    private Map<Field, Attribute> updatedDate = new HashMap<>();

    @Getter
    private Map<Field, Attribute> fieldAttributeMap = new HashMap<>();

    @Getter
    private List<Class<?>> superClasses = new ArrayList<>();

    public EntityImpl(Class<T> entity){
        this.entity = entity;
        parseSuperClasses();
        fillField();
    }


    private void parseSuperClasses(){
        for (Class<?> superClass = entity; superClass != Object.class; superClass = superClass.getSuperclass()){
            superClasses.add(superClass);
        }
    }


    private void fillField() {
        for (Class<?> superClass : superClasses) {
            Field[] fields = superClass.getDeclaredFields();
            parseFields(fields, superClass);
        }
        if(fieldPrimaryKey == null){
            throw new IllegalArgumentException();
        }
    }

    private void parseFields(Field[] fields, Class<?> entity) {

        for (Field field: fields){
            field.setAccessible(true);

            CreatedDate createdDate = field.getAnnotation(CreatedDate.class);

            if(createdDate != null){

            }

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
            if (primaryKeyObj != null && primaryKey == null ){
                this.primaryKey = primaryKeyObj;
                this.fieldPrimaryKey = field;
            }else if(primaryKeyObj != null && primaryKey != null){
                throw new IllegalArgumentException();
            }

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
