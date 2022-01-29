package com.bookpie.chatting.controller;

import com.bookpie.chatting.domain.Message;
import com.bookpie.chatting.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

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
    public void sendMessage(@RequestBody Message message){
        message.setTimestamp(LocalDateTime.now().toString());
        try{
            kafkaTemplate.send("kafka-chat",message).get();
            messageRepository.save(message);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
    @GetMapping("/history")
    public List<Message> getHistoryMessage(@RequestParam("topic") String topic){
        return messageRepository.findAllByTopic(topic);
    }
}
