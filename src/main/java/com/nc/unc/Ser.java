package com.nc.unc;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.nc.unc.util.json.JsonHelper;

import java.util.ArrayList;
import java.util.List;

@JsonTypeInfo(use = JsonTypeInfo.Id.NONE, include = JsonTypeInfo.As.EXTERNAL_PROPERTY)
public class Ser<T> {
    private List<T> list = new ArrayList<>();

    public Ser(List<T> list){
        this.list = list;
    }

    public String toJson() {
        return JsonHelper.toJson(this);
    }
}
