package com.pxing.label.business.service;


import com.pxing.label.business.domain.entity.TaskDetailEntity;

public interface TaskDetailService {

    TaskDetailEntity getTaskDetail(String taskName);

    int insertTaskDetail(String taskName);

    int updateTaskDetailTotal(Integer framesize, String taskName);
}
