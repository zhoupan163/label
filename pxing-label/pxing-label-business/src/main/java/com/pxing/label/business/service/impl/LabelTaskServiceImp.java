package com.pxing.label.business.service.impl;


import com.pxing.label.business.dao.LabelTaskDao;
import com.pxing.label.business.domain.vo.*;
import com.pxing.label.business.service.LabelStreamService;
import com.pxing.label.business.service.LabelTaskService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class LabelTaskServiceImp implements LabelTaskService {

    @Autowired
    private LabelTaskDao labelTaskDao;

    @Resource
    private MongoTemplate mongoTemplate;

    @Autowired
    private LabelStreamService labelStreamService;

    @Override
    public List<LabelTaskVo> selectLabelTaskList(LabelTaskVo labelTaskVo) {

        return labelTaskDao.selectLabelTaskList(labelTaskVo);
    }

}
