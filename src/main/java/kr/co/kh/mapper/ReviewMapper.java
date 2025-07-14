package kr.co.kh.mapper;

import kr.co.kh.model.vo.ReviewVO;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface ReviewMapper {

    // 리뷰 전체 조회
    List<ReviewVO> findAllReviews();

    // 리뷰 삭제
    void deleteReviewById(Long reviewId);
}
