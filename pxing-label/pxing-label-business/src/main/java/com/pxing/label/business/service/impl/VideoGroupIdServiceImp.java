package com.pxing.label.business.service.impl;



import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.pxing.label.business.dao.VideoGroupDao;
import com.pxing.label.business.dao.VideoGroupIdDao;
import com.pxing.label.business.domain.entity.VideoGroupEntity;
import com.pxing.label.business.domain.entity.VideoGroupIdEntity;
import com.pxing.label.business.service.VideoGroupIdService;
import com.pxing.label.business.service.VideoGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class VideoGroupIdServiceImp implements VideoGroupIdService{
    @Autowired
    private VideoGroupIdDao videoGroupIdDao;
    @Override
    public List<VideoGroupIdEntity> selectVideoGroupIdList() {

        return videoGroupIdDao.selectList(new QueryWrapper<>());
    }

    @Override
    public int insertVideoGroupId(VideoGroupIdEntity videoGroupIdEntity) {
        return videoGroupIdDao.insert(videoGroupIdEntity);
    }
}
