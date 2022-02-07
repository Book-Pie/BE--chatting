package com.bookpie.chatting.controller;

import com.bookpie.chatting.domain.ChattingRoom;
import com.bookpie.chatting.domain.Message;
import com.bookpie.chatting.repository.MessageRepository;
import com.bookpie.chatting.service.ChattingService;
import com.bookpie.chatting.utils.ApiUtil;
import com.bookpie.chatting.utils.ApiUtil.ApiResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.xml.transform.OutputKeys;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.List;

import static com.bookpie.chatting.utils.ApiUtil.success;

@RestController
@Slf4j
@RequestMapping("/kafka")
@RequiredArgsConstructor
@CrossOrigin
public class ChattingController {

    private final KafkaTemplate<String, Message> kafkaTemplate;
    private final MessageRepository messageRepository;
    private final ChattingService chattingService;

    @PostMapping("/publish")
    public ResponseEntity sendMessage(@RequestBody Message message,
                            @RequestParam(value = "bookId",required = false) Long bookId,
                            @RequestParam(value = "sellerId",required = false) Long sellerId,
                            @RequestParam(value = "buyerId",required = false) Long buyerId){
        message.setTimestamp(LocalDateTime.now().toString());
        String topic = message.getTopic();
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
            kafkaTemplate.send("kafka-chat",message).get();
            return new ResponseEntity(success(true),HttpStatus.OK);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/history")
    public ChattingRoom getChattingRoom(@RequestParam("topic") String topic){
        return messageRepository.findChattingRoomByTopic(topic);
    }

    @GetMapping("/list/seller/{id}")
    public ResponseEntity<ApiResult> getRoomListBySellerId(@PathVariable("id") Long id){
        return new ResponseEntity<>(success(chattingService.getRoomsBySeller(id)), HttpStatus.OK);
    }

    @GetMapping("/list/buyer/{id}")
    public ResponseEntity<ApiResult> getRoomListByBuyerId(@PathVariable("id") Long id){
        return new ResponseEntity<>(success(chattingService.getRoomsByBuyer(id)),HttpStatus.OK);
    }

}
