package kr.co.kh.model.vo;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.sql.Timestamp;


@Data // Lombok 어노테이션: getter/setter, toString, equals, hashCode 자동 생성
@Slf4j // Lombok 어노테이션: 로깅을 위한 Logger 필드 자동 생성
public class DashBoardVO {

    private String title;
    private Timestamp createdAt;
    private Long balance;
    private int gameCount;
}
