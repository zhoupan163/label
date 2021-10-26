package com.pxing.label.business.service;


import com.pxing.label.business.domain.dto.ProjectStreamDto;
import com.pxing.label.business.domain.entity.ProjectStreamEntity;

import java.util.List;
import java.util.Map;

public interface StreamService {


    Map<String, Object> selectStreamList(ProjectStreamDto projectStreamDto);

    int updateTagStatusById(String streamId);
}
