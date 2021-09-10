package com.pxing.label.business.service;


import com.pxing.label.business.domain.entity.TaskImageEntity;

import java.util.List;

public interface TaskImageService {

    void addTaskImages(Long streamId, Long taskId);

    int discardImage(Long taskId, Long streamId, String jpgUrl);

    List<TaskImageEntity> getFinishedImageList(Long taskId, List<Long> streamIdList);
}
