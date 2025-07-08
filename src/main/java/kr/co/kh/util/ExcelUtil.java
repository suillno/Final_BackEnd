package kr.co.kh.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@Slf4j
public class ExcelUtil {

    /**
     * 엑셀 다운로드 기능
     * @param list: 엑셀에 쓸 데이터 리스트 (각 Map은 한 행을 의미)
     * @param response: HttpServletResponse 객체 (엑셀 파일을 브라우저로 전송)
     */
    public static void download(List<HashMap<String, Object>> list, HttpServletResponse response) throws IOException {
        log.info(list.toString());

        // 데이터가 비어 있으면 다운로드 중단
        if (list == null || list.isEmpty()) {
            throw new IllegalArgumentException("엑셀 다운로드 대상 데이터가 없습니다.");
        }

        // 첫 번째 row의 키를 기준으로 컬럼 헤더 생성
        Set<String> keySet = list.get(0).keySet();
        List<String> header = new ArrayList<>(keySet);

        // 엑셀 워크북 및 시트 생성
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("sheet1");

        // 0번째 행에 컬럼명(헤더) 출력
        Row headerRow = sheet.createRow(0);
        for (int i = 0; i < header.size(); i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(header.get(i)); // 예: "id", "name", "email"
        }

        // 데이터 row 작성 (1행부터 시작)
        for (int i = 0; i < list.size(); i++) {
            HashMap<String, Object> rowMap = list.get(i);
            Row dataRow = sheet.createRow(i + 1);
            for (int j = 0; j < header.size(); j++) {
                Object value = rowMap.get(header.get(j));
                dataRow.createCell(j).setCellValue(value != null ? value.toString() : "");
            }
        }

        // 파일 응답 헤더 설정
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=data.xlsx");

        // 워크북을 HTTP 응답 스트림으로 전송
        workbook.write(response.getOutputStream());
        workbook.close();
    }

    // 추후 업로드 기능 구현 예정
    public static void upload() {
    }
}
