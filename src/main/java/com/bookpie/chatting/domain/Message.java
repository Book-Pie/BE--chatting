package com.bookpie.chatting.domain;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document("chatting")
public class Message {

    private String user;
    private String content;
    private String timestamp;
}
