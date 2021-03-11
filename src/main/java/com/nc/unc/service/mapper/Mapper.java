package com.nc.unc.service.mapper;

public interface Mapper<E, T> {

    E toEntity(T dto);

    T toDto(E entity);
}
