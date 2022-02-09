package com.bookpie.chatting.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Document("room")
public class ChattingRoom {
    @Id
    private String id;
    @Indexed(unique = true)
    private String topic;
    private Long bookId;
    private Long sellerId;
    private Long buyerId;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;
    private List<Message> messages = new ArrayList<>();

    public ChattingRoom(String topic, Long bookId, Long sellerId, Long buyerId) {
        this.topic =topic;
        this.bookId = bookId;
        this.sellerId = sellerId;
        this.buyerId = buyerId;
        this.createDate = LocalDateTime.now();
        this.updateDate = LocalDateTime.now();
    }

    public void addMessage(Message message){
        this.messages.add(message);
        this.updateDate = LocalDateTime.now();
    }
}
