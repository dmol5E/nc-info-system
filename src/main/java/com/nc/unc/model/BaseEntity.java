package com.nc.unc.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.beans.ConstructorProperties;

public class BaseEntity<K> {

    protected K key;

    @ConstructorProperties({"key"})
    public BaseEntity(K key){
        this.key = key;
    }

    public void setKey(K key) {
        this.key = key;
    }

    public K getKey() { return this.key; }

}
