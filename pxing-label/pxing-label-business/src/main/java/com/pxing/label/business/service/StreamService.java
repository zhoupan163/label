package com.pxing.label.business.service;


import com.pxing.label.business.domain.entity.ProjectStreamEntity;
import com.pxing.label.business.domain.entity.StreamEntity;

import java.util.List;

public interface StreamService {

    List<ProjectStreamEntity> selectStreamList();

    int updateTagStatusById(String streamId);
}
