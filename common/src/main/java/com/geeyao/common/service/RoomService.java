package com.geeyao.common.service;

import com.corundumstudio.socketio.SocketIOClient;
import com.geeyao.common.bean.Room;
import com.geeyao.common.bean.RoomPlayer;
import com.geeyao.common.bean.User;
import com.geeyao.common.log.Log;
import com.geeyao.common.repository.RoomRepository;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Component
public class RoomService {
    @Log
    private Logger log;
    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private UserService userService;

    public <T extends RoomPlayer> Room getRoom(String roomId) {
        return roomRepository.findOne(roomId);
    }

    /*
        调用这个方法的人，必须处理OptimisticLockingFailureException，这个异常出多个线程同时读取room对象
        如果2个线程同时读取了Room对象，然后前一个线程更新了Room对象，后一个线程再尝试更新时，就会抛异常
     */
    public void saveRoom(Room room) {
        roomRepository.save(room);
    }

    public void removeRoom(String roomId) {
        roomRepository.delete(roomId);
    }

    public <T extends RoomPlayer> T getRoomPlayer(SocketIOClient client) {
        User user = userService.getUser(client);
        if(StringUtils.isEmpty(user.getCurrentRoomId())){
            return null;
        }
        Room<T> room = getRoom(user.getCurrentRoomId());
        if(room == null){
            return null;
        }
        return room.getPlayer(user.getUnionId());
    }

    public Room findByRoomNum(long roomNum){
        return roomRepository.findByRoomNum(roomNum);
    }

}
