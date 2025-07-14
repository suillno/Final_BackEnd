package kr.co.kh.repository;

import kr.co.kh.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

// vo와, vo의 pk값을 상속
public interface MemberRepository extends JpaRepository<Member, Long> {
//    조건문을 적용하여 DB 가져오는법
    List<Member> findByName(String name);
}
