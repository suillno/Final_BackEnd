package kr.co.kh.controller.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/test")
public class ApiTestController {

    /**
     * api 연결상태 테스트
     * @return
     */
    @GetMapping
    public ResponseEntity<String> testApi() {
        return ResponseEntity.ok("API 연결 성공!");
    }
}
