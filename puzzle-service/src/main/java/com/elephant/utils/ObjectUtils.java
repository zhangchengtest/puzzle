package com.elephant.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.Random;

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

    public static <T> T selectRandomElement(List<T> list) {
        if (list == null || list.isEmpty()) {
            return null;
        }
        Random random = new Random();
        int randomIndex = random.nextInt(list.size());
        return list.get(randomIndex);
    }
}
