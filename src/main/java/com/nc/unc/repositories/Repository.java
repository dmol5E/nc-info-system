package com.nc.unc.repositories;

import com.nc.unc.model.BaseEntity;

import java.io.*;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class Repository<K, V extends BaseEntity<K>> implements DataSource<K, V> {

    protected Map<K, V> entities = new HashMap<>();

    protected ObjectInputStream read;
    protected ObjectOutputStream write;

    protected Logger logger = null;

    protected Repository(String fileName, Class<? extends Repository<K,V>> className) {
        try {
            logger = Logger.getLogger(className.getName());
            write = new ObjectOutputStream(new FileOutputStream(fileName));
            read = new ObjectInputStream(new FileInputStream(fileName));

            logger.log(Level.INFO, className.getName() + " construct");
        } catch (IOException e){
            logger.log(Level.WARNING, className.getName() + " wasn't created " , e);
        }
    }

    public V getByKey(K key){
        V value;
        if((value = this.entities.get(key)) == null) {
            logger.log(Level.WARNING, "Object was not found");
            throw new IllegalArgumentException();
        }
        return value;
    }

    public Collection<V> getAll() {
        return this.entities.values();
    }

    public void update(K key, V entity){
        if(!entities.containsKey(key)) {
            logger.log(Level.WARNING, "Key is already contains" + key);
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

    @Override
    @SuppressWarnings("unchecked")
    public void deSerialize() {
        try {
            this.entities = (Map<K, V>) read.readObject();
            logger.log(Level.INFO, logger.getName() + " deSerialize");
        } catch (IOException| ClassNotFoundException ex) {
            logger.log(Level.WARNING, " Entities wasn't deserialize" , ex);
        }
    }

    @Override
    public void serialize() {
        try {
            write.writeObject(entities);
            logger.log(Level.INFO, logger.getName() + " Serialize");
        } catch (IOException io) {
            logger.log(Level.WARNING, " Entities wasn't deserialize" , io);
        }
    }
}
