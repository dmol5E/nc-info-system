package com.nc.unc.dao;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface Dao<K, V> {


    void update(V value, K key);

    Map<K, V> getAll();

    void insert(V t);

    Optional<V> getByKey(K id);

}
