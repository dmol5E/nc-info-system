package com.nc.unc.repositories;

import com.nc.unc.model.BaseEntity;

import java.io.IOException;

public interface DataSource<K, V extends BaseEntity<K>> {

    void serialize() throws IOException;

    void deSerialize() throws IOException, ClassNotFoundException;

}
