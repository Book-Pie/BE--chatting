package com.bookpie.chatting.controller;

import com.bookpie.chatting.domain.ChattingRoom;
import com.bookpie.chatting.domain.Message;
import com.bookpie.chatting.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/kafka")
@RequiredArgsConstructor
@CrossOrigin
public class ChattingController {

    private final KafkaTemplate<String, Message> kafkaTemplate;
    private final MessageRepository messageRepository;

    @PostMapping("/publish")
    public void sendMessage(@RequestBody Message message,
                            @RequestParam("topic") String topic,
                            @RequestParam(value = "bookId",required = false) Long bookId,
                            @RequestParam(value = "sellerId",required = false) Long sellerId,
                            @RequestParam(value = "buyerId",required = false) Long buyerId){
        message.setTimestamp(LocalDateTime.now().toString());

        if(!messageRepository.existsChattingRoomByTopic(topic)){
            if(bookId == null | sellerId == null | buyerId == null) throw new IllegalArgumentException("채팅방 생성에 필요한 정보가 부족합니다.");
            ChattingRoom room = new ChattingRoom(topic,bookId,sellerId,buyerId);
            messageRepository.save(room);
        }
        ChattingRoom room = messageRepository.findChattingRoomByTopic(topic);
        log.info(room.toString());
        try{
            room.addMessage(message);
            messageRepository.save(room);
            kafkaTemplate.send(topic,message).get();
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/history")
    public ChattingRoom getChattingRoom(@RequestParam("topic") String topic){
        return messageRepository.findChattingRoomByTopic(topic);
    }
}
