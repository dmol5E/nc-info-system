package com.nc.unc.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.beans.ConstructorProperties;

@NoArgsConstructor
@Getter
@Setter
public class BaseEntity<K> {

    protected K key;

    @ConstructorProperties({"key"})
    public BaseEntity(K key){
        this.key = key;
    }

}
