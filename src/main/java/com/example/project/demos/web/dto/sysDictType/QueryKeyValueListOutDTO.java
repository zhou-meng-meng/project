package com.example.project.demos.web.dto.sysDictType;

import com.example.project.demos.web.dto.list.SysDictDataKeyValueInfo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class QueryKeyValueListOutDTO {
    @ApiModelProperty(value = "字典值键值对")
    private Map<String, List<SysDictDataKeyValueInfo>> map;

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
