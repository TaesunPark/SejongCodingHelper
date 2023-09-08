package com.example.testlocal.module.chat.presentation.controller;

import com.example.testlocal.core.security.jwt.JwtTokenProvider;
import com.example.testlocal.module.chat.application.dto.ChatDTO;
import com.example.testlocal.util.Constants;
import com.example.testlocal.module.chat.application.dto.ChatDTO2;
import com.example.testlocal.module.chat.domain.Chat;
import com.example.testlocal.module.chat.application.service.ChatService;
import com.example.testlocal.module.chat.application.service.RoomService;
import com.example.testlocal.module.user.application.service.impl.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;
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
    private final JwtTokenProvider jwtTokenProvider;

    @MessageMapping("/{roomId}") // 여기로 전송되면 메서드 호출 -> WebSocketConfig prefixes 에서 적용
    @SendTo("/room/{roomId}")
    public Chat test(@DestinationVariable Long roomId, ChatDTO chatDTO, @CookieValue(name = "refreshToken", defaultValue = "-1") String refreshToken){
        String studentNumber = jwtTokenProvider.getUserPk(refreshToken);
        long userId = userService.findUserIdByStudentNumber(studentNumber);
        Chat chat = chatService.create(new ChatDTO2(roomId, userId, new Timestamp(System.currentTimeMillis()), chatDTO.getContent(),"",0));
        return chat;
    }

//    @GetMapping("/chat")
//    public List<Chat> all() { return chatService.findAll();}
//
//    @ResponseBody
//    @PostMapping("/chat/create/{studentNumber}")
//    public Chat createChat(@RequestBody ChatDTO2 requestDTO, @PathVariable String studentNumber){
//        Integer id = userService.findUserIdByStudentNumber(studentNumber);
//        requestDTO.setUserId(id.longValue());
//        return chatService.create(requestDTO);
//    }
//
//    @PostMapping("/chat/{id}")
//    public String hello(@RequestBody Map<String, Object> map, @PathVariable Long id) {
//        map = chatService.findById2(id);
//        return map.values().toString();
//    }
//
//    @PostMapping("/chat/roomId/{roomId}")
//    public List<Chat> findByRoomId(@PathVariable Long roomId) {
//        return chatService.findByRoomId(roomId);
//    }
//
//    @DeleteMapping("/chat")
//    public void deleteChat(@PathVariable Long id) { chatService.deleteChat(id);}
//
//    //WebSocketConfig에서 설정한 applicationDestinationPrefixes와
//    // MessageMapping 경로가 병합됨
//    //"/pub/chat/enter"
//    @MessageMapping("/chat/enter")
//    public void enter(ChatDTO2 msg){
//        msg.setMessage(roomService.findById(msg.getRoomId()).getTitle() + " 채팅방에 참여하였습니다.");
//        template.convertAndSend("/sub/chat/room/" + msg.getRoomId().toString(), msg);
//    }
//
//    @PostMapping("/chat/compileMessage")
//    public void compileMessage(@RequestBody ChatDTO2 msg){
//        chatService.create(msg);
//    }
//
//    @MessageMapping("/chat/message")
//    public void message(ChatDTO2 msg){
//        System.out.println(msg.getChatRead() + "   " + msg.getMessage());
//        chatService.create(msg);
//        template.convertAndSend("/sub/chat/room/" + msg.getRoomId().toString(), msg);
//    }
//
//    @MessageMapping("/chat/message2")
//    public void message2(ChatDTO2 msg){
//        template.convertAndSend("/sub/chat/room2/" + msg.getRoomId().toString(), msg);
//    }

}
