package com.example.project.demos.web.dto.sysDictData;

import com.example.project.demos.web.dto.list.SysDictDataInfo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class QueryListOutDTO {


    private List<SysDictDataInfo> sysDictDataInfoList;

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
