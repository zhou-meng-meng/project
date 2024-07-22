package com.example.project.demos.web.dto.uploadFileInfo;

import com.example.project.demos.web.dto.list.UploadFileId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class UploadFileInfoOutDTO {
    private List<UploadFileId> fileIdList;

    /**
     * 操作结果编码:null
     */
    @ApiModelProperty(value = "操作结果编码")
    private String errorCode;

    /**
     * 操作结果信息:null
     */
    @ApiModelProperty(value = "操作结果信息")
    private String errorMsg;

}
