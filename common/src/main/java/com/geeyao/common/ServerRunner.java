package com.geeyao.common;

import com.corundumstudio.socketio.SocketIOServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class ServerRunner implements CommandLineRunner {
    private final SocketIOServer server;

    @Autowired
    public ServerRunner(SocketIOServer server) {
        this.server = server;
    }

    public void run(String... args) throws Exception {
        server.start();
    }

    public void addNameSpace(String namespace) {
        server.addNamespace(namespace);
    }
}
