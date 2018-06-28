package com.geeyao.common.service;

import com.corundumstudio.socketio.SocketIOClient;
import com.geeyao.common.bean.User;
import com.geeyao.common.bean.UserRoom;
import com.geeyao.common.bean.WxUserInfo;
import com.geeyao.common.log.Log;
import com.geeyao.common.repository.UserRepository;
import com.geeyao.common.repository.UserRoomRepository;
import org.slf4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.List;

@Component
public class UserRoomService {
    @Log
    private Logger log;
    @Autowired
    private UserRoomRepository userRoomRepository;

    public UserRoom findByUserIdAndRoomId(String userId, String roomId){
        List<UserRoom> list = userRoomRepository.findByUserIdAndRoomId(userId, roomId);
        if(list.size() > 0){
            return list.get(0);
        }
        return null;
    }

    public void save(UserRoom userRoom){
        UserRoom ur = findByUserIdAndRoomId(userRoom.getUserId(), userRoom.getRoomId());
        if(ur == null){
            userRoomRepository.save(userRoom);
        }
    }

    public void delete(UserRoom userRoom){
        UserRoom ur = findByUserIdAndRoomId(userRoom.getUserId(), userRoom.getRoomId());
        if(ur != null){
            userRoomRepository.delete(userRoom);
        }
    }

    public List<UserRoom> findByUserId(String userId) {
        return userRoomRepository.findByUserId(userId);
    }
}
