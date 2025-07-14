package kr.co.kh.service;

import kr.co.kh.mapper.LeaveMapper;
import kr.co.kh.model.vo.MemberVO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;




@Service
@AllArgsConstructor
@Slf4j
public class LeaveService {
    private final LeaveMapper leaveMapper;
    private final PasswordEncoder passwordEncoder;

    public boolean deactivateMember(Long memberId, String inputPassword) {
        MemberVO member = leaveMapper.findById(memberId);

        if (member == null) {
            throw new IllegalArgumentException("회원이 존재하지 않습니다.");
        }

        if (!passwordEncoder.matches(inputPassword, member.getPassword())) {
            return false;
        }

        int result = leaveMapper.deactivateMember(memberId);
        return result == 1;
    }

}
