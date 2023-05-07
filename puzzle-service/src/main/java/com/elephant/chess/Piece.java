package com.elephant.chess;

import com.elephant.utils.ObjectUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;

@Data
public class Piece {
    private String type;
    private String name;

    private String color;

    public Piece(PieceTypeEnum type) {
        this.type = type.getType();
        this.color = type.getColor();
        this.name = type.getName();
    }

    public Piece(String type, String name, String color) {
        this.type = type;
        this.name = name;
        this.color = color;
    }

    public Piece(){

    }

    public String toString() {

        return  ObjectUtils.getJsonStringFromObject(this);
    }

    public static Piece fromString(String str) {
        if(StringUtils.equals("0", str)){
            return null;
        }
        Piece piece = (Piece)ObjectUtils.getObjectFromJsonString(str, Piece.class);
        return piece;
    }
}