package com.example.project.demos.web.dto.uploadFileInfo;

import lombok.Data;

@Data
public class UploadFileInfoDTO {
    private String loginUser;
    private String functionId;
    private Long businessId;
    private String remark;
}
