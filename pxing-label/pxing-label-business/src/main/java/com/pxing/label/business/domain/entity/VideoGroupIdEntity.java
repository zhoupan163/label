package com.pxing.label.business.domain.entity;


import com.baomidou.mybatisplus.annotation.TableName;
import com.pxing.label.common.core.domain.entity.business.LabelBaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.annotation.Transient;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;


/**
 *  [标注项目]
 *
 * @author zhoup
 * @version 1.0
 * @company pxing
 * @copyright  pxing
 * @date  2021-08-31 11:17:56
 * @since JDK1.8
 */
@Data
@TableName("video_group_id")
public class VideoGroupIdEntity extends LabelBaseEntity {

    @ApiModelProperty("人id")
    private Integer personId;

    @ApiModelProperty("任务名称")
    private String personName;

    @ApiModelProperty("视频组名称")
    private String groupName;

    @ApiModelProperty("图片链接")
    private String imgUrl;

    //@ApiModelProperty("图片信息")
    @Transient
    private transient MultipartFile file;

}
