package com.pxing.label.business.service;


import com.pxing.label.business.domain.entity.TagEntity;
import com.pxing.label.business.domain.vo.LabelTagVo;

import java.util.List;

public interface LabelTagService {
    List<TagEntity> selectLabelTagList(TagEntity labelTag);

    int insertLabelTag(TagEntity labelTag);

    int updateLabelTag(TagEntity labelTag);

    int deleteLabelTagByIds(Long[] projectIds);

    List<LabelTagVo> selectTagListByProjectId(Long projectId);
}
