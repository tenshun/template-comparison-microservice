package com.tenshun.tc.service;

import com.tenshun.tc.service.dto.CustomMessage;
import com.tenshun.tc.utils.QueueConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class DummyMessageSender {

    private static final Logger log = LoggerFactory.getLogger(DummyMessageSender.class);

    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public DummyMessageSender(final RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendMessage(CustomMessage message){
        log.info("Sending custom message...:{}", message);
        rabbitTemplate.convertAndSend(QueueConstants.QUEUE, message);
    }

    @Scheduled(fixedDelay = 3000L) //every 5 second
    public void sendMessage() {
        log.info("Sending message...");
        rabbitTemplate.convertAndSend(QueueConstants.QUEUE, UUID.randomUUID().toString());
    }


}