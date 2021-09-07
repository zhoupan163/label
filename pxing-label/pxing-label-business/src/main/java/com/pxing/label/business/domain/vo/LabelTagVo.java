package com.pxing.label.business.domain.vo;


import com.baomidou.mybatisplus.annotation.TableName;
import com.pxing.label.common.core.domain.entity.business.LabelBaseEntity;
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
public class LabelTagVo {

    @ApiModelProperty("标签id")
    private Long id;

    @ApiModelProperty("标签名称")
    private String tagName;

    @ApiModelProperty("场景id")
    private Long sceneId;

    @ApiModelProperty("场景名称")
    private String  sceneName;

}
