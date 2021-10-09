package com.pxing.label.business.service;

import com.pxing.label.business.domain.dto.TaskGroupDto;
import com.pxing.label.business.domain.entity.VideoGroupEntity;

import java.util.List;


public interface VideoGroupService {

    List<VideoGroupEntity> selectVideoGroupList(TaskGroupDto taskGroupDto);

    int insertVideoGroup(VideoGroupEntity videoGroupEntity);
}
