package com.pxing.label.business.service.impl;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mongodb.client.result.UpdateResult;
import com.pxing.label.business.dao.LabelTaskDao;
import com.pxing.label.business.domain.vo.*;
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
        List<LabelTaskVo> list1=new ArrayList<>();
        List<LabelTaskVo> list= labelTaskDao.selectLabelTaskList(labelTaskVo);
        for(LabelTaskVo labelTaskVo1: list){
            Query query=Query.query(Criteria.where("task_name").is(labelTaskVo1.getTaskName()));
            long size= mongoTemplate.count(query, LabelTaskImageVo.class);
            Query query1= Query.query(Criteria.where("task_name").is(labelTaskVo1.getTaskName()).and("image_status").is("finished"));
            long finishedCount= mongoTemplate.count(query1, LabelTaskImageVo.class);
            labelTaskVo1.setSize(size);
            labelTaskVo1.setFinishedCount(finishedCount);

            list1.add(labelTaskVo1);
        }
        return list1;
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
    public List<LabelStreamVo> getLabelTaskStream(String taskName, Query query) {
        List<LabelTaskImageVo> list=mongoTemplate.find(query, LabelTaskImageVo.class);
        Map<String,Integer> map= new HashMap<>();
        for(LabelTaskImageVo labelTaskImageVo : list){
            String stream_id=labelTaskImageVo.getStream_id();
            if(map.containsKey(stream_id)){
                map.put(stream_id,map.get(stream_id)+1);
            }else {
                map.put(stream_id,1);
            }
        };
        List<LabelStreamVo> labelStreamVoList =new ArrayList<>();
        for(String key : map.keySet()){
             LabelStreamVo labelStreamVo = new LabelStreamVo();
             labelStreamVo.setStreamId(key);
             labelStreamVo.setSize(map.get(key));
             labelStreamVo.setTaskName(taskName);
             labelStreamVoList.add(labelStreamVo);
        };
        return labelStreamVoList;
    }

    @Override
    public void assignLabelTaskStream(String streamId, String userName, String type) {
        Query query=Query.query(Criteria.where("stream_id").is(streamId).and(type).is(""));
        Update update=new Update();
        update.set(type, userName);
        update.set("image_lock", 1);
        UpdateResult updateResult= mongoTemplate.updateMulti(query, update ,LabelTaskImageVo.class);
        System.out.println(updateResult.toString());
        }

    @Override
    public List<LabelTaskImageVo> getLabelTaskUnfinishedStream(String taskName, String userName) {
        Query query=Query.query(Criteria.where("task_name").is(taskName).and("label").is(userName).and("image_status").is("0"));
        List<LabelTaskImageVo> list=mongoTemplate.find(query, LabelTaskImageVo.class);
        return list;
    }

    @Override
    public void changeStreamStatus(LabelViaProjectVo labelViaProjectVo) {
        Query query=Query.query(Criteria.where("stream_id").is(labelViaProjectVo.getStream_id()));
        Update update=new Update();
        update.set("image_status", "1");
        update.set("image_lock", "0");
        UpdateResult updateResult= mongoTemplate.updateMulti(query, update ,LabelTaskImageVo.class);
        System.out.println(updateResult.toString());

    }

    @Override
    public List<LabelTaskImageVo> getFinishedImageList(String taskName) {
        Query query=Query.query(Criteria.where("task_name").is(taskName).and("image_status").is("3"));
        List<LabelTaskImageVo> list=mongoTemplate.find(query, LabelTaskImageVo.class);
        return list;
    }

    @Override
    public List<Update> updateLabelTaskImages(LabelViaProjectVo labelViaProjectVo) {
        JSONObject via_img_metadata= labelViaProjectVo.getVia_img_metadata();
        for (String jpgUrl : labelViaProjectVo.getVia_image_id_list()) {
             JSONArray regions= via_img_metadata.getJSONObject(jpgUrl).getJSONArray("regions");
             Query query = Query.query(Criteria.where("jpg_url").is(jpgUrl));
             Update update= new Update();
             update.set("annotationInfo", regions);
             mongoTemplate.updateFirst(query, update,LabelTaskImageVo.class);
        }
        return null;
    }

}
