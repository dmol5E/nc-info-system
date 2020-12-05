package com.nc.unc.model;

import java.io.Serializable;

public class BaseEntity<K> implements Serializable {
    public K key;

    public K getKey() { return this.key; }

}
