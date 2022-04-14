package com.bookpie.chatting.service;


import com.bookpie.chatting.domain.ChattingRoom;
import com.bookpie.chatting.domain.ChattingRoomListDto;
import com.bookpie.chatting.domain.Message;
import com.bookpie.chatting.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChattingService {

    private final MessageRepository messageRepository;
    private final KafkaTemplate<String,Message> kafkaTemplate;

    public List<ChattingRoomListDto> getRoomsBySeller(Long id){
        List<ChattingRoom> rooms = messageRepository.findChattingRoomsBySellerId(id);
        return rooms.stream().map(ChattingRoomListDto::new).collect(Collectors.toList());
    }

    public List<ChattingRoomListDto> getRoomsByBuyer(Long id){
        List<ChattingRoom> rooms = messageRepository.findChattingRoomsByBuyerId(id);
        return rooms.stream().map(ChattingRoomListDto::new).collect(Collectors.toList());
    }

    @Transactional
    public void createRoom(String topic,Long bookId, Long sellerId, Long buyerId){
        ChattingRoom room = new ChattingRoom(topic,bookId,sellerId,buyerId);
        messageRepository.save(room);
    }

    public boolean sendMessage(String topic, Message message){
        ChattingRoom room = messageRepository.findChattingRoomByTopic(topic);
        log.info(String.valueOf(room.getMessages().size()));
        log.info(room.toString());
        try{
 
            kafkaTemplate.send("kafka-chat", message).get();
            return true;

        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
