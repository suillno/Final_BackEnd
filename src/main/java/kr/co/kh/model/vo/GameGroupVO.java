package kr.co.kh.model.vo;


import lombok.Data;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
@Getter
public class GameGroupVO {
    private String userName;
    private Long gameId;
    private String result;
}
