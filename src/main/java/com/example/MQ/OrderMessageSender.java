package com.example.MQ;

import com.example.settelmets.Models.Chaque;
import com.example.settelmets.Models.SettledChaque;
import com.example.settelmets.RTGSLink.RTGSUser;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderMessageSender {
    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;

    @Autowired
    public OrderMessageSender(RabbitTemplate rabbitTemplate, ObjectMapper objectMapper) {
        this.rabbitTemplate = rabbitTemplate;
        this.objectMapper = objectMapper;
    }

    public void sendOrderCheck(Chaque check) {
        this.rabbitTemplate.convertAndSend("CheckQ2", check);
    }
    public void sendOrderCheck(SettledChaque settledChaque) {
        this.rabbitTemplate.convertAndSend("SettledChaqueQ", settledChaque);
    }
    public void sendOrderCheck(RTGSUser rtgsUser) {
        this.rabbitTemplate.convertAndSend("RTGSUserQ", rtgsUser);
    }
}