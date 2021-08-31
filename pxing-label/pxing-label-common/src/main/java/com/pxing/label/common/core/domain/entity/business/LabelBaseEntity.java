package com.pxing.label.common.core.domain.entity.business;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * [ 业务实体类的及基类 ]
 *
 * @author zhoupan
 * @version 1.0
 * @company 1024lab.net
 * @date 2019/3/27 0027 上午 11:15
 * @since JDK1.8
 */
@Data
public class LabelBaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 创建人
     */
    private String createtBy;



}
