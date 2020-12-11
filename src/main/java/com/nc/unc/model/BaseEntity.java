package com.nc.unc.model;

public class BaseEntity<K> {
    protected K key;

    public BaseEntity(K key){
        this.key = key;
    }

    public K getKey() { return this.key; }

}
