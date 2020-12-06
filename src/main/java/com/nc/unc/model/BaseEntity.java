package com.nc.unc.model;

import java.io.Serializable;

public class BaseEntity<K> implements Serializable {
    protected K key;

    public BaseEntity(K key){
        this.key = key;
    }

    public K getKey() { return this.key; }

}
