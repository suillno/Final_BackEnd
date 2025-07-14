package kr.co.kh.controller.api;

import kr.co.kh.util.ExcelUtil;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api/excel")
public class ExcelController {

    @GetMapping("/download")
    public void download(HttpServletResponse response) throws IOException {
        List<HashMap<String, Object>> list = new ArrayList<>();
        HashMap<String, Object> row1 = new HashMap<>();
        HashMap<String, Object> row2 = new HashMap<>();
        row1.put("id", 1);
        row1.put("name", "홍길동");
        row1.put("email", "test@mail.com");

        row2.put("id", 2);
        row2.put("name", "임꺽정");
        row2.put("email", "test2@mail.com");

        list.add(row1);
        list.add(row2);

        ExcelUtil.download(list, response);

    }

    @PostMapping("/upload")
    public void upload() {}
}
