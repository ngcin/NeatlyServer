package com.geeyao.management.wslistener;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.annotation.OnEvent;
import com.geeyao.common.log.Log;
import com.geeyao.management.auth.NotNeedLoggedIn;
import com.geeyao.management.bean.BoUser;
import com.geeyao.management.service.BoUserService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LoginEventListener {
    @Log
    private Logger log;
    @Autowired
    private BoUserService boUserService;

    @OnEvent(value = BoServerEventName.BO_LOGIN)
    @NotNeedLoggedIn
    public void onGetUserInfo(SocketIOClient client, AckRequest request, BoUser boUser) {
        BoUser user = boUserService.login(boUser);
        request.sendAckData(user);
    }
}
