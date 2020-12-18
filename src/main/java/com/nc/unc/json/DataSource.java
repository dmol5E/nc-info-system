package com.nc.unc.json;

import com.nc.unc.model.BaseEntity;
import com.nc.unc.repositories.Repository;

import java.io.IOException;
import java.util.List;

public interface DataSource {

    void serialize() throws IOException;

    List<Repository<Long,? extends BaseEntity<Long>>> deSerialize() throws IOException, ClassNotFoundException;

}
