package com.nc.unc.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.nc.unc.model.BaseEntity;
import com.nc.unc.repositories.Repository;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class SerialisePoolRepository implements DataSource {

    private Logger logger = LogManager.getLogger("SerialisePoolRepository");

    private Gson gson = new GsonBuilder().setPrettyPrinting().create();

    private List<Repository<Long,? extends BaseEntity<Long>>> serialeseList = new ArrayList<>();

    public SerialisePoolRepository(){}


    public SerialisePoolRepository(List<Repository<Long,? extends BaseEntity<Long>>> repositories) {
        this.serialeseList = repositories;
    }

    @Override
    public void serialize()  {
        try {
            FileWriter writer = new FileWriter("file.json");
            for (var it: serialeseList){
                gson.toJson(it, writer);
                writer.flush();
            }
        }catch (IOException e){
            logger.log(Level.ERROR, "JSON writer Exception", e);
        }

    }

    @Override
    public List<Repository<Long,? extends BaseEntity<Long>>> deSerialize() {
        List<Repository<Long,? extends BaseEntity<Long>>> repositories = null;
        try {
            FileReader reader = new FileReader("file.json");

        }catch (IOException e){
            logger.log(Level.ERROR, "JSON reader Exception", e);
        }
        return repositories;
    }

}
