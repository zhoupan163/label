package com.pxing.label.business.domain.vo;


import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;



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
@TableName("label_stream")
public class LabelStreamVo{

    @ApiModelProperty("stream_id")
    private String streamId;

    @ApiModelProperty("任务名称")
    private String taskName;

    @ApiModelProperty("图片数量")
    private Integer size;

    @ApiModelProperty("状态")
    private Integer status;

    @ApiModelProperty("一级审核人")
    private String qa1;

    @ApiModelProperty("二级审核人")
    private String qa2;

    @ApiModelProperty("标注人")
    private String labelBy;

    @ApiModelProperty("任务备注")
    private String remark;

}
