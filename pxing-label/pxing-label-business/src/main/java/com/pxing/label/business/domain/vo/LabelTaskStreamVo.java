package com.pxing.label.business.domain.vo;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

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
public class LabelTaskStreamVo {
    @ApiModelProperty("任务名称")
    private String taskName;

    @ApiModelProperty("stream_id")
    private String streamId;

    @ApiModelProperty("图片数量")
    private int size;

}
