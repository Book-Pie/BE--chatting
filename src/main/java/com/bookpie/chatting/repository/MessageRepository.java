package com.bookpie.chatting.repository;

import com.bookpie.chatting.domain.Message;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface MessageRepository extends MongoRepository<Message,String> {
    List<Message> findAllByTopic(String topic);
}
