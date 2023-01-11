package com.example.testlocal.module.user.domain.VO;

import com.example.testlocal.module.user.domain.entity.User;
import lombok.Builder;
import lombok.Getter;

import java.util.Objects;
@Getter
@Builder
public class UserVO {

    private String studentNumber;

    private String password;

    private String name;

    private String email;

    @Override
    public int hashCode() {
        return Objects.hash(studentNumber, password, name, email);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        UserVO userVO = (UserVO) obj;
        return  (userVO.studentNumber == ((UserVO) obj).studentNumber)
                && (userVO.password == ((UserVO) obj).password)
                && (userVO.name == ((UserVO) obj).name)
                && (userVO.email == ((UserVO) obj).email);
    }

}
