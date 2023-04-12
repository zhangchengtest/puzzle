package com.elephant.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ObjectUtils {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static Object getObjectFromJsonString(String jsonString, Class<?> objectClass) {
        try {
            return objectMapper.readValue(jsonString, objectClass);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getJsonStringFromObject(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
