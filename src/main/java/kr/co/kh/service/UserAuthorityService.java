package kr.co.kh.service;

import kr.co.kh.exception.NotFoundException;
import kr.co.kh.mapper.UserAuthorityMapper;
import kr.co.kh.model.payload.request.EmailRequest;
import kr.co.kh.model.vo.MemberVO;
import kr.co.kh.model.vo.UserAuthorityVO;
import kr.co.kh.util.Util;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j

public class UserAuthorityService {

    private final UserAuthorityMapper userAuthorityMapper;
    private final MailService mailService;
    private final PasswordEncoder passwordEncoder;

    public void save(UserAuthorityVO userAuthorityVO) {
        userAuthorityMapper.save(userAuthorityVO);
    }

    // 아이디찾기
    public MemberVO findId(MemberVO memberVO) {
        MemberVO result = userAuthorityMapper.findId(memberVO);


        if(result != null) {

            EmailRequest emailRequest = new EmailRequest();
            emailRequest.setUsername(result.getUsername());
            emailRequest.setMailTo(result.getEmail());
            emailRequest.setMailType("findId");
            emailRequest.setName(result.getName());
            mailService.sendMimeMessage(emailRequest);
        } else {
            throw new NotFoundException(memberVO.getName() + "에 해당하는 계정이 없습니다.");
        }
        return result;

    }

    public boolean updatePw(MemberVO memberVO) {
        log.info("입력된 사용자 정보: {}", memberVO); // 먼저 파라미터 로그

        MemberVO user = userAuthorityMapper.existUser(memberVO);
        log.info("DB에서 조회된 사용자: {}", user); // null도 안전하게 출력됨

        if (user == null) {
            throw new NotFoundException(memberVO.getUsername() + "에 해당하는 계정이 없습니다.");
        }

        String result = Util.randomString(5);
        log.info("임시 비밀번호: {}", result);

        String encodePassword = passwordEncoder.encode(result);
        memberVO.setPassword(encodePassword);

        userAuthorityMapper.changePw(memberVO);

        EmailRequest emailRequest = new EmailRequest();
        emailRequest.setUsername(user.getUsername());
        emailRequest.setMailTo(user.getEmail());
        emailRequest.setMailType("changePw");
        emailRequest.setName(user.getName());
        emailRequest.setPassword(result);

        mailService.sendMimeMessage(emailRequest);

        return true;
    }



}
