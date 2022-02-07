package com.bookpie.chatting.repository;

import com.bookpie.chatting.domain.ChattingRoom;
import com.bookpie.chatting.domain.Message;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface MessageRepository extends MongoRepository<ChattingRoom,String> {
    ChattingRoom findChattingRoomByTopic(String topic);
    boolean existsChattingRoomByTopic(String topic);
    List<ChattingRoom> findChattingRoomsBySellerId(Long id);
    List<ChattingRoom> findChattingRoomsByBuyerId(Long id);
}
