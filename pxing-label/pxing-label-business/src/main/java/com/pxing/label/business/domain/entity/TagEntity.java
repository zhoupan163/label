package com.pxing.label.business.domain.entity;


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
@TableName("label_tag")
public class TagEntity extends LabelBaseEntity {

    @ApiModelProperty("场景名称")
    private String sceneName;

    @ApiModelProperty("标签名称")
    private String tagName;

    @ApiModelProperty("项目名称")
    private String projectName;

    @ApiModelProperty("示例图片链接")
    private String imageUrl;

}
