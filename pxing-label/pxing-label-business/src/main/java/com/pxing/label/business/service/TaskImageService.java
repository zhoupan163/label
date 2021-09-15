package com.pxing.label.business.service;


import com.pxing.label.business.domain.entity.TaskImageEntity;

import java.util.List;

public interface TaskImageService {

    void addTaskImages(String streamId, String taskName);

    int discardImage(String taskName, String streamId, String jpgUrl);

    List<TaskImageEntity> getFinishedImageList(String taskName, List<String> streamIdList);
}
