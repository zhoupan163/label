package com.pxing.label.business.service;


import com.pxing.label.business.domain.vo.StreamTagVo;

import java.util.List;

public interface StreamTagService {

    int tagStreamList(Long streamId, List<Long> tagList);

    List<StreamTagVo> getTaggedStreamList(Long projectId, Long taskId);
}
