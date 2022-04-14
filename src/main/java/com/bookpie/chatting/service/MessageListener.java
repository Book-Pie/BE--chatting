package com.bookpie.chatting.service;

import com.bookpie.chatting.domain.ChattingRoom;
import com.bookpie.chatting.domain.Message;
import com.bookpie.chatting.repository.MessageRepository;
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
    private final MessageRepository messageRepository;

    @KafkaListener(topics = "kafka-chat",
                    groupId = "bookpie")
    private void listen(Message message){
        log.info("sending via kafka listener..");
        log.info(message.toString());
        ChattingRoom room = messageRepository.findChattingRoomByTopic(message.getTopic());
        room.addMessage(message);
        messageRepository.save(room);
        simpMessagingTemplate.convertAndSend("/topic/"+message.getTopic(),message);
    }


}
