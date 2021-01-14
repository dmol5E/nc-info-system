package com.nc.unc.repositories.impl;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.nc.unc.model.BaseEntity;
import com.nc.unc.repositories.Repository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;


public abstract class RepositoryEntity<K, V extends BaseEntity<K>> implements Repository<K, V> {


    protected Map<K, V> entities = new HashMap<>();

    @JsonIgnore
    protected Logger logger;

    protected RepositoryEntity(Class<? extends RepositoryEntity<K,V>> className) {
        logger = LoggerFactory.getLogger(className.getName());
    }

    public V getByKey(K key){
        V value;
        if((value = this.entities.get(key)) == null) {
            logger.warn("Object was not found this key: {}", key);
            throw new IllegalArgumentException();
        }
        return value;
    }

    @JsonAnyGetter
    public Map<K, V> getEntities() {
        return this.entities;
    }

    public Collection<V> getValue() { return this.entities.values(); }

    public void update(K key, V entity){
        if(!entities.containsKey(key)) {
            logger.warn("Object was not found this key: {}", key);
            throw new IllegalArgumentException();
        }
        this.entities.replace(key, entity);
    }

    public void put(V newEntity) {
        this.entities.put(newEntity.getKey(), newEntity);
    }

    public int size() {
        return this.entities.size();
    }

    @Override
    public String toString() {
        return "RepositoryEntity{" +
                "entities=" + entities.toString() +
                '}';
    }
}
