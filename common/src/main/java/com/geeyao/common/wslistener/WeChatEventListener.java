package com.geeyao.common.wslistener;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.annotation.OnEvent;
import com.geeyao.common.bean.User;
import com.geeyao.common.bean.WxUserInfo;
import com.geeyao.common.log.Log;
import com.geeyao.common.message.ServerEventName;
import com.geeyao.common.service.UserService;
import com.geeyao.common.service.WeChatService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class WeChatEventListener {
    @Log
    private Logger log;
    @Autowired
    private SocketIOServer socketIOServer;
    @Autowired
    private WeChatService weChatService;
    @Autowired
    private UserService userService;


    @OnEvent(value = ServerEventName.GET_WX_REDIRECT_URL)
    public void onGetWxRedirectUrl(SocketIOClient client, AckRequest request, String origin) {
        String wxUrlForCode = weChatService.getWxUrlForCode(origin);
        request.sendAckData(wxUrlForCode);
    }

    @OnEvent(value = ServerEventName.GET_USER_INFO_BY_CODE)
    public void onGetUserInfo(SocketIOClient client, AckRequest request, String code) {
        WxUserInfo wxUserInfo = weChatService.getWxUserInfo(code);
        User user = userService.saveUser(wxUserInfo);
        request.sendAckData(user);
    }

}
