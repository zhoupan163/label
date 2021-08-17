package com.pxing.label.business.domain.entity;


import lombok.Data;

/**
 * 用户和角色关联 label_task
 * @date  2021/08/12 18:24
 *
 * @author zhoup 官方网址：www.pxing.com
 */
@Data
public class LabelTaskEntity
{
    /** 任务ID */
    private Long taskId;

    /** 任务名称 */
    private Long taskName;

    /** 项目ID */
    private Long projectId;

    /** 状态 */
    private String status;




}
