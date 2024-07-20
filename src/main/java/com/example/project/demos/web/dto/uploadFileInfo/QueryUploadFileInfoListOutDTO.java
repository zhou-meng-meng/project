package com.example.project.demos.web.dto.uploadFileInfo;

import com.example.project.demos.web.dto.list.UploadFileInfo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class QueryUploadFileInfoListOutDTO {
    private List<UploadFileInfo> fileInfoList;

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

    @ApiModelProperty(value = "总数 ")
    private Integer turnPageTotalNum;

}
