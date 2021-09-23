package com.pxing.label.business.domain.entity;


import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.annotation.TableName;
import com.pxing.label.common.core.domain.entity.business.LabelBaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

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
@Document("image")
public class ImageEntity {

    @ApiModelProperty("imageId")
    private String imageId;

    @ApiModelProperty("streamId")
    private String streamId;

    @ApiModelProperty("frameIndex")
    private String frameIndex;

    @ApiModelProperty("传感器类型")
    private String sensorLocation;

    @ApiModelProperty("传感器位置")
    private String sensorType;

    @ApiModelProperty("topic类型")
    private String topicType;

    @ApiModelProperty("日期")
    private String fileDate;

    @ApiModelProperty("width")
    private Double width;

    @ApiModelProperty("height")
    private Double height;

    @ApiModelProperty("png_url")
    private String pngUrl;

    @ApiModelProperty("jpg_url")
    private String jpgUrl;

    @ApiModelProperty("thumbnail_url")
    private String thumbnailUrl;

    @ApiModelProperty("时间戳")
    private String timestamp;

    @ApiModelProperty("备注")
    private String comment;




}
