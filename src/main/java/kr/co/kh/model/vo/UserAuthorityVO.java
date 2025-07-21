package kr.co.kh.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 사용자 권한 매핑 VO
 * 회원(User)과 권한(Role) 간의 매핑 정보를 담는 VO 클래스입니다.
 */
@Getter
@Setter
@ToString
@ApiModel(description = "사용자와 권한 정보를 매핑하는 VO")
public class UserAuthorityVO {

    @ApiModelProperty(value = "회원 고유 ID", example = "101")
    private Long userId;

    @ApiModelProperty(value = "권한 고유 ID", example = "2")
    private Long roleId;
}
