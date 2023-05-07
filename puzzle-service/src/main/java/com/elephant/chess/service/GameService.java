package com.elephant.chess.service;

import com.elephant.chess.Chessboard;
import com.elephant.chess.Piece;
import com.elephant.chess.Position;
import com.elephant.chess.SideEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GameService {
    private final RedisTemplate<String, String> redisTemplate;



    public Chessboard getChessboard(String roomId) {
        return readChessboardFromRedis(roomId);
    }

    public SideEnum getCurrentSideEnum(String roomId) {
        return readCurrentSideEnumFromRedis(roomId);
    }

    public void switchSideEnum(String roomId) {
        SideEnum currentSideEnum = readCurrentSideEnumFromRedis(roomId);
        currentSideEnum = (currentSideEnum == SideEnum.RED) ? SideEnum.BLACK : SideEnum.RED;
        saveCurrentSideEnumToRedis(roomId, currentSideEnum);
    }

    public boolean movePiece(String roomId, Position src, Position dest) {
        Chessboard chessboard = readChessboardFromRedis(roomId);
        SideEnum currentSideEnum = readCurrentSideEnumFromRedis(roomId);

        Piece piece = chessboard.getPiece(src);
        if (piece == null || !piece.getColor().equals(currentSideEnum.getType())) {
            return false;
        }

//        if (!piece.canMove(src, dest, chessboard)) {
//            return false;
//        }

        Piece destPiece = chessboard.getPiece(dest);
        if (destPiece != null) {
            if (destPiece.getColor().equals(currentSideEnum.getType())) {
                return false;
            }
            chessboard.capture(dest);
        }

        chessboard.move(src, dest);
        currentSideEnum = (currentSideEnum == SideEnum.RED) ? SideEnum.BLACK : SideEnum.RED;
        saveCurrentSideEnumToRedis(roomId, currentSideEnum);
        saveChessboardToRedis(roomId, chessboard);
        return true;
    }

    private Chessboard readChessboardFromRedis(String roomId) {
        String chessboardStr = redisTemplate.opsForValue().get("room:" + roomId + ":chessboard");
        if (chessboardStr == null) {
            Chessboard chessboard = new Chessboard();
            SideEnum currentSideEnum = readCurrentSideEnumFromRedis(roomId);
            currentSideEnum = (currentSideEnum == SideEnum.RED) ? SideEnum.BLACK : SideEnum.RED;
            saveCurrentSideEnumToRedis(roomId, currentSideEnum);
            saveChessboardToRedis(roomId, chessboard);
            return chessboard;
        } else {
            return Chessboard.fromString(chessboardStr);
        }
    }

    public Chessboard readChess(String roomId) {
        String chessboardStr = redisTemplate.opsForValue().get("room:" + roomId + ":chessboard");
        if (chessboardStr == null) {
           return null;
        } else {
            return Chessboard.fromString(chessboardStr);
        }
    }

    private SideEnum readCurrentSideEnumFromRedis(String roomId) {
        String currentSideEnumStr = redisTemplate.opsForValue().get("room:" + roomId + ":currentSideEnum");
        if (currentSideEnumStr == null) {
            return SideEnum.RED;
        } else {
            return SideEnum.valueOf(currentSideEnumStr);
        }
    }

    public void saveChessboardToRedis(String roomId, Chessboard chessboard) {
        redisTemplate.opsForValue().set("room:" + roomId + ":chessboard", chessboard.toString());
    }

    public void clearChessboardToRedis(String roomId) {
        redisTemplate.delete("room:" + roomId + ":chessboard");
    }

    public void clearCurrentSideEnumToRedis(String roomId) {
        redisTemplate.delete("room:" + roomId + ":currentSideEnum");
    }

    private void saveCurrentSideEnumToRedis(String roomId, SideEnum currentSideEnum) {
        redisTemplate.opsForValue().set("room:" + roomId + ":currentSideEnum", currentSideEnum.toString());
    }
}
