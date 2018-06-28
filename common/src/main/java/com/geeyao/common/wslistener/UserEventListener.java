package com.geeyao.common.wslistener;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.annotation.OnEvent;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.geeyao.common.bean.User;
import com.geeyao.common.log.Log;
import com.geeyao.common.message.ServerEventName;
import com.geeyao.common.service.UserService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserEventListener {
    @Log
    private Logger log;
    @Autowired
    private UserService userService;

    @OnEvent(value = ServerEventName.GET_CURRENT_USER)
    public void onGetUserInfo(SocketIOClient client, AckRequest request) {
        User user = userService.getUser(client);
        request.sendAckData(user);
    }
}
