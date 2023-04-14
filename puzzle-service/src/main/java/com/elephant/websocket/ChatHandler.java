package com.elephant.websocket;

import cn.hutool.core.collection.ConcurrentHashSet;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.cunw.tid.SnowIdGenerator;
import com.elephant.chat.service.RoomChatService;
import com.elephant.common.model.chat.RoomChat;
import com.elephant.common.model.puzzle.PuzzleRank;
import com.elephant.utils.ObjectUtils;
import com.elephant.websocket.dto.ChatMessage;
import com.elephant.websocket.dto.ChatRoom;
import com.elephant.websocket.dto.User;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.IdGenerator;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class ChatHandler extends TextWebSocketHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(ChatHandler.class);

    private static final Map<String, WebSocketSession>  usersMap= new ConcurrentHashMap<>();
    private Map<String, WebSocketSession> sessions = new ConcurrentHashMap<>();
    private static final Map<User, Set<ChatRoom>> userRooms = new ConcurrentHashMap<>();
    private static final Map<String, Set<User>> roomUsers = new ConcurrentHashMap<>();
    private static final Map<String, ChatRoom> rooms = new ConcurrentHashMap<>();
    private static final Map<String, Set<User>> roomUsersExceptSelf = new ConcurrentHashMap<>();

    static {
        rooms.put("1001", new ChatRoom("1001", "1001"));
        rooms.put("1001", new ChatRoom("1002", "1002"));
    }

    @Autowired
    private RoomChatService roomChatService;

    @Autowired
    private SnowIdGenerator idGenerator;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        LOGGER.info("Connection established - {}", session.getId());
        sessions.put(session.getId(), session);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        LOGGER.info("Received message - {}", message.getPayload());
        String payload = message.getPayload();

        try {
            JSONObject json = JSONObject.parseObject(payload);
            String action = json.getString("action");

            if ("join".equals(action)) {
                String roomId = json.getString("roomId");
                String userId = json.getString("userId");

                User user =new User(userId, userId);
                JoinRoom(roomId, user, session);
            } else if ("leave".equals(action)) {
                String roomId = json.getString("roomId");
                String userId = json.getString("userId");

//                User user = users.get(userId);
//
//                if (user != null) {
//                    LeaveRoom(roomId, user, session);
//                } else {
//                    LOGGER.error("User not found for session {}", session.getId());
//                }
            } else if ("send".equals(action)) {
                String roomId = json.getString("roomId");
                String userId = json.getString("userId");
                String content = json.getString("content");

                User user = new User(userId,userId);

                RoomChat roomChat = new RoomChat();
                roomChat.setId(idGenerator.getNextStr());
                roomChat.setRoomId(roomId);
                roomChat.setUserId(user.getUserId());
                roomChat.setContent(content);
                roomChat.setSendDate(new Date());

                roomChatService.add(roomChat);

                SendMessage(roomId, content, user, session, false, roomChat);
            } else {
                LOGGER.error("Unknown action - {}", action);
            }
        } catch (JSONException e) {
            LOGGER.error("Invalid JSON payload - {}", payload);
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        LOGGER.info("Connection closed - {} : {}", session.getId(), status.getReason());

        sessions.remove(session.getId());
    }

    private void JoinRoom(String roomId, User user, WebSocketSession session) throws IOException {
        ChatRoom room = rooms.get(roomId);
        if (room == null) {
            LOGGER.warn("room no exit");
            return;
        }

        Set<User> users = roomUsers.get(roomId);
        if (users == null) {
            users = new ConcurrentHashSet<>();
            roomUsers.put(roomId, users);
        }

        boolean added = users.add(user);

        if (added) {
            Set<ChatRoom> rooms = userRooms.get(user);
            if (rooms == null) {
                rooms = new ConcurrentHashSet<>();
                userRooms.put(user, rooms);
            }

            rooms.add(room);

            LOGGER.info("User {} joined ChatRoom {}", user.getUserId(), room.getRoomName());
        }
        usersMap.put(user.getUserId(), session);

        // Broadcast join message to all other users in the room
        BroadcastMessage(roomId, user.getUserName() + " joined the room", null, session);

        SendMessageHistory(roomId, "欢迎来到"+roomId, user, session, false);
    }

    private void LeaveRoom(String roomId, User user, WebSocketSession session) throws IOException {
        ChatRoom room = rooms.get(roomId);

        if (room != null) {
            Set<User> users = roomUsers.get(room);
            if (users != null) {
                boolean removed = users.remove(user);

                if (removed) {
                    Set<ChatRoom> rooms = userRooms.get(user);
                    if (rooms != null) {
                        rooms.remove(room);
                    }

                    LOGGER.info("User {} left ChatRoom {}", user.getUserId(), room.getRoomName());
                }

                // Broadcast leave message to all other users in the room
                BroadcastMessage(roomId, user.getUserName() + " left the room", null, session);
            }
        }
    }

    private void SendMessage(String roomId, String content, User user, WebSocketSession session,
                             boolean isSelf, RoomChat roomChat) throws IOException {


        Set<User> users = roomUsers.get(roomId);
        if (users != null) {

            ChatMessage message = new ChatMessage();
            message.setUserId(user.getUserId());
            message.setUsername(user.getUserName());
            message.setContent(content);
            message.setAvatarUrl("https://i.pravatar.cc/150?img=1");
            message.setTimestamp(DateFormatUtils.format(roomChat.getSendDate(), "yyyy-MM-dd HH:mm:ss"));
            message.setIsSelf(isSelf);
            message.setId(roomChat.getId());

            for (User u : users) {
                if (!u.equals(user)) {
                    WebSocketSession s = GetSession(u);
                    if (s != null && s.isOpen()) {
//                        s.sendMessage(new TextMessage(message.toString()));
                        String jsonstring  = ObjectUtils.getJsonStringFromObject(message);
                        s.sendMessage(new TextMessage(jsonstring));
                    }
                }
            }

            LOGGER.info("User {} sent message in ChatRoom {}: {}", user.getUserId(), roomId, content);
        }
    }

    private void SendMessageHistory(String roomId, String content, User user, WebSocketSession s,
                             boolean isSelf) throws IOException {
        ChatMessage message = new ChatMessage();
        message.setUserId(user.getUserId());
        message.setUsername(user.getUserName());
        message.setContent(content);
        message.setAvatarUrl("https://i.pravatar.cc/150?img=1");
        message.setTimestamp(DateFormatUtils.format( new Date(), "yyyy-MM-dd HH:mm:ss"));
        message.setIsSelf(isSelf);
        message.setId(System.currentTimeMillis()+"");

        if (s != null && s.isOpen()) {
//                        s.sendMessage(new TextMessage(message.toString()));
            String jsonstring  = ObjectUtils.getJsonStringFromObject(message);
            s.sendMessage(new TextMessage(jsonstring));
        }
    }

    private WebSocketSession GetSession(User user) {
        for (Map.Entry<String, WebSocketSession> entry : usersMap.entrySet()) {
            if (entry.getKey().equals(user.getUserId())) {
                return entry.getValue();
            }
        }

        return null;
    }

    private void BroadcastMessage(String roomId, String content, User sender, WebSocketSession exclude) throws IOException {
        Set<User> users = roomUsers.get(roomId);
        if (users != null) {
            for (User user : users) {
                if (sender == null || !user.equals(sender)) {
                    WebSocketSession session = GetSession(user);
                    if (session != null && session.isOpen() && session != exclude) {
                        ChatMessage message = new ChatMessage();
                        message.setUserId(user.getUserId());
                        message.setUsername(user.getUserName());
                        message.setContent(content);
                        message.setAvatarUrl("https://i.pravatar.cc/150?img=1");
                        message.setTimestamp(System.currentTimeMillis()+"");
                        message.setIsSelf(false);
                        message.setId(System.currentTimeMillis()+"");


                        String jsonstring  = ObjectUtils.getJsonStringFromObject(message);
                        session.sendMessage(new TextMessage(jsonstring));
                    }
                }
            }
        }
    }

}
