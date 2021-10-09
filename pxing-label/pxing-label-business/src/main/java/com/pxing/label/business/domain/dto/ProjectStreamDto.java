package com.pxing.label.business.domain.dto;

import com.pxing.label.common.core.domain.dto.business.BaseDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ProjectStreamDto extends BaseDto {
    @ApiModelProperty("projectName")
    private String projectName;

    @ApiModelProperty("标记状态")
    private Integer tagStatus;

    @ApiModelProperty("传感器位置")
    private String sensorLocation;

}
