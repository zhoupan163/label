package com.pxing.label.business.service;


import com.pxing.label.business.domain.dto.ProjectStreamDto;
import com.pxing.label.business.domain.entity.ProjectStreamEntity;

import java.util.List;

public interface StreamService {

    List<ProjectStreamEntity> selectStreamList(ProjectStreamDto projectStreamDto);

    int updateTagStatusById(String streamId);
}
