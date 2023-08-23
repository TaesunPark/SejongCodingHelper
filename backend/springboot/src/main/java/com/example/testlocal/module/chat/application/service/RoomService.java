package com.example.testlocal.module.chat.application.service;

import com.example.testlocal.module.chat.application.dto.RoomDTO;
import com.example.testlocal.module.chat.domain.Room;
import com.example.testlocal.core.exception.InvalidRoomIdException;
import com.example.testlocal.module.chat.domain.RoomUser;
import com.example.testlocal.module.chat.domain.repository.RoomRepository;
import com.example.testlocal.core.security.jwt.JwtTokenProvider;
import com.example.testlocal.module.chat.domain.repository.RoomUserRepository;
import com.example.testlocal.module.user.application.service.impl.UserService;
import com.example.testlocal.module.user.domain.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service
@RequiredArgsConstructor
public class RoomService {
    private final RoomRepository repository;
    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;
    private final RoomUserRepository roomUserRepository;

    @Transactional
    public Room create(RoomDTO roomDTO, String studentNumber){
        Optional<User> user = userService.findUserByStudentNumber(studentNumber);
        Room room = new Room(roomDTO);
        RoomUser roomUser = new RoomUser();
        roomUser.setRoom(room);
        roomUser.setUser(user.get());
        roomUserRepository.save(roomUser);
        room.getRoomUserList().add(roomUser);
        return repository.save(room);
    }

    public List<Room> findAll(){
        return repository.findAll();
    }

    public Room findById(Long id){
        return repository.findById(id).orElseThrow(()-> new InvalidRoomIdException());
    }

    public List<Room> findAllRoomByStudentId(String refreshToken){
        String studentId = jwtTokenProvider.getUserPk(refreshToken);

        List<Room> result = repository.findAllRoomByStudentId(studentId);

        for(int i = 0; i<result.size(); i++){
            String updateDate = repository.findLastChatTime(result.get(i).getId());

//            if(updateDate == null || updateDate.equals(""))
//                result.get(i).setUpdateDate("2000-12-30 00:00:00");
//            else
//                result.get(i).setUpdateDate(updateDate);
        }
        Collections.sort(result,new SortByDate());

        return result;
    }

    public List<Integer> findUnReadByStudentId(String refreshToken, List<Room> rooms){

        List<Integer> unReadRoomNumbers = new ArrayList<>();
        String studentId = jwtTokenProvider.getUserPk(refreshToken);
        int id = userService.findUserIdByStudentNumber(studentId);

        for(int i = 0; i<rooms.size(); i++){

            try {
                if(repository.findUnReadByStudentId(id, rooms.get(i).getId().intValue()) == 0){

                    System.out.printf(String.valueOf(rooms.get(i).getId().intValue()));
                    System.out.printf(String.valueOf(rooms.size()));
                    unReadRoomNumbers.add(rooms.get(i).getId().intValue());
                }
            } catch (Exception e){

            }

        }

        return unReadRoomNumbers;
    }

    public void updateReadStatus(String refreshToken, int roomId){
        String studentId = jwtTokenProvider.getUserPk(refreshToken);
        int id = userService.findUserIdByStudentNumber(studentId);
        repository.updateReadStatus(id, roomId);
    }

    public void deleteRoom(Long id) {
        repository.deleteById(id);
    }

    static class SortByDate implements Comparator<Room> {
        @Override
        public int compare(Room o1, Room o2) {
            return o2.getUpdateAt(). compareTo(o1.getUpdateAt());
        }
    }
}
