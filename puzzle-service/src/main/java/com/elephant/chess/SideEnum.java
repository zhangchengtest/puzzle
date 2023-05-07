package com.elephant.chess;


import java.util.HashMap;
import java.util.Map;

/**
 * 棋子颜色枚举
 */
public enum SideEnum {

    RED("红方", "red"),
    BLACK("黑方", "black");

    private static final Map<String, SideEnum> NAME_ENUM_MAP = new HashMap<>();
    private static final Map<String, SideEnum> TYPE_ENUM_MAP = new HashMap<>();

    static {
        for (SideEnum sideEnum : SideEnum.values()) {
            NAME_ENUM_MAP.put(sideEnum.getName(), sideEnum);
            TYPE_ENUM_MAP.put(sideEnum.getType(), sideEnum);
        }
    }

    private final String name;
    private final String type;

    SideEnum(String name, String type) {
        this.name = name;
        this.type = type;
    }

    public static SideEnum getByName(String name) {
        return NAME_ENUM_MAP.get(name);
    }

    public static SideEnum getByType(String type) {
        return TYPE_ENUM_MAP.get(type);
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }
}
