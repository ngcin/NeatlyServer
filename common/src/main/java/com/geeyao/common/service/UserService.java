package com.geeyao.common.service;

import com.corundumstudio.socketio.SocketIOClient;
import com.geeyao.common.bean.User;
import com.geeyao.common.bean.WxUserInfo;
import com.geeyao.common.log.Log;
import com.geeyao.common.repository.UserRepository;
import org.slf4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;

import java.util.List;

@Component
public class UserService {
    @Log
    private Logger log;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SequenceService sequenceService;

    public User getUser(SocketIOClient client) {
        String uid = getUid(client);
        if (StringUtils.hasText(uid)) {
            return getByUnionId(uid);
        }
        return null;
    }

    private String getUid(SocketIOClient client) {
        return client.getHandshakeData().getSingleUrlParam("uid");
    }

    public User getByUnionId(String wxUid) {
        return userRepository.findByUnionId(wxUid);
    }

    public List<User> getByCurrentRoomId(String roomId) {
        return userRepository.findByCurrentRoomId(roomId);
    }

    public User saveUser(WxUserInfo wxUserInfo) {
        if (StringUtils.hasText(wxUserInfo.getUnionId())) {
            User user = getByUnionId(wxUserInfo.getUnionId());
            if (user == null) {
                user = new User();
                BeanUtils.copyProperties(wxUserInfo, user);
                long nextSequence = sequenceService.getNextSequence(User.class.getSimpleName(), 22000);
                user.setNumber(nextSequence);
                userRepository.save(user);
            }
            return user;
        }
        throw new IllegalArgumentException("wxUserInfo里面的unionId不可以为空");
    }

    public void saveUser(User user) {
        userRepository.save(user);
    }

//    public String getWxUid(SocketIOClient client) {
//        try {
//            String header = client.getHandshakeData().getHttpHeaders().get("cookie");
//            String[] pairs = header.split(";");
//            for (String pair : pairs) {
//                String[] nameAndValue = pair.split("=");
//                if ("wx_u_id".equalsIgnoreCase(nameAndValue[0].trim())) {
//                    return nameAndValue[1].trim();
//                }
//            }
//            return null;
//        } catch (Exception e) {
//            log.warn("error while paring cookie", e);
//            return null;
//        }
//    }
}
