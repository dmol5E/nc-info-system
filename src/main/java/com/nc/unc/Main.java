package com.nc.unc;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;

public class Main  {

    protected static Logger logger = Logger.getLogger(Main.class.getName());

    static Map<Integer, String> map = new HashMap<>();
    static {
        map.put(1, "User1");
        map.put(2, "User2");
        map.put(3, "User3");
    }

    public static void main(String[] args) {
        map.replace(2,"Is0");
        map.forEach((k,v) -> System.out.println(k + ": " + v));
    }
}
