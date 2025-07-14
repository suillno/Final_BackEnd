package kr.co.kh.service;

import kr.co.kh.mapper.ReviewMapper;
import kr.co.kh.model.vo.ReviewVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewMapper reviewMapper;

    // 전체 리뷰 목록 조회
    public List<ReviewVO> getAllReviews() {
        return reviewMapper.findAllReviews();
    }

    // 리뷰 삭제 기능
    public void deleteReviewById(Long reviewId) {
        reviewMapper.deleteReviewById(reviewId);
    }
}
