package kr.co.kh.mapper;

import kr.co.kh.model.vo.MemberVO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface LeaveMapper {
    // 탈퇴 처리 (IS_ACTIVE 변경)
    int deactivateMember(Long memberId);
    // 회원정보 조회( 비밀번호 검증용)
    MemberVO findById(Long memberId);
}
