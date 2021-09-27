package com.pxing.label.business.service;

import com.pxing.label.business.domain.entity.TaskEntity;
import com.pxing.label.business.domain.vo.*;



import java.util.List;


public interface LabelTaskService {
    List<TaskEntity> selectLabelTaskList(LabelTaskVo labelTask);

    int insertTaskEntity(TaskEntity taskEntity);

    int pullTaskImage(String taskName, String type, Integer number, String userName);
}
