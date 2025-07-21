package kr.co.kh.controller.game.member;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import kr.co.kh.model.vo.GameReviewVO;
import kr.co.kh.service.GameReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController // REST API 컨트롤러임을 명시
@RequestMapping("/game/member") // 이 컨트롤러의 공통 URL Prefix
@Slf4j // 로그 사용을 위한 Lombok 어노테이션
@RequiredArgsConstructor // 생성자 주입을 위한 Lombok 어노테이션
public class GameReviewController {

    private final GameReviewService gameReviewService;

    /**
     * 리뷰를 등록하거나 수정합니다.
     * @param vo 클라이언트로부터 전달받은 리뷰 정보
     * @return 등록 또는 수정 완료 메시지
     */
    @ApiOperation(value = "리뷰 등록", notes = "게임 리뷰를 등록하거나 수정합니다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "vo", value = "리뷰 정보(GameReviewVO)", required = true, dataType = "GameReviewVO", paramType = "body")
    })
    @PostMapping("/review/add")
    public ResponseEntity<?> reviewSave(@RequestBody GameReviewVO vo) {
        boolean result = gameReviewService.reviewSave(vo);
        if (result) {
            return ResponseEntity.ok("리뷰를 등록하였습니다.");
        } else {
            return ResponseEntity.ok("리뷰를 수정하였습니다.");
        }
    }

    /**
     * 특정 게임에 대한 사용자의 리뷰를 삭제합니다.
     * @param userName 사용자 이름
     * @param gameId 게임 ID
     * @return 리뷰 삭제 완료 메시지
     */
    @ApiOperation(value = "리뷰 삭제", notes = "게임 리뷰를 삭제합니다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userName", value = "사용자 이름", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "gameId", value = "게임 ID", required = true, dataType = "long", paramType = "query")
    })
    @DeleteMapping("/review")
    public ResponseEntity<?> reviewDelete(@RequestParam String userName, @RequestParam Long gameId) {
        int result = gameReviewService.reviewDelete(userName, gameId);
        return ResponseEntity.ok("리뷰를 삭제하였습니다.");
    }

    /**
     * 특정 게임에 등록된 리뷰 목록을 조회합니다.
     * @param gameId 리뷰를 조회할 게임 ID
     * @return 해당 게임에 대한 리뷰 리스트
     */
    @ApiOperation(value = "리뷰 목록 조회", notes = "특정 게임에 대한 리뷰 목록을 반환합니다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "gameId", value = "게임 ID", required = true, dataType = "long", paramType = "path")
    })
    @GetMapping("/review/list/{gameId}")
    public ResponseEntity<?> getReviewList(@PathVariable Long gameId) {
        return ResponseEntity.ok(gameReviewService.reviewListByGameId(gameId));
    }
}