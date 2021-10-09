package com.pxing.label.common.core.domain.dto.business;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class BaseDto {

    @ApiModelProperty("备注信息")
    private String remark;

    @ApiModelProperty("范围时间")
    private List<String> dateRange;

    @ApiModelProperty("pageNum")
    private Integer pageNum;

    @ApiModelProperty("结束时间")
    private Integer pageSize;



}
