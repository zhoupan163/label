package com.pxing.label.business.service;


import com.pxing.label.business.domain.entity.ProjectStreamEntity;
import com.pxing.label.business.domain.vo.StreamTagVo;

import java.util.List;

public interface StreamTagService {

    int tagStreamList(String streamId, List<Long> tagList);

    List<ProjectStreamEntity> getTaggedStreamList(String projectName, String  taskName);
}
