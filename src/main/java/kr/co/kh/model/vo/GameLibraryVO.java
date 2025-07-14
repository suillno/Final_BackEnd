package kr.co.kh.model.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Data
@Slf4j
@Getter
public class GameLibraryVO {
    private Long libraryId;           // 라이브러리 고유 ID
    private String userName;          // 유저 이름
    private Long gameId;              // 게임 ID
    private String title;             // 게임 제목
    private String backgroundImage;   // 배경 이미지 URL
    private Long price;               // 가격
    /** 생성 일시 */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private Timestamp createdAt;      // 생성일시
    private String released;          // 출시일
    private String esrRating;         // 등급 (예: Everyone, Teen, Mature 등)
    private String result;
}
