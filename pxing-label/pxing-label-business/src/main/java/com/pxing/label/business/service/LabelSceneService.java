package com.pxing.label.business.service;


import com.pxing.label.business.domain.entity.SceneEntity;

import java.util.List;

public interface LabelSceneService {


    List<SceneEntity> selectLabelSceneList(SceneEntity labelScene);

    int insertLabelScene(SceneEntity labelScene);

    int updateLabelScene(SceneEntity labelScene);

    int deleteLabelSceneByIds(Long[] projectIds);
}
