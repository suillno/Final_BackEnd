package kr.co.kh.model.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.time.LocalDateTime;

/**
 * 업로드된 파일 정보를 담는 VO 클래스
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@ApiModel(description = "업로드된 파일 정보를 담는 VO")
public class UploadFile {

    @ApiModelProperty(value = "파일 고유 ID", example = "1")
    private Long id;

    @ApiModelProperty(value = "파일이 속한 대상(ex. profile, post)", example = "profile")
    private String fileTarget;

    @ApiModelProperty(value = "원본 파일명", example = "my_photo.png")
    private String fileName;

    @ApiModelProperty(value = "저장된 파일명 (UUID 등)", example = "e3c92cbe.png")
    private String saveFileName;

    @JsonIgnore
    @ApiModelProperty(hidden = true)
    private String filePath;

    @JsonIgnore
    @ApiModelProperty(hidden = true)
    private String fileDir;

    @ApiModelProperty(value = "파일 MIME 타입", example = "image/png")
    private String contentType;

    @ApiModelProperty(value = "파일 크기(byte)", example = "102400")
    private long fileSize;

    @JsonIgnore
    @ApiModelProperty(hidden = true)
    private String username;

    @JsonIgnore
    @ApiModelProperty(hidden = true)
    private LocalDateTime createdAt;

    public UploadFile(String fileName, String saveFileName, String filePath, String contentType, long fileSize, String fileDir, String fileTarget, String username) {
        this.fileName = fileName;
        this.saveFileName = saveFileName;
        this.filePath = filePath;
        this.contentType = contentType;
        this.fileSize = fileSize;
        this.fileDir = fileDir;
        this.fileTarget = fileTarget;
        this.username = username;
    }

    @Builder
    public UploadFile(String fileTarget, String fileName, String saveFileName, String filePath, String fileDir,
                      String contentType, long fileSize, LocalDateTime createdAt, String username) {
        this.fileTarget = fileTarget;
        this.fileName = fileName;
        this.saveFileName = saveFileName;
        this.filePath = filePath;
        this.fileDir = fileDir;
        this.contentType = contentType;
        this.fileSize = fileSize;
        this.createdAt = createdAt;
        this.username = username;
    }
}
