package com.pxing.label.business.service;

import com.pxing.label.business.domain.entity.VideoGroupEntity;
import com.pxing.label.business.domain.entity.VideoGroupIdEntity;

import java.io.IOException;
import java.util.List;


public interface VideoGroupIdService {

    List<VideoGroupIdEntity> selectVideoGroupIdList(String groupName);

    int insertVideoGroupId(VideoGroupIdEntity videoGroupIdEntity) throws IOException;
}
