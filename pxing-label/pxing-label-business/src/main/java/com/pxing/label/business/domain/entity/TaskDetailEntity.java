package com.pxing.label.business.domain.entity;


import com.baomidou.mybatisplus.annotation.TableName;
import com.pxing.label.common.core.domain.entity.business.LabelBaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 *  [标注任务]
 *
 * @author zhoup
 * @version 1.0
 * @company pxing
 * @copyright  pxing
 * @date  2021-08-31 11:17:56
 * @since JDK1.8
 */
@Data
@TableName("task_detail")
public class TaskDetailEntity extends LabelBaseEntity {

    @ApiModelProperty("任务名称")
    private String taskName;

    @ApiModelProperty("总数")
    private Integer total;

    @ApiModelProperty("待标注总数")
    private Integer unLabel;

    @ApiModelProperty("标注中总数")
    private Integer labeling;

    @ApiModelProperty("废弃数量")
    private Integer discarded;

    @ApiModelProperty("标注完成待qa1审核总数")
    private Integer labeled;

    @ApiModelProperty("qa1中审核数量")
    private Integer qa1;

    @ApiModelProperty("qa1完成待qa2审核数量")
    private Integer qa1ed;

    @ApiModelProperty("qa2审核中数量")
    private Integer qa2;

    @ApiModelProperty("审核完成数量")
    private Integer finished;

}
