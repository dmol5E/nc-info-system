package com.nc.unc.util.serialize.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.nc.unc.path.PathResources;
import com.nc.unc.util.json.JsonHelper;
import com.nc.unc.util.serialize.DataSource;


import java.util.List;
import java.util.stream.Collectors;


public class DataSourceImpl<T> implements DataSource<T> {

    private List<T> repos;

    public DataSourceImpl(List<T> list){
        this.repos = list;
    }

    @Override
    public void serialize() {
        JsonHelper.jsonToFile(PathResources.path, JsonHelper.toJson(repos));
    }

    public List<T> search(Class<? extends T> type){
        if(repos == null)
            deSerialize(new TypeReference<>() {});
        return repos.stream()
                .filter(t -> t.getClass() == type)
                .collect(Collectors.toList());
    }

    @Override
    public List<T> deSerialize(TypeReference<List<T>> type) {
        return repos = JsonHelper.fromJson(JsonHelper.fromJsonFile(PathResources.path, String.class), type);
    }

}
