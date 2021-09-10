package com.pxing.label.business.service.impl;



import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.pxing.label.business.dao.LabelSceneDao;
import com.pxing.label.business.domain.entity.SceneEntity;
import com.pxing.label.business.service.LabelSceneService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class LabelSceneServiceImp implements LabelSceneService {

    @Autowired
    private LabelSceneDao labelSceneDao;

    @Override
    public List<SceneEntity> selectLabelSceneList(SceneEntity labelScene) {
        QueryWrapper<SceneEntity> querywrapper = new QueryWrapper<>();

        return labelSceneDao.selectList(querywrapper);
    }

    @Override
    public int insertLabelScene(SceneEntity labelScene) {

        return labelSceneDao.insert(labelScene);
    }

    @Override
    public int updateLabelScene(SceneEntity labelScene) {
        return 0;
    }

    @Override
    public int deleteLabelSceneByIds(Long[] projectIds) {
        return 0;
    }
}
