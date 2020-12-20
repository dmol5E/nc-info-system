package com.nc.unc.repositories;

import java.util.Collection;
import java.util.Map;

public interface Repository<K, V> {
    V getByKey(K key);

    Map<K, V> getEntities();

    void put(V newEntity);

    int sizeEntities();

}
