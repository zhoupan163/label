package com.pxing.label.business.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.pxing.label.business.dao.ImageDao;
import com.pxing.label.business.domain.entity.ImageEntity;
import com.pxing.label.business.domain.entity.StreamEntity;
import com.pxing.label.business.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ImageServiceImp implements ImageService {

    @Autowired
    private ImageDao imageDao;

    @Override
    public List<ImageEntity> selectImageListByStreamId(Long streamId) {
        QueryWrapper<ImageEntity> querywrapper = new QueryWrapper<>();
        ImageEntity imageEntity= new ImageEntity();
        imageEntity.setStreamId(streamId);
        querywrapper.setEntity(imageEntity);
        return imageDao.selectList(querywrapper);
    }
}
