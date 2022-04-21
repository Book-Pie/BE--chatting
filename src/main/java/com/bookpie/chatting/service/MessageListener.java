package com.bookpie.chatting.service;

import com.bookpie.chatting.domain.ChattingRoom;
import com.bookpie.chatting.domain.Message;
import com.bookpie.chatting.repository.MessageRepository;
import com.mongodb.client.result.UpdateResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class MessageListener {

    private final SimpMessagingTemplate simpMessagingTemplate;
    private final MongoTemplate mongoTemplate;
    @KafkaListener(topics = "kafka-chat",
                    groupId = "bookpie")
    private void listen(Message message){
        log.info("sending via kafka listener..");
        log.info(message.toString());

        //ChattingRoom room = messageRepository.findChattingRoomByTopic(message.getTopic());
        //room.addMessage(message);
        //messageRepository.save(room);
        mongoTemplate.updateFirst(
                Query.query(Criteria.where("topic").is(message.getTopic())),
                new Update().push("messages",message), ChattingRoom.class
        );
        simpMessagingTemplate.convertAndSend("/topic/"+message.getTopic(),message);
    }


}
