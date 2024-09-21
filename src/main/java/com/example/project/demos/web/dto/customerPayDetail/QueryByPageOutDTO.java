package com.example.project.demos.web.dto.customerPayDetail;

import com.example.project.demos.web.dto.list.CustomerPayDetailInfo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class QueryByPageOutDTO {
    /**
     * 总数:200
     */
    @ApiModelProperty(value = "总数 ")
    private Integer turnPageTotalNum;

    @ApiModelProperty(value = "来料/销售合计数量")
    private BigDecimal sumCount;
    @ApiModelProperty(value = "来料/销售合计单价")
    private BigDecimal sumUnitPrice;
    @ApiModelProperty(value = "来料/销售合计物料金额")
    private BigDecimal sumMaterialAmt;

    @ApiModelProperty(value = "合计退货数量")
    private BigDecimal sumReturnCount;
    @ApiModelProperty(value = "合计退货单价")
    private BigDecimal sumReturnUnitPrice;
    @ApiModelProperty(value = "合计退货金额")
    private BigDecimal sumReturnAmt;

    @ApiModelProperty(value = "合计税金")
    private BigDecimal sumTaxAmt;
    @ApiModelProperty(value = "合计其他金额")
    private BigDecimal sumOtherAmt;
    @ApiModelProperty(value = "合计打款金额")
    private BigDecimal sumPayAmt;
    @ApiModelProperty(value = "合计运费")
    private BigDecimal sumFreightAmt;

    private List<CustomerPayDetailInfo> customerPayDetailInfoList;

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
