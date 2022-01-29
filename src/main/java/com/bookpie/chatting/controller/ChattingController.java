package com.bookpie.chatting.controller;

import com.bookpie.chatting.domain.Message;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@Slf4j
@RequestMapping("/kafka")
@RequiredArgsConstructor
@CrossOrigin
public class ChattingController {

    private final KafkaTemplate<String, Message> kafkaTemplate;

    @PostMapping("/publish")
    public void sendMessage(@RequestBody Message message){
        message.setTimestamp(LocalDateTime.now().toString());
        log.info(message.toString());
        try{
            kafkaTemplate.send("kafka-chat",message).get();
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
