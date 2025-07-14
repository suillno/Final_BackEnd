package kr.co.kh.mapper;

import kr.co.kh.model.vo.MemberVO;
import kr.co.kh.model.vo.UserAuthorityVO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserAuthorityMapper {

    void save(UserAuthorityVO userAuthorityVO);


    // 아이디 찾기
    MemberVO findId(MemberVO memberVO);

    // 비밀번호 조회 확인
    MemberVO existUser(MemberVO memberVO);
    // 비밀번호 변경
    void changePw(MemberVO memberVO);


}

