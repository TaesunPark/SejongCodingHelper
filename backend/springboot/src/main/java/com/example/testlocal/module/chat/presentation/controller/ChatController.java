package com.example.testlocal.module.chat.presentation.controller;

import com.example.testlocal.config.Constants;
import com.example.testlocal.domain.dto.ChatDTO2;
import com.example.testlocal.domain.entity.Chat;
import com.example.testlocal.module.chat.application.service.ChatService;
import com.example.testlocal.module.chat.application.service.RoomService;
import com.example.testlocal.module.user.application.service.impl.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
//@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
@CrossOrigin(origins = Constants.URL , allowCredentials = "true")
public class ChatController {

    private final SimpMessagingTemplate template;
    private final RoomService roomService;
    private final ChatService chatService;
    private final UserService userService;

    @GetMapping("/chat")
    public List<Chat> all() { return chatService.findAll();}

    @ResponseBody
    @PostMapping("/chat/create/{studentNumber}")
    public Chat createChat(@RequestBody ChatDTO2 requestDTO, @PathVariable String studentNumber){
        Integer id = userService.findUserIdByStudentNumber(studentNumber);
        requestDTO.setUserId(id.longValue());
        return chatService.create(requestDTO);
    }

    @PostMapping("/chat/{id}")
    public String hello(@RequestBody Map<String, Object> map, @PathVariable Long id) {
        map = chatService.findById2(id);
        return map.values().toString();
    }

    @PostMapping("/chat/roomId/{roomId}")
    public List<Chat> findByRoomId(@PathVariable Long roomId) {
        return chatService.findByRoomId(roomId);
    }

    @DeleteMapping("/chat")
    public void deleteChat(@PathVariable Long id) { chatService.deleteChat(id);}

    //WebSocketConfig?????? ????????? applicationDestinationPrefixes???
    // MessageMapping ????????? ?????????
    //"/pub/chat/enter"
    @MessageMapping("/chat/enter")
    public void enter(ChatDTO2 msg){
        msg.setMessage(roomService.findById(msg.getRoomId()).getTitle() + " ???????????? ?????????????????????.");
        template.convertAndSend("/sub/chat/room/" + msg.getRoomId().toString(), msg);
    }

    @PostMapping("/chat/compileMessage")
    public void compileMessage(@RequestBody ChatDTO2 msg){
        chatService.create(msg);
    }

    @MessageMapping("/chat/message")
    public void message(ChatDTO2 msg){
        System.out.println(msg.getChatRead() + "   " + msg.getMessage());
        chatService.create(msg);

        template.convertAndSend("/sub/chat/room/" + msg.getRoomId().toString(), msg);
    }

    @MessageMapping("/chat/message2")
    public void message2(ChatDTO2 msg){

        template.convertAndSend("/sub/chat/room2/" + msg.getRoomId().toString(), msg);
    }

}
