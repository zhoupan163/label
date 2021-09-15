package com.pxing.label.business.service;

import com.pxing.label.business.domain.entity.VideoGroupEntity;
import com.pxing.label.business.domain.entity.VideoGroupIdEntity;

import java.util.List;


public interface VideoGroupIdService {

    List<VideoGroupIdEntity> selectVideoGroupIdList();

    int insertVideoGroupId(VideoGroupIdEntity videoGroupIdEntity);
}
