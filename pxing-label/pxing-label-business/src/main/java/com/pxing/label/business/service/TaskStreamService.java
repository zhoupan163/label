package com.pxing.label.business.service;


import com.pxing.label.business.domain.entity.TaskStreamEntity;

import java.util.List;

public interface TaskStreamService {
    int insertTaskStream(TaskStreamEntity taskStreamEntity);

    List<TaskStreamEntity> getTaskStream(String taskName,String groupName, String type, String userName);

    int assignTaskStream(String streamId, String taskName, String type, String userName);

    int commitTaskStream(String taskName, String streamId);

    List<TaskStreamEntity> getUnFinishedTaskStream(String taskName, String groupName,String type, String userName);

    List<TaskStreamEntity> getFinishedTaskStream(String taskName, String groupName);

    List<TaskStreamEntity> getTaskStreamList(String taskName);

    List<TaskStreamEntity> getTaskStreamListByIds(Long[] ids);

}
