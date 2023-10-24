package com.example.practicaJMSjms.Configuraciones;

import com.example.practicaJMSjms.PracticaJmsJmsApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledTasks {
    @Autowired
    private SimpMessagingTemplate webSocket;
    @Scheduled(fixedRate = 2000)
    public void fireGreeting() {
        webSocket.convertAndSend("/topic/graficos", PracticaJmsJmsApplication.getDatos());
    }
}
