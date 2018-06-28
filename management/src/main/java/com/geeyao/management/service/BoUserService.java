package com.geeyao.management.service;

import com.corundumstudio.socketio.SocketIOClient;
import com.geeyao.common.log.Log;
import com.geeyao.management.bean.BoUser;
import com.geeyao.management.repository.BoUserRepository;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.UUID;

@Component
public class BoUserService {
    @Log
    private Logger log;
    @Autowired
    private BoUserRepository boUserRepository;

    public BoUser login(BoUser form) {
        if (StringUtils.hasText(form.getCsid())) {
            BoUser boUser = boUserRepository.findByCsid(form.getCsid());
            return boUser;
        } else {
            BoUser boUser = boUserRepository.findByUserNameAndPassword(form.getUserName(), form.getPassword());
            if (boUser == null) {
                if ("admin".equalsIgnoreCase(form.getUserName())) {
                    Iterable<BoUser> boUsers = boUserRepository.findAll();
                    if (!boUsers.iterator().hasNext()) {
                        //当数据库没有用户时，并且登录名是admin时，则默认当前登录的用户是管理员
                        return form;
                    }
                }
            } else {
                String csid = UUID.randomUUID().toString();
                boUser.setCsid(csid);
                boUserRepository.save(boUser);
            }
            return boUser;
        }
    }

    public BoUser currentBoUser(SocketIOClient client) {
        String csid = client.getHandshakeData().getSingleUrlParam("csid");
        return boUserRepository.findByCsid(csid);
    }

}
