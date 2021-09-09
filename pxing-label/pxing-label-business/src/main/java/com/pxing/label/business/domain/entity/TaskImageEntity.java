package com.pxing.label.business.domain.entity;

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
@Document("task_image1")
public class TaskImageEntity {

    @ApiModelProperty("imageId")
    private Long imageId;

    @ApiModelProperty("streamId")
    private Long stream_id;

    @ApiModelProperty("任务Id")
    private Long task_id;

    @ApiModelProperty("png_url")
    private String png_url;

    @ApiModelProperty("jpg_url")
    private String jpg_url;

    @ApiModelProperty("comment")
    private String comment;

    @ApiModelProperty("status")
    private Integer status;

    @ApiModelProperty("image_lock")
    private Integer image_lock;

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
