package com.nc.unc.repositories;

import com.nc.unc.model.BaseEntity;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Level;

import java.util.HashMap;
import java.util.Map;
public abstract class RepositoryEntity<K, V extends BaseEntity<K>> implements Repository<K, V> {

    protected Map<K, V> entities = new HashMap<>();

    protected Logger logger = null;

    protected RepositoryEntity(Class<? extends RepositoryEntity<K,V>> className) {
        logger = LogManager.getLogger(className.getName());
    }

    public V getByKey(K key){
        V value;
        if((value = this.entities.get(key)) == null) {
            logger.log(Level.WARN, "Object was not found");
            throw new IllegalArgumentException();
        }
        return value;
    }

    public Map<K, V> getEntities() {
        return this.entities;
    }

    public void update(K key, V entity){
        if(!entities.containsKey(key)) {
            logger.log(Level.WARN, "Key is already contains" + key);
            throw new IllegalArgumentException();
        }
        this.entities.replace(key, entity);
    }

    public void put(V newEntity) {
        this.entities.put(newEntity.getKey(), newEntity);
    }

    public int sizeEntities() {
        return this.entities.size();
    }

}
