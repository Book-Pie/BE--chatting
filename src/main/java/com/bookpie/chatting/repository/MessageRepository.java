package com.bookpie.chatting.repository;

import com.bookpie.chatting.domain.ChattingRoom;
import com.bookpie.chatting.domain.Message;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface MessageRepository extends MongoRepository<ChattingRoom,String> {
    public List<ChattingRoom> findChattingRoomBySellerIdOrBuyerId(Long id);
    public ChattingRoom findChattingRoomByTopic(String topic);
    public boolean existsChattingRoomByTopic(String topic);
}
