package com.nc.unc.util.serialize.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.nc.unc.path.PathResources;
import com.nc.unc.util.json.JsonHelper;
import com.nc.unc.util.serialize.DataSource;


import java.lang.reflect.InvocationTargetException;
import java.util.List;


public class DataSourceImpl<T> implements DataSource<T> {

    private List<T> objects;

    public DataSourceImpl(List<T> list){
        this.objects = list;
    }

    public DataSourceImpl() {}

    public void setRepos(List<T> repos) { this.objects = repos; }

    @Override
    public void serialize() {
        JsonHelper.jsonToFile(PathResources.path, JsonHelper.toJson(objects));
    }

    public T search(Class<? extends T> type) throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        if(objects == null)
            objects = deSerialize(new TypeReference<>() {});
        return objects.stream()
                .filter(t -> t.getClass() == type).findFirst().orElse(null);
    }

    @Override
    public List<T> deSerialize(TypeReference<List<T>> type) {
        return objects = JsonHelper.fromJson(JsonHelper.fromJsonFile(PathResources.path, String.class), type);
    }

}
