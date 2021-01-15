package com.nc.unc.util.serialize;

import com.fasterxml.jackson.core.type.TypeReference;

import java.io.IOException;
import java.util.List;

public interface DataSource<T> {

    void serialize();

    List<T> deSerialize(TypeReference<List<T>> type);
}
