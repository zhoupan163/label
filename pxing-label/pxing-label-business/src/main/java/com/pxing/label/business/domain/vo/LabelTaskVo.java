package com.pxing.label.business.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 *  [标注任务]
 *
 * @author zhoup
 * @version 1.0
 * @company pxing
 * @copyright  pxing
 * @date  2021-08-16 09:17:56
 * @since JDK1.8
 */
@Data
public class LabelTaskVo {

    @ApiModelProperty("任务Id")
    private Long taskId;

    @ApiModelProperty("任务名称")
    private String taskName;

    @ApiModelProperty("关联工程id")
    private Long projectId;

    @ApiModelProperty("图片总数量")
    private Long size;

    @ApiModelProperty("一级审核中图片数量")
    private Long qa1Size;

    @ApiModelProperty("二级审核中图片数量")
    private Long qa2Size;

    @ApiModelProperty("驳回中图片数量")
    private Long rejectSize;

    @ApiModelProperty("审核完成图片数量")
    private Long finishedSize;

    @ApiModelProperty("任务状态")
    private String status;

    @ApiModelProperty("任务类型")
    private String type;

    @ApiModelProperty("创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    @ApiModelProperty("任务创建人")
    private String createBy;

    @ApiModelProperty("任务标注人")
    private String labelBy;

    @ApiModelProperty("任务备注")
    private String remark;

    @ApiModelProperty("修改时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;
}
