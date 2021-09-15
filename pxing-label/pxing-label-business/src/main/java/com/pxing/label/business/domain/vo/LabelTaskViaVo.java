package com.pxing.label.business.domain.vo;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

/**
 *  [标注属性]
 *
 * @author zhoup
 * @version 1.0
 * @company pxing
 * @copyright  pxing
 * @date  2021-08-16 13:31:56
 * @since JDK1.8
 */
@Data
@Document(collection= "label_task_viaInfo")
public class LabelTaskViaVo {

    @ApiModelProperty("属性Id")
    private String taskName;

    @ApiModelProperty("属性名称")
    private JSONObject via_project_info;
}
