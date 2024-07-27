package com.example.project.demos.web.dto.confirmOperationFlow;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.util.Date;

@Data
public class EditDTO {
    /**
     * 自增主键
     */
    @ApiModelProperty(value = "自增主键")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /**
     * 确认人英文名
     */
    @ApiModelProperty(value = "确认人英文名")
    private String confirmUser;
    /**
     * 确认时间
     */
    @ApiModelProperty(value = "确认时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date confirmTime;

    @ApiModelProperty(value = "确认意见")
    private String confirmOpinion;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String remark;
}
