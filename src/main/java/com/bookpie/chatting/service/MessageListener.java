package com.bookpie.chatting.service;

import com.bookpie.chatting.domain.Message;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class MessageListener {

    private final SimpMessagingTemplate simpMessagingTemplate;

    /*
    @KafkaListener(topics = "kafka-chat",
                    groupId = "bookpie")
    private void listen(Message message){
        log.info("sending via kafka listener..");
        log.info(message.toString());
        simpMessagingTemplate.convertAndSend(message.getTopic(),message);
    }

     */
}
