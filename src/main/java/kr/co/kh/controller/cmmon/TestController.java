package kr.co.kh.controller.cmmon;

import kr.co.kh.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
@RequiredArgsConstructor
public class TestController {

    private final MemberService memberService;

    @GetMapping("/list")
    private ResponseEntity<?> testList() {
        return ResponseEntity.ok(memberService.memberList());
    }

    @GetMapping("/name")
    private ResponseEntity<?> name(@RequestParam(name = "name")String name){
        return ResponseEntity.ok(memberService.findByName(name));
    }
}
