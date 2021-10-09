package com.pxing.label.business.domain.dto;

import com.pxing.label.common.core.domain.dto.business.BaseDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class TaskGroupDto extends BaseDto {
    @ApiModelProperty("taskName")
    private String taskName;

    @ApiModelProperty("groupName")
    private String groupName;
}
