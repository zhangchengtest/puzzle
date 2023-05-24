package com.elephant.websocket;

import com.elephant.chess.Chessboard;
import com.elephant.chess.Position;
import com.elephant.chess.SideEnum;
import com.elephant.chess.service.GameService;
import com.elephant.utils.ObjectUtils;
import com.elephant.websocket.dto.ChatRoom;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.*;

@Component
@Slf4j
public class ChessHandler extends TextWebSocketHandler {
    @Autowired
    private GameService gameService;

    private static Map<String, Set<WebSocketSession>> rooms = new HashMap<>();

    static {
        rooms.put("1001", new HashSet<WebSocketSession>());
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) {

        try {
            // 解析WebSocket消息
            JSONObject jsonMessage = new JSONObject(message.getPayload());
            String messageType = jsonMessage.getString("type");
            log.info("handleTextMessage, {}", jsonMessage);
            switch (messageType) {
                case "createRoom":
                    // 创建房间
                    String roomId = jsonMessage.getString("roomId");
                    Set<WebSocketSession> players = new HashSet<>();
                    players.add(session);
                    rooms.put(roomId, players);
                    // 发送房间创建成功消息
                    JSONObject jsonRoomCreated = new JSONObject();
                    jsonRoomCreated.put("type", "roomCreated");
                    jsonRoomCreated.put("roomId", roomId);
                    session.sendMessage(new TextMessage(jsonRoomCreated.toString()));
                    break;
                case "joinRoom":
                    // 加入房间
                    String joinRoomId = jsonMessage.getString("roomId");
                    Set<WebSocketSession> joinPlayers = rooms.get(joinRoomId);
                    if (joinPlayers != null) {
                        joinPlayers.add(session);
                        // 发送房间加入成功消息
                        JSONObject jsonRoomJoined = new JSONObject();
                        jsonRoomJoined.put("type", "roomJoined");
                        jsonRoomJoined.put("roomId", joinRoomId);
                        session.sendMessage(new TextMessage(jsonRoomJoined.toString()));

                        Chessboard chessboard = gameService.getChessboard(joinRoomId);
                        SideEnum sideEnum = gameService.getCurrentSideEnum(joinRoomId);
                        Position position = gameService.readCurrentPositionFromRedis(joinRoomId);
                        // 向所有客户端广播新的棋盘状态
                        broadcastChessboardState(joinRoomId, chessboard, sideEnum.getType(), position);
                    } else {
                        // 发送房间不存在消息
                        JSONObject jsonRoomNotFound = new JSONObject();
                        jsonRoomNotFound.put("type", "roomNotFound");
                        session.sendMessage(new TextMessage(jsonRoomNotFound.toString()));
                    }
                    break;
                case "leaveRoom":
                    // 离开房间
                    String leaveRoomId = jsonMessage.getString("roomId");
                    Set<WebSocketSession> leavePlayers = rooms.get(leaveRoomId);
                    if (leavePlayers != null) {
                        leavePlayers.remove(session);
                        // 发送房间离开成功消息
                        JSONObject jsonRoomLeft = new JSONObject();
                        jsonRoomLeft.put("type", "roomLeft");
                        jsonRoomLeft.put("roomId", leaveRoomId);
                        session.sendMessage(new TextMessage(jsonRoomLeft.toString()));
                    }
                    break;
                case "movePiece":
                    // 移动棋子
                    // 获取房间号
                    String room = getRoomBySession(session);
                    log.info("room {}", room);
                    // 获取起始位置和目标位置
                    JSONObject jsonStart = jsonMessage.getJSONObject("start");
                    JSONObject jsonEnd = jsonMessage.getJSONObject("end");
                    Position start = new Position(jsonStart.getInt("row"), jsonStart.getInt("col"));
                    Position end = new Position(jsonEnd.getInt("row"), jsonEnd.getInt("col"));
                    // 更新棋盘状态
                    gameService.movePiece(room, start, end);
                    Chessboard chessboard = gameService.readChess(room);
                    SideEnum sideEnum = gameService.getCurrentSideEnum(room);
                    if(chessboard != null){
                        // 向所有客户端广播新的棋盘状态
                        broadcastChessboardState(room, chessboard, sideEnum.getType(), end);

                    }else{
                        log.info("boardNotFound");
                        JSONObject jsonRoomNotFound = new JSONObject();
                        jsonRoomNotFound.put("type", "boardNotFound");
                        session.sendMessage(new TextMessage(jsonRoomNotFound.toString()));
                    }
                    break;
                case "restart":
                    restart(session, message);

                    break;
                default:
                    log.error("not found");
            }
        } catch (JSONException | IOException e) {
            log.error("e", e);
        }
    }

    public void restart(WebSocketSession session, TextMessage message){
        // 移动棋子
        // 获取房间号
        String joinRoomId = getRoomBySession(session);
        log.info("room {}", joinRoomId);

        gameService.clearChessboardToRedis(joinRoomId);
        gameService.clearCurrentSideEnumToRedis(joinRoomId);
        gameService.clearCurrentPositionToRedis(joinRoomId);

        Chessboard chessboard = gameService.getChessboard(joinRoomId);
        SideEnum sideEnum = gameService.getCurrentSideEnum(joinRoomId);
        // 向所有客户端广播新的棋盘状态
        broadcastChessboardState(joinRoomId, chessboard, sideEnum.getType(), null);
    }

    private void broadcastChessboardState(String room, Chessboard chessboard, String side, Position end) {
        try {
            // 构造WebSocket消息
            JSONObject jsonMessage = new JSONObject();
            jsonMessage.put("type", "chessboardState");
            jsonMessage.put("chessboard", chessboard.toJSON());
            jsonMessage.put("side", side);
            if(end != null){
                jsonMessage.put("lastRow", end.getRow());
                jsonMessage.put("lastCol", end.getCol());
            }

            log.info("chessboard.toJSON()");
            log.info(chessboard.toJSON());
            log.info("Position end [{}]", ObjectUtils.getJsonStringFromObject(end));
            // 向房间内所有客户端广播消息
            Set<WebSocketSession> players = rooms.get(room);
            if (players != null) {
                for (WebSocketSession session : players) {
                    if (session != null && session.isOpen()) {
                        session.sendMessage(new TextMessage(jsonMessage.toString()));
                    }
                }
            }
        } catch (JSONException | IOException e) {
            // 处理JSON构造或发送异常
        }
    }

    private String getRoomBySession(WebSocketSession session) {
        for (Map.Entry<String, Set<WebSocketSession>> entry : rooms.entrySet()) {
            String roomId = entry.getKey();
            Set<WebSocketSession> players = entry.getValue();
            if (players.contains(session)) {
                return roomId;
            }
        }
        return null;
    }
}
