package kr.co.kh.controller.admin;

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

    // 전체 리뷰 목록 조회
    @GetMapping
    public ResponseEntity<List<ReviewVO>> getAllReviews() {
        return ResponseEntity.ok(reviewService.getAllReviews());
    }

    // 리뷰 삭제 요청 (리뷰 ID 기준)
    @DeleteMapping("/{reviewId}")
    public ResponseEntity<Void> deleteReview(@PathVariable Long reviewId) {
        reviewService.deleteReviewById(reviewId);
        return ResponseEntity.noContent().build(); // 204 No Content 응답
    }
}
