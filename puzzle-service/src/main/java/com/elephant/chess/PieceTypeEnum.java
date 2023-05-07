package com.elephant.chess;

import java.util.HashMap;
import java.util.Map;

/**
 * 棋子类型枚举
 */
public enum PieceTypeEnum {

    KING("將", "king", "black"),
    ROOK("車", "rook", "black"),
    KNIGHT("馬", "knight", "black"),
    CANNON("炮", "cannon", "black"),
    ELEPHANT("象", "bishop", "black"),
    ADVISOR("士", "advisor", "black"),
    PAWN("卒", "pawn", "black"),

    KING_RED("帥", "king", "red"),
    ROOK_RED("車", "rook", "red"),
    KNIGHT_RED("馬", "knight", "red"),
    CANNON_RED("炮", "cannon", "red"),
    ELEPHANT_RED("象", "bishop", "red"),
    ADVISOR_RED("士", "advisor", "red"),
    PAWN_RED("兵", "pawn", "red");

    private static final Map<String, PieceTypeEnum> NAME_ENUM_MAP = new HashMap<>();
    private static final Map<String, PieceTypeEnum> TYPE_ENUM_MAP = new HashMap<>();

    static {
        for (PieceTypeEnum pieceTypeEnum : PieceTypeEnum.values()) {
            NAME_ENUM_MAP.put(pieceTypeEnum.getName(), pieceTypeEnum);
            TYPE_ENUM_MAP.put(pieceTypeEnum.getType(), pieceTypeEnum);
        }
    }

    private final String name;
    private final String type;

    private final String color;

    PieceTypeEnum(String name, String type, String color) {
        this.name = name;
        this.type = type;
        this.color = color;
    }

    public static PieceTypeEnum getByName(String name) {
        return NAME_ENUM_MAP.get(name);
    }

    public static PieceTypeEnum getByType(String type) {
        return TYPE_ENUM_MAP.get(type);
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getColor() {
        return color;
    }
}
