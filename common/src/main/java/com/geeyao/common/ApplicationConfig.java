package com.geeyao.common;

import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketConfig;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.annotation.SpringAnnotationScanner;
import com.corundumstudio.socketio.store.MemoryStoreFactory;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

@org.springframework.context.annotation.Configuration
public class ApplicationConfig {
    @Bean
    public MongoClient mongoClient(@Value("${mongodb.url}") String mongoDbConString) {
        MongoClientURI connectionString = new MongoClientURI(mongoDbConString);
        return new MongoClient(connectionString);
    }

    @Bean
    public SocketIOServer server() {
        Configuration configuration = new Configuration();
        SocketConfig socketConfig = new SocketConfig();
        socketConfig.setReuseAddress(true);
        configuration.setSocketConfig(socketConfig);
        configuration.setPort(9092);
        configuration.setStoreFactory(new MemoryStoreFactory());
        return new SocketIOServer(configuration);
    }

    @Bean
    public SpringAnnotationScanner springAnnotationScanner(SocketIOServer socketServer) {
        return new SpringAnnotationScanner(socketServer);
    }
}