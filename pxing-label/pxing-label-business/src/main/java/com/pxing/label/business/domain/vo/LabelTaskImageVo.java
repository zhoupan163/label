package com.pxing.label.business.domain.vo;

import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;


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
@Document("task_image")
public class LabelTaskImageVo {

    @ApiModelProperty("image_id")
    private String image_id;

    @ApiModelProperty("stream_id")
    private String stream_id;

    @ApiModelProperty("任务名称")
    private String task_name;

    @ApiModelProperty("project_name")
    private String project_name;

    @ApiModelProperty("sensor_location")
    private String sensor_location;

    @ApiModelProperty("sensor_type")
    private String sensor_type;

    @ApiModelProperty("topic_type")
    private String topic_type;

    @ApiModelProperty("file_date")
    private String file_date;

    @ApiModelProperty("width")
    private Double width;

    @ApiModelProperty("height")
    private Double height;

    @ApiModelProperty("png_url")
    private String png_url;

    @ApiModelProperty("jpg_url")
    private String jpg_url;

    @ApiModelProperty("thumbnail_url")
    private String thumbnail_url;

    @ApiModelProperty("frame_index")
    private String frame_index;

    @ApiModelProperty("timestamp")
    private String timestamp;

    @ApiModelProperty("comment")
    private String comment;

    @ApiModelProperty("image_status")
    private String image_status;

    @ApiModelProperty("image_lock")
    private String image_lock;

    @ApiModelProperty("label")
    private String label;

    @ApiModelProperty("qa1")
    private String qa1;

    @ApiModelProperty("qa2")
    private String qa2;

    @ApiModelProperty("qa_comment")
    private String qa_comment;

    @ApiModelProperty("标注属性")
    private List<JSONObject> annotationInfo;


}
