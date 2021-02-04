package com.nc.unc.myDao.mapper;


import com.nc.unc.myDao.annotation.Attribute;
import com.nc.unc.myDao.annotation.Enumerated;
import com.nc.unc.myDao.annotation.PrimaryKey;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.RowMapper;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;

import static java.lang.Math.toIntExact;

@Slf4j(topic = "log")
@AllArgsConstructor
public class AbstractMapper<T> implements RowMapper<T> {

    private final Class<T> entityClass;
    private final Field primaryKeyField;
    private final Map<Field, Attribute> fieldAttributeMap;
    private final Map<Field, Attribute> fieldEnumeratedMap;
    @Override
    public T mapRow(ResultSet rs, int i) throws SQLException {
        try {
            return map(rs);
        } catch (Exception exp) {
            log.error("Can't map object {}", entityClass, exp);
            return null;
        }
    }

    private T map(ResultSet resultSet) throws Exception {
        T object = entityClass.getConstructor().newInstance();
        PrimaryKey primaryKey = primaryKeyField.getAnnotation(PrimaryKey.class);
        String dbColumn = primaryKey.value();
        Object attr = castTypes(resultSet.getObject(dbColumn), primaryKeyField.getType());
        primaryKeyField.set(object, attr);

        for (Map.Entry<Field, Attribute> entry : fieldEnumeratedMap.entrySet()){
            Field field = entry.getKey();
            Attribute attribute = entry.getValue();

            dbColumn = attribute.value();
            field.set(object, Enum.valueOf((Class<Enum>) field.getType(),resultSet.getString(dbColumn)));
        }

        for (Map.Entry<Field, Attribute> entry : fieldAttributeMap.entrySet()) {
            Field field = entry.getKey();
            Attribute attribute = entry.getValue();

            dbColumn = attribute.value();
            attr = castTypes(resultSet.getObject(dbColumn), field.getType());
            field.set(object, attr);

        }
        return object;
    }

    private Object castTypes(Object attr, Class<?> fieldType) {
        if (attr == null) {
            return null;
        }

        if (attr instanceof BigDecimal) {
            BigDecimal bd = (BigDecimal) attr;
            if (fieldType.equals(Integer.class)) {
                attr = bd.intValueExact();
            } else if (fieldType.equals(Long.class)) {
                attr = bd.longValueExact();
            }

        } else if (attr instanceof Number) {
            Number bd = (Number) attr;
            if (fieldType.equals(Integer.class)) {
                attr = toIntExact(bd.longValue());
            } else if (fieldType.equals(Long.class)) {
                attr = bd.longValue();
            }

        } else if (attr instanceof Date && fieldType.equals(LocalDate.class)) {
            Date date = (Date) attr;
            attr = date.toLocalDate();

        } else if (attr instanceof Timestamp && fieldType.equals(LocalDateTime.class)) {
            Timestamp timestamp = (Timestamp) attr;
            attr = timestamp.toLocalDateTime();

        } else if (attr instanceof Timestamp && fieldType.equals(LocalDate.class)) {
            Timestamp timestamp = (Timestamp) attr;
            attr = timestamp.toLocalDateTime()
                    .toLocalDate();
        }

        return attr;
    }
}
