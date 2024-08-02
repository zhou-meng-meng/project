package com.example.project.demos.web.dto.list;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 产量入库  生产员工使用
 */
@Data
public class ProductProducerInfo {

    /*@ApiModelProperty(value = "主键")
    private Long id;
    @ApiModelProperty(value = "产量入库主表主键")
    private Long incomeId;*/

    /**
     * 生产员工
     */
    @ApiModelProperty(value = "生产员工登录名")
    private String producer;
    @ApiModelProperty(value = "生产员工姓名")
    private String producerName;

    @ApiModelProperty(value = "员工产量")
    private BigDecimal producerNum;

}
