package com.pxing.label.business.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.pxing.label.business.dao.StreamDao;
import com.pxing.label.business.domain.entity.StreamEntity;
import com.pxing.label.business.service.StreamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class StreamServiceImp implements StreamService {

    @Autowired
    private StreamDao streamDao;

    @Override
    public List<StreamEntity> selectStreamList() {
        QueryWrapper<StreamEntity> querywrapper = new QueryWrapper<>();

        return streamDao.selectList(querywrapper);
    }

    @Override
    public int updateTagStatusById(Long streamId) {
        StreamEntity streamEntity = new StreamEntity();
        streamEntity.setTagStatus(1);
        UpdateWrapper<StreamEntity> updateWrapper= new UpdateWrapper<>();
        updateWrapper.eq("id", streamId);
        updateWrapper.set("tag_status", 1);
        return streamDao.update(null , updateWrapper);
    }
}
