package com.geeyao.common.wslistener;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.BroadcastOperations;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.annotation.OnConnect;
import com.corundumstudio.socketio.annotation.OnDisconnect;
import com.corundumstudio.socketio.annotation.OnEvent;
import com.geeyao.common.annotation.Retry;
import com.geeyao.common.bean.Room;
import com.geeyao.common.bean.RoomPlayer;
import com.geeyao.common.bean.User;
import com.geeyao.common.log.Log;
import com.geeyao.common.message.ClientEventName;
import com.geeyao.common.message.ServerEventName;
import com.geeyao.common.service.RoomService;
import com.geeyao.common.service.UserService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Component
public class RoomEventListener{
    @Log
    private Logger log;
    @Autowired
    private UserService userService;
    @Autowired
    private RoomService roomService;

    void updateRoomInfoToAllPlayers(SocketIOClient client, Room room) {
        client.getNamespace().getBroadcastOperations().sendEvent(ClientEventName.ROOM_UPDATED, room);
    }

    @OnConnect
    @Retry(times = 10, on = org.springframework.dao.OptimisticLockingFailureException.class)
    public void connect(SocketIOClient client) {
        User user = userService.getUser(client);
        if(user != null){
            String clientScene = client.getHandshakeData().getSingleUrlParam("scene");
            if(StringUtils.hasText(clientScene) && clientScene.equals("game")){
                if (user != null) {
                    user.setClientSessionId(client.getSessionId().toString());
                    userService.saveUser(user);
                    if(StringUtils.hasText(user.getCurrentRoomId())){
                        Room<RoomPlayer> room = roomService.getRoom(user.getCurrentRoomId());
                        String userMD5Uid = DigestUtils.md5DigestAsHex(user.getUnionId().getBytes());
                        if(room != null){
                            RoomPlayer player = room.getPlayer(userMD5Uid);
                            player.setClientSessionId(client.getSessionId().toString());
                            player.setOnline(true);
                            roomService.saveRoom(room);
                            sendRoomEvent(ClientEventName.PLAYER_ONLINE, room, client, player.getPlayerMD5Uid());
                        }
                        client.joinRoom(user.getCurrentRoomId());
                    }
                }
            }
        }
        log.info("connect from default namespace: " + client.getSessionId().toString());
    }

    @OnDisconnect
    @Retry(times = 10, on = org.springframework.dao.OptimisticLockingFailureException.class)
    public void disconnect(SocketIOClient client){
        User user = userService.getUser(client);
        String clientScene = client.getHandshakeData().getSingleUrlParam("scene");
        if(StringUtils.hasText(clientScene) && clientScene.equals("game")){
            if (user != null) {
                user.setClientSessionId("");
                userService.saveUser(user);
                if(StringUtils.hasText(user.getCurrentRoomId())){
                    Room<RoomPlayer> room = roomService.getRoom(user.getCurrentRoomId());
                    String userMD5Uid = DigestUtils.md5DigestAsHex(user.getUnionId().getBytes());
                    if(room != null){
                        RoomPlayer player = room.getPlayer(userMD5Uid);
                        player.setClientSessionId("");
                        player.setOnline(false);
                        roomService.saveRoom(room);
                        sendRoomEvent(ClientEventName.PLAYER_OFFLINE, room, client, player.getPlayerMD5Uid());
                    }
                    client.leaveRoom(user.getCurrentRoomId());
                }
            }
        }
        log.info("disconnect：" + client.getSessionId().toString());
    }

    @OnEvent(value = ServerEventName.REMOVE_ROOM)
    public void removeRoom(SocketIOClient client, AckRequest request, Object data) {
        String sessionId = client.getSessionId().toString();
        RoomPlayer roomPlayer = roomService.getRoomPlayer(client);
        String roomId = roomPlayer.getRoomId();
        Room room = roomService.getRoom(roomId);
        boolean dispose = dissolve(room, sessionId);
        User user = userService.getUser(client);
        user.setCurrentRoomId(null);
        userService.saveUser(user);
        if (dispose) {
            roomService.removeRoom(roomId);
            dispose(room);
        }
        client.getNamespace().getBroadcastOperations().sendEvent(ClientEventName.ROOM_REMOVED, room);
    }

//    @OnEvent(value = ServerEventName.LEAVE_ROOM)
//    public void leaveRoom(SocketIOClient client, AckRequest request) {
//        RoomPlayer roomPlayer = roomService.getRoomPlayer(client);
//        if(roomPlayer != null){
//            Room room = roomService.getRoom(roomPlayer.getRoomId());
//            String gamePlayerId = roomPlayer.getPlayerMD5Uid();
//            playerLeaves(room, gamePlayerId);
//            roomService.saveRoom(room);
//        }
//        User user = userService.getUser(client);
//        user.setCurrentRoomId(null);
//        userService.saveUser(user);
//        request.sendAckData(true);
//        client.getNamespace().getBroadcastOperations().sendEvent(ClientEventName.PLAYER_LEFT, gamePlayerId);
//    }

    public RoomPlayer getGamePlayer(Room room, String gamePlayerId) {
        List<RoomPlayer> roomPlayerList = room.getPlayers();
        for (RoomPlayer roomPlayer : roomPlayerList) {
            if (roomPlayer.is(gamePlayerId)) {
                return roomPlayer;
            }
        }
        return null;
    }

    public void playerLeaves(Room room, String gamePlayerId) {
        RoomPlayer roomPlayer = getGamePlayer(room, gamePlayerId);
        if (roomPlayer != null) {
            roomPlayer.setRoomId(null);
            room.getPlayers().remove(roomPlayer);
        }
    }

    public boolean dissolve(Room<RoomPlayer> room, String sessionId) {
        getGamePlayer(room, sessionId).setDissolve(true);
        if (room.isForceDissolve()) {
            return room.isForceDissolve();
        } else {
            boolean isAllAgree = true;
            for (RoomPlayer roomPlayer : room.getPlayers()) {
                if (!roomPlayer.isDissolve()) {
                    isAllAgree = false;
                    break;
                }
            }
            return isAllAgree;
        }
    }

    public void dispose(Room<RoomPlayer> room) {
        for (RoomPlayer user : room.getPlayers()) {
            user.setRoomId(null);
        }
        room.getPlayers().clear();
    }

    public void sendRoomEvent(String eventName, Room room, SocketIOClient client, Object... data){
        List<RoomPlayer> players = room.getPlayers();
        BroadcastOperations roomOperations = client.getNamespace().getRoomOperations(room.getRoomId());
        for (RoomPlayer roomPlayer : players) {
            SocketIOClient targetClient = findTargetClient(roomOperations, roomPlayer);
            if (targetClient != null) {
                targetClient.sendEvent(eventName, data);
            }
        }
    }

    public void sendRoomEvent(String eventName, Room room, SocketIOClient client, SocketIOClient excludedClient, Object... data){
        List<RoomPlayer> players = room.getPlayers();
        BroadcastOperations roomOperations = client.getNamespace().getRoomOperations(room.getRoomId());
        for (RoomPlayer roomPlayer : players) {
            SocketIOClient targetClient = findTargetClient(roomOperations, roomPlayer);
            if (targetClient != null && (excludedClient == null || !targetClient.getSessionId().equals(excludedClient.getSessionId()))) {
                targetClient.sendEvent(eventName, data);
            }
        }
    }

    private SocketIOClient findTargetClient(BroadcastOperations operations, RoomPlayer roomPlayer) {
        Collection<SocketIOClient> clients = operations.getClients();
        for (SocketIOClient client : clients) {
            if (Objects.equals(client.getSessionId().toString(), roomPlayer.getClientSessionId())) {
                return client;
            }
        }
        return null;
    }
}
