package kr.co.kh.mapper;

import kr.co.kh.model.User;
import kr.co.kh.model.vo.UserVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserMapper {

    // 회원가입 사용자 삽입
    void insertUser(User user);

    // 사용자 전체 목록 조회 (VO 반환)
    List<UserVO> findAllUsers();

    // ROLE_NAME → ROLE_ID 조회
    Long findRoleIdByName(@Param("roleName") String roleName);

    // 현재 사용자의 ROLE_ID 조회
    Long findCurrentRoleIdByUserId(@Param("userId") Long userId);

    // USER_AUTHORITY 테이블의 ROLE_ID 업데이트
    void updateUserRoleById(@Param("userId") Long userId, @Param("roleId") Long roleId);

    // 사용자 활성화/비활성화 상태 전환
    void toggleUserStatus(@Param("userId") Long userId);
}



