package com.pxing.label.business.domain.dto;

import com.pxing.label.common.core.domain.dto.business.BaseDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class TaskImgDto extends BaseDto {

    @ApiModelProperty("taskName")
    private String taskName;

    @ApiModelProperty("streamId")
    private String streamId;

    @ApiModelProperty("imageId")
    private String imageId;

    @ApiModelProperty("状态")
    private Integer status;

    @ApiModelProperty("标注人")
    private String label;

    @ApiModelProperty("一级审核人")
    private String qa1;

    @ApiModelProperty("二级审核人")
    private String qa2;

   //标注人审核人  修改时间 提交时间

}
