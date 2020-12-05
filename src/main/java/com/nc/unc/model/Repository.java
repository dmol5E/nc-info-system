package com.nc.unc.model;

import java.io.*;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class Repository<K, V extends  BaseEntity<K>> implements DataSource<K, V> {

    protected Map<K, V> entities = new HashMap<>();

    protected ObjectInputStream read;
    protected ObjectOutputStream write;

    protected Logger logger = null;

    protected Repository(String fileName, Class<Repository<K,V>> className)
            throws IOException {
        try {
            logger = Logger.getLogger(className.getName());
            read = new ObjectInputStream(new FileInputStream(fileName));
            write = new ObjectOutputStream(new FileOutputStream(fileName));
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

    public void updateEntity(K key, V entity){
        this.entities.replace(key, entity);
    }

    public void put(V newEntity) {
        this.entities.put(newEntity.key, newEntity);
    }

    public int sizeEntities() {
        return this.entities.size();
    }

    @Override
    @SuppressWarnings("unchecked")
    public void deSerialize() {
        try {
            this.entities = (Map<K, V>) read.readObject();
            logger.log(Level.INFO, logger.getName() + "deSerialize");
        } catch (IOException| ClassNotFoundException ex) {
            logger.log(Level.WARNING, "Entities wasn't deserialize" , ex);
        }
    }

    @Override
    public void serialize() {
        try {
            write.writeObject(entities);
            logger.log(Level.INFO, logger.getName() + "Serialize");
        } catch (IOException io) {
            logger.log(Level.WARNING, "Entities wasn't deserialize" , io);
        }
    }
}
