package com.example.project.demos.web.dto.sysMenu;

import com.example.project.demos.web.dto.list.SysMenuTreeInfo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.util.List;

@Data
public class QueryMenuTreeOutDTO {

    private static final long serialVersionUID = -89026850526790880L;
    private List<SysMenuTreeInfo> list;

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
