package com.nc.unc.util.serialize;

import com.fasterxml.jackson.core.type.TypeReference;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

public interface DataSource<T> {

    void serialize();

    T search(Class<? extends T> type) throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException;

    List<T> deSerialize(TypeReference<List<T>> type);
}
