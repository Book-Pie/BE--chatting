package com.bookpie.chatting.service;


import com.bookpie.chatting.domain.ChattingRoom;
import com.bookpie.chatting.domain.ChattingRoomListDto;
import com.bookpie.chatting.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChattingService {

    private final MessageRepository messageRepository;

    public List<ChattingRoomListDto> getRoomsBySeller(Long id){
        List<ChattingRoom> rooms = messageRepository.findChattingRoomsBySellerId(id);
        return rooms.stream().map(ChattingRoomListDto::new).collect(Collectors.toList());
    }

    public List<ChattingRoomListDto> getRoomsByBuyer(Long id){
        List<ChattingRoom> rooms = messageRepository.findChattingRoomsByBuyerId(id);
        return rooms.stream().map(ChattingRoomListDto::new).collect(Collectors.toList());
    }

}
