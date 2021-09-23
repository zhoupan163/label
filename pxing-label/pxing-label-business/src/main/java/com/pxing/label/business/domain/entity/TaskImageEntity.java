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
@Document("task_image")
public class TaskImageEntity {

    @ApiModelProperty("imageId")
    private String imageId;

    @ApiModelProperty("streamId")
    private String streamId;

    @ApiModelProperty("任务名称")
    private String taskName;

    @ApiModelProperty("png_url")
    private String pngUrl;

    @ApiModelProperty("jpg_url")
    private String jpgUrl;

    private Double width;

    private Double height;

    @ApiModelProperty("comment")
    private String comment;

    @ApiModelProperty("status")
    private Integer status;

    @ApiModelProperty("image_lock")
    private Integer imageLock;

    @ApiModelProperty("label")
    private String label;

    @ApiModelProperty("qa1")
    private String qa1;

    @ApiModelProperty("qa2")
    private String qa2;

    @ApiModelProperty("qaComment")
    private String qaComment;

    @ApiModelProperty("标注属性")
    private List<JSONObject> annotationInfo;

}
