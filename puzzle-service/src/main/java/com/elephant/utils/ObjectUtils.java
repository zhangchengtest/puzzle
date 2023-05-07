package com.elephant.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

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

    public static ObjectNode getJsonNodeFromObject(Object object) {
        try {
            JsonNode jsonNode = objectMapper.convertValue(object, JsonNode.class);
            return (ObjectNode) jsonNode;
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
