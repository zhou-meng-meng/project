package com.example.project.demos.web.dto.uploadFileInfo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class DownloadFileInfoDTO {
    @ApiModelProperty(value = "文件的全路径")
    private String filePath;
}
