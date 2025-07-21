package kr.co.kh.controller.admin;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import kr.co.kh.model.vo.ReviewVO;
import kr.co.kh.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/game/admin/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    /**
     * 전체 리뷰 목록을 조회합니다.
     * @return 모든 리뷰 리스트
     */
    @GetMapping
    @ApiOperation(value = "전체 리뷰 목록 조회", notes = "등록된 모든 게임 리뷰를 반환합니다.")
    public ResponseEntity<List<ReviewVO>> getAllReviews() {
        return ResponseEntity.ok(reviewService.getAllReviews());
    }

    /**
     * 리뷰 ID를 기준으로 리뷰를 삭제합니다.
     * @param reviewId 삭제할 리뷰 ID
     * @return 리뷰 삭제 후 204 No Content 응답
     */
    @DeleteMapping("/{reviewId}")
    @ApiOperation(value = "리뷰 삭제", notes = "리뷰 ID를 기준으로 해당 리뷰를 삭제합니다.")
    @ApiImplicitParam(
            name = "reviewId",
            value = "삭제할 리뷰 ID",
            required = true,
            dataType = "Long",
            dataTypeClass = Long.class,
            paramType = "path"
    )
    public ResponseEntity<Void> deleteReview(@PathVariable Long reviewId) {
        reviewService.deleteReviewById(reviewId);
        return ResponseEntity.noContent().build();
    }
}