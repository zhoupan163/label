package com.pxing.label.business.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.pxing.label.business.dao.StreamDao;
import com.pxing.label.business.domain.entity.LabelProjectEntity;
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
}
