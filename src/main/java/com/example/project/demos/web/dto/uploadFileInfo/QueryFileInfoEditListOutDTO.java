package com.example.project.demos.web.dto.uploadFileInfo;

import com.example.project.demos.web.dto.list.UploadFileEditInfo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.util.List;

@Data
public class QueryFileInfoEditListOutDTO {
    private List<UploadFileEditInfo> fileInfoList;

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
