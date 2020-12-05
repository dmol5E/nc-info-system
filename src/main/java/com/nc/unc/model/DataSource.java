package com.nc.unc.model;

import java.io.IOException;
import java.util.Map;

public interface DataSource<K, V extends BaseEntity<K>> {

    void serialize() throws IOException;

    void deSerialize() throws IOException, ClassNotFoundException;

}
