package com.pxing.label.business.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

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
public class LabelTaskStream {

    @ApiModelProperty("任务名称")
    private String taskName;

    @ApiModelProperty("图片数量")
    private Long size;

    @ApiModelProperty("streamId")
    private String streamId;

    @ApiModelProperty("status")
    private String status;

    @ApiModelProperty("任务备注")
    private String remark;

}
