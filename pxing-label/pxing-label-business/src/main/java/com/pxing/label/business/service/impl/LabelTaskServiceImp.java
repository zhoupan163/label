package com.pxing.label.business.service.impl;


import com.pxing.label.business.dao.LabelTaskDao;
import com.pxing.label.business.domain.vo.LabelTaskViaVo;
import com.pxing.label.business.domain.vo.LabelTaskVo;
import com.pxing.label.business.service.LabelTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDbFactory;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class LabelTaskServiceImp implements LabelTaskService {

    @Autowired
    private LabelTaskDao labelTaskDao;

    @Resource
    private MongoTemplate mongoTemplate;

    @Override
    public List<LabelTaskVo> selectLabelTaskList(LabelTaskVo labelTaskVo) {
        List<LabelTaskVo> list=labelTaskDao.selectLabelTaskList(labelTaskVo);
        return list;
    }

    @Override
    public  List<LabelTaskViaVo>selectLabelTaskViaInfo(String taskId) {
        //MongoOperations mongoOps = new MongoTemplate(new SimpleMongoClientDbFactory(MongoClients.create("mongodb://pca:5%Trv3@10.66.33.112:27017/?authSource=admin"), "px_pca"));
       Query query=Query.query(Criteria.where("taskId").is(taskId));
       LabelTaskViaVo labelTaskViaVo= mongoTemplate.findOne(query,LabelTaskViaVo.class);
        //LabelTaskViaVo labelTaskViaVo= mongoOps.findOne(query,LabelTaskViaVo.class);
        List<LabelTaskViaVo> list= new ArrayList<>();
       list.add(labelTaskViaVo);
        return list;
    }
}
