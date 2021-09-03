package com.pxing.label.business.domain.entity;


import com.baomidou.mybatisplus.annotation.TableName;
import com.pxing.label.common.core.domain.entity.business.LabelBaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 *  [标注场景]
 *
 * @author zhoup
 * @version 1.0
 * @company pxing
 * @copyright  pxing
 * @date  2021-08-31 11:17:56
 * @since JDK1.8
 */
@Data
@TableName("label_scene")
public class LabelSceneEntity extends LabelBaseEntity {

    @ApiModelProperty("项目Id")
    private String projectId;

    @ApiModelProperty("场景名称")
    private String sceneName;

}
