package kr.co.kh.controller.api;

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


    @PostMapping("/upload")
    public void upload() {}
}
