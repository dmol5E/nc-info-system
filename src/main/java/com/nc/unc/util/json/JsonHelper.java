package com.nc.unc.util.json;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.BasicPolymorphicTypeValidator;
import com.fasterxml.jackson.databind.jsontype.PolymorphicTypeValidator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;

public class JsonHelper {

    private static final Logger log = LoggerFactory.getLogger(JsonHelper.class);

    private static final ObjectMapper mapper;

    private static final PolymorphicTypeValidator ptv = BasicPolymorphicTypeValidator.builder().build();

    static {
        mapper = new ObjectMapper();
        mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        mapper.setVisibility(PropertyAccessor.GETTER, JsonAutoDetect.Visibility.NONE);
    }

    public static String toJson(Object o) {
        try {
            return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(o);
        } catch (JsonProcessingException e) {
            log.error("Not Writing to Json", e);
            throw new RuntimeException();
        }
    }

    public static <T> T fromJson(String json, Class<T> type) {
        try {
            return mapper.readValue(json, type);
        } catch (IOException e) {
            log.error("Read from Json");
            throw new RuntimeException(e);
        }
    }

    public static <T> T fromJson(String json, TypeReference<T> type) {
        try {
            return mapper.readValue(json, type);
        } catch (IOException e) {
            log.error("Read from Json");
            throw new RuntimeException(e);
        }
    }

    public static void jsonToFile(String filePath, String json) {
        try {
            mapper.writeValue(new File(filePath), json);
        } catch (IOException io) {
            log.error("Error to write into file {} ", filePath, io);
            throw new RuntimeException();
        }
    }

    public static <T> T fromJsonFile(String filePath, Class<T> type) {
        try {
            return mapper.readValue(new File(filePath), type);
        } catch (IOException io){
            log.error("Error to read into file {} ", filePath, io);
            throw new RuntimeException();
        }
    }

    public static <T> T fromJsonFile(String filePath, TypeReference<T> type) {
        try {
            return mapper.readValue(new File(filePath), type);
        } catch (IOException io){
            log.error("Error to read into file {} ", filePath, io);
            throw new RuntimeException();
        }
    }

    private JsonHelper(){

    }

}
