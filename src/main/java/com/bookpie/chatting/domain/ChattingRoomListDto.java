package com.bookpie.chatting.domain;

import lombok.Data;

@Data
public class ChattingRoomListDto {
    private String id;
    private String topic;
    private Long bookId;
    private Long sellerId;
    private Long buyerId;

    public ChattingRoomListDto(ChattingRoom room){
        this.id = room.getId();
        this.topic = room.getTopic();
        this.bookId = room.getBookId();
        this.sellerId = room.getSellerId();
        this.buyerId = room.getBuyerId();
    }
}
