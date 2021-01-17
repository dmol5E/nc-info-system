package com.nc.unc.repositories.impl;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.nc.unc.model.BaseEntity;
import com.nc.unc.repositories.Repository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.ConstructorProperties;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@JsonIgnoreProperties({"log"})
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type")
@JsonSubTypes({
        @Type(value = AddressRepository.class, name = "AddressRepository"),
        @Type(value = CustomerRepository.class, name = "CustomerRepository"),
        @Type(value = OrderRepository.class, name = "OrderRepository"),
        @Type(value = ProductRepository.class, name = "ProductRepository")
})
public abstract class RepositoryEntity<K, V extends BaseEntity<K>> implements Repository<K, V> {

    private final String type;

    @JsonProperty("entities")
    protected Map<K, V> entities = new HashMap<>();

    protected Logger log;

    protected RepositoryEntity(Class<? extends RepositoryEntity<K,V>> className) {
        type = className.getSimpleName();
        log = LoggerFactory.getLogger(className.getSimpleName());
        log.info("Repository {} created", className.getSimpleName());
    }

    @ConstructorProperties({"type"})
    protected RepositoryEntity(String type){
        this.type = type;
    }

    public V getByKey(K key){
        V value;
        if((value = this.entities.get(key)) == null) {
            log.warn("Object was not found this key: {}", key);
            throw new IllegalArgumentException();
        }
        return value;
    }

    public Collection<V> getValue() { return this.entities.values(); }

    public void update(K key, V entity){
        if(!entities.containsKey(key)) {
            log.warn("Object was not found this key: {}", key);
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
                "\nentities = \n" + entities.entrySet()
                            .stream()
                            .map(Map.Entry::toString)
                            .collect(Collectors.joining("\n")) +
                '}';
    }

    @Override
    public Map<K, V> getEntities() {
        return entities;
    }

    @JsonProperty("entities")
    public void setEntities(Map<K, V> entities) {
        this.entities = entities;
    }

    public String getType() {
        return type;
    }
}
