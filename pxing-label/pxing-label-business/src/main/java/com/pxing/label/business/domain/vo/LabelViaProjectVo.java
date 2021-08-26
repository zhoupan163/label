package com.pxing.label.business.domain.vo;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

/**
 *  [标注属性]
 *
 * @author zhoup
 * @version 1.0
 * @company pxing
 * @copyright  pxing
 * @date  2021-08-16 10:17:56
 * @since JDK1.8
 */
@Data
@Document("via_project")
public class LabelViaProjectVo {

    @ApiModelProperty("任务名称")
    private String task_name;

    @ApiModelProperty("type")
    private String type;

    @ApiModelProperty("via_settings")
    private JSONObject via_settings;

    @ApiModelProperty("via_img_metadata")
    private JSONObject via_img_metadata;

    @ApiModelProperty("via_attributes")
    private JSONObject via_attributes;

    @ApiModelProperty("via_image_id_list")
    private List<String> via_image_id_list;

    @ApiModelProperty("via_data_format_version")
    private String via_data_format_version;

}
