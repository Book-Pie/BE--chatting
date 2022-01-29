package com.bookpie.chatting.domain;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Message {
    private String user;
    private String content;
    private Long roomId;
    private String timestamp;
}
