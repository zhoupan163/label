package com.pxing.label.business.service;


import com.pxing.label.business.domain.entity.LabelTagEntity;
import com.pxing.label.business.domain.vo.LabelTagVo;

import java.util.List;

public interface LabelTagService {
    List<LabelTagEntity> selectLabelTagList(LabelTagEntity labelTag);

    int insertLabelTag(LabelTagEntity labelTag);

    int updateLabelTag(LabelTagEntity labelTag);

    int deleteLabelTagByIds(Long[] projectIds);

    List<LabelTagVo> selectTagListByProjectId(Long projectId);
}
