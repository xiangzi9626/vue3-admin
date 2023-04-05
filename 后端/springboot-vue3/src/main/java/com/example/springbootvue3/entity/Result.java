package com.example.springbootvue3.entity;

import java.util.HashMap;
import java.util.Map;

public class Result {
    public static Map<String,Object> res(Object data) {
        Map<String,Object> map = new HashMap<>();
        map.put("code",200);
        map.put("msg","");
        map.put("data",data);
        return map;
    }
    public static Map<String,Object> success(String msg) {
        Map<String,Object> map = new HashMap<>();
        map.put("code",200);
        map.put("msg",msg);
        map.put("data","");
        return map;
    }
    public static Map<String,Object> success(String msg,Object data) {
        Map<String,Object> map = new HashMap<>();
        map.put("code",200);
        map.put("msg",msg);
        map.put("data",data);
        return map;
    }
    public static Map<String,Object> error(String msg) {
        Map<String,Object> map = new HashMap<>();
        map.put("code",500);
        map.put("msg",msg);
        map.put("data","");
        return map;
    }
}
