package kr.co.kh.controller.api;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import kr.co.kh.model.vo.GameWalletLogVO;
import kr.co.kh.service.WalletService;
import kr.co.kh.util.ExcelUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/game/excel")
public class ExcelController {

    private final WalletService walletService;

    /**
     * 특정 사용자의 지갑 거래 내역을 Excel 파일로 다운로드합니다.
     * @param response HttpServletResponse 객체 (파일 다운로드 스트림용)
     * @param userId 사용자 ID
     * @return 없음 (응답으로 Excel 파일 스트림 전송)
     */
    @ApiOperation(value = "지갑 내역 엑셀 다운로드", notes = "사용자의 지갑 거래 내역을 Excel 파일로 생성하여 다운로드합니다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "사용자 ID", required = true, dataType = "long", paramType = "path")
    })
    @GetMapping("/download/{userId}")
    public void download(HttpServletResponse response, @PathVariable("userId") Long userId) throws IOException {
        List<HashMap<String, Object>> list = new ArrayList<>();

        List<GameWalletLogVO> logs = walletService.selectLogsByUserId(userId);
        log.info("vo 데이터: {}", logs);

        for (GameWalletLogVO vo : logs) {
            HashMap<String, Object> row = new HashMap<>();
            row.put("일시", vo.getCreatedAt().toString());
            row.put("타입", vo.getLogText());
            row.put("금액", vo.getAmount());
            row.put("잔액", vo.getBalance());
            list.add(row);
        }

        ExcelUtil.download(list, response);
    }

    /**
     * 엑셀 업로드 처리 (추후 구현 예정)
     * @return 없음
     */
    @ApiOperation(value = "엑셀 업로드 (미구현)", notes = "엑셀 파일을 업로드합니다. 현재는 미구현입니다.")
    @PostMapping("/upload")
    public void upload() {
        // TODO: 엑셀 업로드 기능 구현 예정
    }
}
