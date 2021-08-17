package com.pxing.label.business.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

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
public class LabelAttributeVo {

    @ApiModelProperty("属性Id")
    private Long attributeId;

    @ApiModelProperty("属性名称")
    private String attributeName;

    @ApiModelProperty("关联工程id")
    private Long projectId;

    @ApiModelProperty("图片数量")
    private Long size;

    @ApiModelProperty("任务状态")
    private String status;

    @ApiModelProperty("创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    @ApiModelProperty("任务创建人")
    private String createBy;

    @ApiModelProperty("任务备注")
    private String remark;




}
