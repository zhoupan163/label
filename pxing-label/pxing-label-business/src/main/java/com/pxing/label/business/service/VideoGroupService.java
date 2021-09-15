package com.pxing.label.business.service;

import com.pxing.label.business.domain.entity.TaskEntity;
import com.pxing.label.business.domain.entity.VideoGroupEntity;
import com.pxing.label.business.domain.vo.LabelTaskVo;

import java.util.List;


public interface VideoGroupService {

    List<VideoGroupEntity> selectVideoGroupList();

    int insertVideoGroup(VideoGroupEntity videoGroupEntity);
}
