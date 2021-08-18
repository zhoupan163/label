package com.pxing.label.business.service.impl;


import com.mongodb.client.result.UpdateResult;
import com.pxing.label.business.dao.LabelTaskDao;
import com.pxing.label.business.domain.vo.LabelTaskImageVo;
import com.pxing.label.business.domain.vo.LabelTaskStreamVo;
import com.pxing.label.business.domain.vo.LabelTaskViaVo;
import com.pxing.label.business.domain.vo.LabelTaskVo;
import com.pxing.label.business.service.LabelTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public  List<LabelTaskViaVo> selectLabelTaskViaInfo(String taskId) {
        //MongoOperations mongoOps = new MongoTemplate(new SimpleMongoClientDbFactory(MongoClients.create("mongodb://pca:5%Trv3@10.66.33.112:27017/?authSource=admin"), "px_pca"));
       Query query=Query.query(Criteria.where("taskId").is(taskId));
       LabelTaskViaVo labelTaskViaVo= mongoTemplate.findOne(query,LabelTaskViaVo.class);
        //LabelTaskViaVo labelTaskViaVo= mongoOps.findOne(query,LabelTaskViaVo.class);
        System.out.println(labelTaskViaVo.toString());
        List<LabelTaskViaVo> list= new ArrayList<>();
        list.add(labelTaskViaVo);
        return list;
    }

    @Override
    public UpdateResult updateLabelTaskViaInfo(LabelTaskViaVo labelTaskViaVo) {
        Query query=Query.query(Criteria.where("taskId").is(String.valueOf(labelTaskViaVo.getTaskId())));
        Update update=new Update();
        update.set("via_project_info", labelTaskViaVo.getVia_project_info());
        UpdateResult updateResult= mongoTemplate.updateFirst(query, update ,LabelTaskViaVo.class);
        System.out.println(updateResult);
        return updateResult;
    }

    @Override
    public void insertLabelTaskViaInfo(LabelTaskViaVo labelTaskViaVo) {
        List<LabelTaskViaVo> list=selectLabelTaskViaInfo("1001");
        LabelTaskViaVo labelTaskViaVo1=list.get(0);
        labelTaskViaVo1.setTaskId("888");
        mongoTemplate.insert(labelTaskViaVo1);

    }

    @Override
    public void assiginTask(String taskId,String LabelBy) {
        labelTaskDao.updateLabelBy(taskId, LabelBy);

    }

    @Override
    public void labelTask(String taskId, String userName) {

    }

    @Override
    public List<LabelTaskStreamVo> getLabelTaskStream(String taskName) {
        Query query1=Query.query(Criteria.where("task_name").is(taskName).and("label").is(""));
        List<LabelTaskImageVo> list=mongoTemplate.find(query1, LabelTaskImageVo.class);
        Map<String,Integer> map= new HashMap<>();

        for(LabelTaskImageVo labelTaskImageVo : list){
            String stream_id=labelTaskImageVo.getStream_id();
            if(map.containsKey(stream_id)){
                map.put(stream_id,map.get(stream_id)+1);
            }else {
                map.put(stream_id,1);
            }
        };
        List<LabelTaskStreamVo> labelTaskStreamVoList=new ArrayList<>();
        for(String key : map.keySet()){
             LabelTaskStreamVo labelTaskStreamVo= new LabelTaskStreamVo();
             labelTaskStreamVo.setStreamId(key);
             labelTaskStreamVo.setSize(map.get(key));
             labelTaskStreamVo.setTaskName(taskName);
             labelTaskStreamVoList.add(labelTaskStreamVo);
        };


        return labelTaskStreamVoList;
    }

    @Override
    public void assignLabelTaskStream(String streamId, String userName) {
        Query query=Query.query(Criteria.where("stream_id").is(streamId).and("label").is(""));
        Update update=new Update();
        update.set("label", userName );
        update.set("image_lock", 1);
        UpdateResult updateResult= mongoTemplate.updateMulti(query, update ,LabelTaskImageVo.class);
        System.out.println(updateResult);
        }

}
