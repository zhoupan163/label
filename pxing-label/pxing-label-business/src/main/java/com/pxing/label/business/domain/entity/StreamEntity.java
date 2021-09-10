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
@TableName("stream")
public class StreamEntity extends LabelBaseEntity {

    @ApiModelProperty("传感器位置")
    private String sensorLocation;

    @ApiModelProperty("传感器类型")
    private String sensorType;

    @ApiModelProperty("topic类型")
    private String topicType;

    @ApiModelProperty("width")
    private Double width;

    @ApiModelProperty("height")
    private Double height;

    @ApiModelProperty("标记状态")
    private Integer tagStatus;

}
