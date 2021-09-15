package com.pxing.label.business.service;


import com.pxing.label.business.domain.entity.TaskStreamEntity;

import java.util.List;

public interface TaskStreamService {
    int insertTaskStream(TaskStreamEntity taskStreamEntity);

    List<TaskStreamEntity> getTaskStream(String taskName, String type, String userName);

    int assignTaskStream(String streamId, String taskName, String type, String userName);

    int commitTaskStream(String taskName, String streamId);

    List<TaskStreamEntity> getUnFinishedTaskStream(String taskName, String type, String userName);

    List<TaskStreamEntity> getFinishedTaskStream(String taskName);

    List<TaskStreamEntity> getTaskStreamList(String taskName);
}
