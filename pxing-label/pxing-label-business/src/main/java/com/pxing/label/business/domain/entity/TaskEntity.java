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
@TableName("label_task")
public class TaskEntity extends LabelBaseEntity {

    @ApiModelProperty("项目名称")
    private String projectName;

    @ApiModelProperty("任务名称")
    private String taskName;

    @ApiModelProperty("任务类型")
    private Integer type;
}
