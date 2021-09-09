package com.pxing.label.business.service;


import com.pxing.label.business.domain.entity.TaskStreamEntity;

import java.util.List;

public interface TaskStreamService {
    int insertTaskStream(TaskStreamEntity taskStreamEntity);

    List<TaskStreamEntity> getTaskStream(Long taskId, String type, String userName);

    int assignTaskStream(String streamId, String taskId, String type, String userName);
}
