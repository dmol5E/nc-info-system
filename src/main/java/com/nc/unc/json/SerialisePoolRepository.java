package com.nc.unc.json;

import com.nc.unc.model.BaseEntity;
import com.nc.unc.repositories.Repository;

import java.io.*;
import java.util.List;

public class SerialisePoolRepository implements DataSource<Long, BaseEntity<Long>> {

    private List<Repository<Long, BaseEntity<Long>>> list;


    public SerialisePoolRepository(List<Repository<Long, BaseEntity<Long>>> list) {
        this.list = list;
    }

    @Override
    public void serialize() throws IOException {

    }

    @Override
    public void deSerialize() throws IOException, ClassNotFoundException {

    }

}
