package com.pxing.label.business.service;


import com.pxing.label.business.domain.entity.TaskStreamEntity;

import java.util.List;

public interface TaskStreamService {
    int insertTaskStream(TaskStreamEntity taskStreamEntity);

    List<TaskStreamEntity> getTaskStream(Long taskId, String type, String userName);

    int assignTaskStream(Long streamId, Long taskId, String type, String userName);

    int commitTaskStream(long task_id, Long streamId);

    List<TaskStreamEntity> getUnFinishedTaskStream(Long taskId, String type, String userName);

    List<TaskStreamEntity> getFinishedTaskStream(Long taskId);
}
