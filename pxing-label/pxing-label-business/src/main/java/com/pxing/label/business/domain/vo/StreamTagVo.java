package com.pxing.label.business.domain.vo;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 *  [标注标签]
 *
 * @author zhoup
 * @version 1.0
 * @company pxing
 * @copyright  pxing
 * @date  2021-08-31 11:17:56
 * @since JDK1.8
 */
@Data
public class StreamTagVo {

    @ApiModelProperty("视频流id")
    private Long streamId;


    @ApiModelProperty("备注")
    private String  remark;

}
