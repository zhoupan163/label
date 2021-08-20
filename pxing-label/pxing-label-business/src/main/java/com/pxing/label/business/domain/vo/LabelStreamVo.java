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
public class LabelStreamVo {
    //select stream_id, task_name, size, status, create_time, type, label_by, remark from label_stream where task_name=? and label_by= ?
    @ApiModelProperty("stream_id")
    private String streamId;

    @ApiModelProperty("任务名称")
    private String taskName;

    @ApiModelProperty("图片数量")
    private int size;

    @ApiModelProperty("状态")
    private String status;

    @ApiModelProperty("创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    @ApiModelProperty("任务标注人")
    private String labelBy;

    @ApiModelProperty("任务备注")
    private String remark;

}
