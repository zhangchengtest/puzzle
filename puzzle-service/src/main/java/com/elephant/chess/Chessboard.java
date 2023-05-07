package com.elephant.chess;

import com.elephant.utils.ObjectUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.IOException;

public class Chessboard {

    private static final int ROWS = 10;
    private static final int COLS = 9;
    private Piece[][] pieces;

    public Chessboard() {
        initBoard();
    }

    public Piece getPiece(Position pos) {
        if (!isValidPosition(pos)) {
            return null;
        }
        return pieces[pos.getRow()][pos.getCol()];
    }

    public void move(Position src, Position dest) {
        pieces[dest.getRow()][dest.getCol()] = pieces[src.getRow()][src.getCol()];
        pieces[src.getRow()][src.getCol()] = null;
    }

    public void capture(Position pos) {
        pieces[pos.getRow()][pos.getCol()] = null;
    }

    public String toString() {
        try {
            return toJSON();
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static Chessboard fromString(String str){
        try {
            return fromJSON(str);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void initBoard() {
        pieces = new Piece[10][9];
        // 初始化红方棋子
        pieces[0][0] = new Piece(PieceTypeEnum.ROOK_RED);
        pieces[0][1] = new Piece(PieceTypeEnum.KNIGHT_RED);
        pieces[0][2] = new Piece(PieceTypeEnum.ELEPHANT_RED);
        pieces[0][3] = new Piece(PieceTypeEnum.ADVISOR_RED);
        pieces[0][4] = new Piece(PieceTypeEnum.KING_RED);
        pieces[0][5] = new Piece(PieceTypeEnum.ADVISOR_RED);
        pieces[0][6] = new Piece(PieceTypeEnum.ELEPHANT_RED);
        pieces[0][7] = new Piece(PieceTypeEnum.KNIGHT_RED);
        pieces[0][8] = new Piece(PieceTypeEnum.ROOK_RED);
        pieces[2][1] = new Piece(PieceTypeEnum.CANNON_RED);
        pieces[2][7] = new Piece(PieceTypeEnum.CANNON_RED);
        pieces[3][0] = new Piece(PieceTypeEnum.PAWN_RED);
        pieces[3][2] = new Piece(PieceTypeEnum.PAWN_RED);
        pieces[3][4] = new Piece(PieceTypeEnum.PAWN_RED);
        pieces[3][6] = new Piece(PieceTypeEnum.PAWN_RED);
        pieces[3][8] = new Piece(PieceTypeEnum.PAWN_RED);
        // 初始化黑方棋子
        pieces[9][0] = new Piece(PieceTypeEnum.ROOK);
        pieces[9][1] = new Piece(PieceTypeEnum.KNIGHT);
        pieces[9][2] = new Piece(PieceTypeEnum.ELEPHANT);
        pieces[9][3] = new Piece(PieceTypeEnum.ADVISOR);
        pieces[9][4] = new Piece(PieceTypeEnum.KING);
        pieces[9][5] = new Piece(PieceTypeEnum.ADVISOR);
        pieces[9][6] = new Piece(PieceTypeEnum.ELEPHANT);
        pieces[9][7] = new Piece(PieceTypeEnum.KNIGHT);
        pieces[9][8] = new Piece(PieceTypeEnum.ROOK);
        pieces[7][1] = new Piece(PieceTypeEnum.CANNON);
        pieces[7][7] = new Piece(PieceTypeEnum.CANNON);
        pieces[6][0] = new Piece(PieceTypeEnum.PAWN);
        pieces[6][2] = new Piece(PieceTypeEnum.PAWN);
        pieces[6][4] = new Piece(PieceTypeEnum.PAWN);
        pieces[6][6] = new Piece(PieceTypeEnum.PAWN);
        pieces[6][8] = new Piece(PieceTypeEnum.PAWN);
    }

    private boolean isValidPosition(Position pos) {
        int row = pos.getRow();
        int col = pos.getCol();
        return row >= 0 && row < 10 && col >= 0 && col < 9;
    }

    public String toJSON() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        ArrayNode rootNode = mapper.createArrayNode();
        for (int row = 0; row < ROWS; row++) {
            ArrayNode rowNode = mapper.createArrayNode();
            for (int col = 0; col < COLS; col++) {
                Piece piece = pieces[row][col];
                if (piece == null) {
                    rowNode.addNull();
                } else {
                    ObjectNode objNode1 = ObjectUtils.getJsonNodeFromObject(piece);
                    rowNode.add(objNode1);
                }
            }
            rootNode.add(rowNode);
        }
        return mapper.writeValueAsString(rootNode);
    }

    public static Chessboard fromJSON(String json) throws IOException {
        Chessboard chessboard = new Chessboard();
        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = mapper.readTree(json);
        for (int row = 0; row < ROWS; row++) {
            JsonNode rowNode = rootNode.get(row);
            for (int col = 0; col < COLS; col++) {
                JsonNode pieceNode = rowNode.get(col);
                if (pieceNode.isNull()) {
                    chessboard.pieces[row][col] = null;
                } else {
                    Piece piece =  (Piece)ObjectUtils.getObjectFromJsonString(pieceNode.toString(), Piece.class);
                    chessboard.pieces[row][col] = piece;
                }
            }
        }
        return chessboard;
    }
}
