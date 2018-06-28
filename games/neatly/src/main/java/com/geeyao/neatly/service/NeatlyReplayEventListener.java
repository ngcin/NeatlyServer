package com.geeyao.neatly.service;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.annotation.OnEvent;
import com.geeyao.common.bean.*;
import com.geeyao.common.log.Log;
import com.geeyao.common.service.ReplayService;
import com.geeyao.common.service.RoomService;
import com.geeyao.common.service.UserRoomService;
import com.geeyao.common.service.UserService;
import com.geeyao.neatly.bean.MessageBean;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class NeatlyReplayEventListener {
    @Log
    private Logger log;
    @Autowired
    private UserRoomService userRoomService;
    @Autowired
    private ReplayService replayService;
    @Autowired
    private UserService userService;
    @Autowired
    private RoomService roomService;

    @OnEvent(value = NeatlyServerEventName.NEATLY_REPLAY_USER_ROOM)
    public void userRoom(SocketIOClient client, AckRequest request){
        User user = userService.getUser(client);
        if(user != null){
            List<UserRoom> urList = userRoomService.findByUserId(user.getUnionId());
            List<Room> roomList = new ArrayList<Room>();
            for (UserRoom ur : urList){
                Room room = roomService.getRoom(ur.getRoomId());
                if(room.getRoomState() == RoomState.Over){
                    roomList.add(room);
                }
            }
            request.sendAckData(roomList);
        }
    }

    @OnEvent(value = NeatlyServerEventName.NEATLY_REPLAY_ROOM_INFO)
    public void roomInfo(SocketIOClient client, AckRequest request, String roomId){
        Room room = roomService.getRoom(roomId);
        request.sendAckData(room);
    }

    @OnEvent(value = NeatlyServerEventName.NEATLY_REPLAY_ROOM_ROUND)
    public void roomRound(SocketIOClient client, AckRequest request, String roomId){
        List<Replay> replayList = replayService.findByRoomId(roomId);
        request.sendAckData(replayList);
    }

    @OnEvent(value = NeatlyServerEventName.NEATLY_REPLAY_ROUND_RESULT)
    public void roundResult(SocketIOClient client, AckRequest request, MessageBean bean){
        Replay replay = replayService.findByRoomIdAndRound(bean.getRoomId(), bean.getRound());
        request.sendAckData(replay);
    }

}
