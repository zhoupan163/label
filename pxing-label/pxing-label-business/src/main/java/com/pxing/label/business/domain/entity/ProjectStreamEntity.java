package com.pxing.label.business.domain.entity;


import com.baomidou.mybatisplus.annotation.TableName;
import com.pxing.label.common.core.domain.entity.business.LabelBaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;


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
@Document("project_stream")
public class ProjectStreamEntity {

    @ApiModelProperty("streamId")
    private String streamId;

    @ApiModelProperty("projectName")
    private String projectName;

    @ApiModelProperty("tagStatus")
    private Long tagStatus;

    @ApiModelProperty("comment")
    private String comment;

    @ApiModelProperty("上传时间")
    private String updateTime;

    @ApiModelProperty("开始时间")
    private String startTime;

    @ApiModelProperty("结束时间")
    private String endTime;

    @ApiModelProperty("传感器位置")
    private String sensorLocation;

    @ApiModelProperty("传感器类型")
    private String sensorType;

    @ApiModelProperty("topic类型")
    private String topicType;

    @ApiModelProperty("设备id")
    private String deviceId;

    @ApiModelProperty("设备编码")
    private String deviceCode;

    @ApiModelProperty("rosbagId")
    private String rosbagId;

    @ApiModelProperty("大小")
    private String frameSize;

}
