package kr.co.kh.service;

import kr.co.kh.model.Member;
import kr.co.kh.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    public List<Member> memberList() {
        return memberRepository.findAll();
    }

    public List<Member> findByName(String name) {
        return memberRepository.findByName(name);
    }
}
