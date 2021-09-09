package com.pxing.label.business.domain.entity;


import com.baomidou.mybatisplus.annotation.TableName;
import com.pxing.label.common.core.domain.entity.business.LabelBaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 *  [标注项目]
 *
 * @author zhoup
 * @version 1.0
 * @company pxing
 * @copyright  pxing
 * @date  2021-08-31 11:17:56
 * @since JDK1.8
 */
@Data
@TableName("task_stream")
public class TaskStreamEntity extends LabelBaseEntity {

    @ApiModelProperty("视频流id")
    private Long streamId;

    @ApiModelProperty("任务id")
    private Long taskId;

    @ApiModelProperty("图片大小")
    private Integer size;

    @ApiModelProperty("状态")
    private Integer status;

    @ApiModelProperty("一级审核人")
    private String qa1;

    @ApiModelProperty("二级审核人")
    private String qa2;

    @ApiModelProperty("标注人")
    private String label;

    @ApiModelProperty("任务备注")
    private String remark;

}
