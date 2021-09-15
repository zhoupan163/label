package com.pxing.label.business.service.impl;



import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.pxing.label.business.dao.VideoGroupDao;
import com.pxing.label.business.domain.entity.VideoGroupEntity;
import com.pxing.label.business.service.VideoGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class VideoGroupServiceImp implements VideoGroupService {
    @Autowired
    private VideoGroupDao videoGroupDao;
    @Override
    public List<VideoGroupEntity> selectVideoGroupList() {

        return videoGroupDao.selectList(new QueryWrapper<>());
    }

    @Override
    public int insertVideoGroup(VideoGroupEntity videoGroupEntity) {
        return videoGroupDao.insert(videoGroupEntity);
    }
}
