package com.pxing.label.business.domain.entity;


import com.baomidou.mybatisplus.annotation.TableName;
import com.pxing.label.common.core.domain.entity.business.LabelBaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 *  [标签流]
 *
 * @author zhoup
 * @version 1.0
 * @company pxing
 * @copyright  pxing
 * @date  2021-08-31 11:27:20
 * @since JDK1.8
 *
 *
 *
 */
@Data
@TableName("label_stream_tag")
public class LabelStreamTagEntity extends LabelBaseEntity {

    @ApiModelProperty("streamId")
    private Long streamId;

    @ApiModelProperty("标签id")
    private Long tagId;

    @ApiModelProperty("状态")
    private String status;

}
