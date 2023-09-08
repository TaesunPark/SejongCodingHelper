package com.example.testlocal.module.chat.presentation.controller;

import com.example.testlocal.module.chat.application.service.ChatService;
import com.example.testlocal.module.chat.domain.Room;
import com.example.testlocal.util.Constants;
import com.example.testlocal.module.chat.application.dto.RoomDTO;
import com.example.testlocal.module.chat.application.service.RoomService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import java.util.ArrayList;

@Slf4j
@RestController
@RequiredArgsConstructor
//@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
@CrossOrigin(origins = Constants.URL , allowCredentials = "true")
public class RoomController {

    private final ChatService chatService;
    private final RoomService roomService;

    /**
     * 채팅방 참여하기
     * @param roomId 채팅방 id
     */
//    @GetMapping("/{roomId}")
//    public String joinRoom(@PathVariable(required = false) Long roomId, Model model) {
//        List<Chat> chatList = chatService.findByRoomId(roomId);
//
//        model.addAttribute("roomId", roomId);
//        model.addAttribute("chatList", chatList);
//        return "room";
//    }
//
//    /**
//     * 채팅방 등록
//     * @param form
//     */
//    @PostMapping("/room")
//    public String createRoom(RoomForm form) {
//        roomService.create(new RoomDTO(form.getName()));
//        return "redirect:/roomList";
//    }
//
//
//    /**
//     * 방만들기 폼
//     */
//    @GetMapping("/roomForm")
//    public String roomForm(@CookieValue(name = "studentNumber", required = false) Cookie cookie) {
//
//        if(cookie == null) {
//            //cookieValue 변수에 쿠키 값을 저장한다.
//            String cookieValue = cookie.getValue();
//        }
//        return "roomForm";
//    }


        /**
     * 채팅방 리스트 보기
     */
    @GetMapping("/rooms")
    public ArrayList<RoomDTO> roomList(@CookieValue(name = "studentNumber") Cookie cookie) {
        log.info("룸 정보 가져오기 실행");
        return roomService.findAllRoomByStudentId(cookie.getValue()).getRoomDTOs();
    }

    /**
     * 채팅방 정보 가져오기
     * @param cookie 학번 담은 cookie 정보
     * @param requestDTO 채팅방 제목
     * @return
     */
    @ResponseBody
    @PostMapping("/room")
    public String createRoom(@CookieValue(name = "studentNumber") Cookie cookie, @RequestBody RoomDTO requestDTO){
        roomService.create(requestDTO, cookie.getValue());
        return "success";
    }

//
    @GetMapping("/room/{id}")
    public RoomDTO getRoom(@PathVariable Long id) {
        RoomDTO roomDTO = new RoomDTO(roomService.findById(id).getTitle());
        return roomDTO;
    }
//
//    @DeleteMapping("/room")
//    public void deleteRoom(@PathVariable Long id) { roomService.deleteRoom(id);}
//
//    @PostMapping("/room/readStatus")
//    public void updateRoomReadStatus(@RequestBody Map<String, Integer> map, HttpServletRequest request, @CookieValue(name = "refreshToken", defaultValue = "-1") String refreshToken) {
//        roomService.updateReadStatus(refreshToken, map.get("roomId"));
//    }
//
//    @PostMapping("/room/studentId")
//    public Map<String, Object> findAllRoomByStudentId(@RequestBody Map<String, Object> map, HttpServletRequest request, @CookieValue(name = "refreshToken", defaultValue = "-1") String refreshToken) {
//
//        List<Room> result = roomService.findAllRoomByStudentId(refreshToken);
//        // 생성된 채팅 방이 없을 경우.
//        if(result.isEmpty()){
//            return null;
//        }
//
//        // 채팅 메시지 읽었을 때 제일 마지막 채팅이 unread면 프론트에 방 번호 전송
//        List<Integer> list = roomService.findUnReadByStudentId(refreshToken, result);
//
//        HttpSession session = request.getSession();
//        String roomId = (String)session.getAttribute("roomId");
//
//        // get한 세션이 없을 경우 => 첫 번째 채팅방으로 session set.
//        if(roomId == null){
//            session.setAttribute("roomId",String.valueOf(result.get(0).getId()));
//        }
//
//        //temp
////        if(list.size() ==0)
////            list.add(4);
//
//        // room 값들 list 형식으로 보내줌
//        map.put("room", result);
//
//        // new 해야할 값들 list 형식으로 보내줌
//        map.put("roomIdList", list);
//
//        return map;
//    }
//
//    @PostMapping("/room/roomSessionId")
//    public String getRoomSessionId(HttpServletRequest request){
//        HttpSession session = request.getSession();
//        String roomId = (String)session.getAttribute("roomId");
//        return roomId;
//    }
//
//    @PostMapping("/room/roomSessionId/{roomId}")
//    public void setRoomSessionId(HttpServletRequest request,@PathVariable String roomId){
//        HttpSession session = request.getSession();
//        session.setAttribute("roomId",roomId);
//    }

}
