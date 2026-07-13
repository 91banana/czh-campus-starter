package com.campus.websocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
public class WebSocketHeartbeat {

    @Autowired
    private ChatWebSocketHandler chatWebSocketHandler;

    @Scheduled(fixedRate = 30000)
    public void ping() {
        chatWebSocketHandler.sendPingToAll();
    }
}
