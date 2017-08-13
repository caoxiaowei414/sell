package com.ldlood.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Created by Ldlood on 2017/7/26.
 */
public class JsonUtil {

    public static String toJson(Object object) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setPrettyPrinting();
        Gson gson = gsonBuilder.create();
        return gson.toJson(object);
    }
}
