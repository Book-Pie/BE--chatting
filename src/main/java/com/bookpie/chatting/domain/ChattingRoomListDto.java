package com.bookpie.chatting.domain;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ChattingRoomListDto {
    private String id;
    private String topic;
    private Long bookId;
    private Long sellerId;
    private Long buyerId;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;
    public ChattingRoomListDto(ChattingRoom room){
        this.id = room.getId();
        this.topic = room.getTopic();
        this.bookId = room.getBookId();
        this.sellerId = room.getSellerId();
        this.buyerId = room.getBuyerId();
        this.createDate = room.getCreateDate();
        this.updateDate = room.getUpdateDate();
    }
}
