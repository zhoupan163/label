package com.pxing.label.business.domain.entity;


import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.annotation.TableName;
import com.pxing.label.common.core.domain.entity.business.LabelBaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;


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
@TableName("image")
public class ImageEntity extends LabelBaseEntity {

    @ApiModelProperty("streamId")
    private Long streamId;

    @ApiModelProperty("frame_index")
    private String frameIndex;

    @ApiModelProperty("传感器类型")
    private String sensorLocation;

    @ApiModelProperty("传感器位置")
    private String sensorType;

    @ApiModelProperty("topic类型")
    private String topicType;

    @ApiModelProperty("width")
    private Integer width;

    @ApiModelProperty("height")
    private Integer height;

    @ApiModelProperty("png_url")
    private String pngUrl;

    @ApiModelProperty("jpg_url")
    private String jpgUrl;

    @ApiModelProperty("thumbnail_url")
    private String thumbnailUrl;


}
