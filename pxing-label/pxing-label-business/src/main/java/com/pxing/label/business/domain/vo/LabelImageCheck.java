package com.pxing.label.business.domain.vo;


import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
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
public class LabelImageCheck {

    @ApiModelProperty("taskName")
    private String taskName;

    @ApiModelProperty("streamId")
    private String streamId;

    @ApiModelProperty("qaType")
    private String qaType;

    @ApiModelProperty("imgList")
    private List<String> imgList;

    @ApiModelProperty("imgStatusList")
    private List<Integer> imgStatusList;

    @ApiModelProperty("imgQaCommentList")
    private List<String> imgQaCommentList;







}
