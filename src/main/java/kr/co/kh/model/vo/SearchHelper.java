package kr.co.kh.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import kr.co.kh.util.AppConstants;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@ApiModel(description = "검색 조건 및 페이징 정보를 담는 헬퍼 클래스")
public class SearchHelper {

    @ApiModelProperty(value = "검색 코드", example = "game")
    private String searchCode;

    @ApiModelProperty(value = "검색 키워드", example = "Witcher")
    private String searchKeyword;

    @ApiModelProperty(value = "검색 구분", example = "title")
    private String searchType;

    @ApiModelProperty(value = "페이지당 데이터 개수", example = "10")
    private int size = Integer.parseInt(AppConstants.DEFAULT_PAGE_SIZE);

    @ApiModelProperty(value = "페이지 번호 (0부터 시작)", example = "0")
    private int page = Integer.parseInt(AppConstants.DEFAULT_PAGE_NUMBER);

    @Builder
    public SearchHelper(String searchCode, String searchKeyword, String searchType, int size, int page) {
        this.searchCode = searchCode;
        this.searchKeyword = searchKeyword;
        this.searchType = searchType;
        this.size = size;
        this.page = page;
    }
}
