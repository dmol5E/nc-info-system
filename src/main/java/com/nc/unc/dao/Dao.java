package com.nc.unc.dao;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface Dao<K, T> {

    /**
     * select * from ...
     * */
    Map<K, T> getAll();

    /**
     * select * from cond0 and ... and condN
     * */
    Map<K, T> gelAllWhere(String str);

     /**
      *
      * */
     default Optional<T> getById(int id){
         return Optional.ofNullable(
                 gelAllWhere("id=" + id).get(id)
         );
     }

}
