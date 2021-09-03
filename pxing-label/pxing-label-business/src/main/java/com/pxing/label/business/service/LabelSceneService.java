package com.pxing.label.business.service;


import com.pxing.label.business.domain.entity.LabelProjectEntity;
import com.pxing.label.business.domain.entity.LabelSceneEntity;

import java.util.List;

public interface LabelSceneService {


    List<LabelSceneEntity> selectLabelSceneList(LabelSceneEntity labelScene);

    int insertLabelScene(LabelSceneEntity labelScene);

    int updateLabelScene(LabelSceneEntity labelScene);

    int deleteLabelSceneByIds(Long[] projectIds);
}
