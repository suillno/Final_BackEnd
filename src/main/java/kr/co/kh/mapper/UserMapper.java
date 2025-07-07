package kr.co.kh.mapper;

import kr.co.kh.model.vo.UserVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserMapper {
    List<UserVO> findAllUsers();

    void updateUserRole(@Param("userId") Long userId, @Param("role") String role);

    void toggleUserStatus(@Param("userId") Long userId);
}


