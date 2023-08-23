package com.example.testlocal.module.chat.domain.repository;

import com.example.testlocal.module.chat.domain.Room;
import com.example.testlocal.module.chat.domain.RoomUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomUserRepository extends JpaRepository<RoomUser, Long> {
    List<RoomUser> findRoomUsersByUser_StudentNumber(String studentNumber);
}
