package com.example.project.demos.web.dto.materialPackageDetail;


import com.example.project.demos.web.dto.list.MaterialPackageDetailInfo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.util.List;

@Data
public class QueryByPackageIdOutDTO {


    private List<MaterialPackageDetailInfo> materialPackageDetailInfoList;

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
