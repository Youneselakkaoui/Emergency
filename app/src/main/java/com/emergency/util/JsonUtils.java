package com.emergency.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

/**
 * Created by elmehdiharabida on 28/05/15.
 */
public class JsonUtils {

    private static ObjectMapper mapper = new ObjectMapper();

    public static String toJson(Object objectIn) {

        try {
            return mapper.writeValueAsString(objectIn);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }


}
