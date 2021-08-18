package com.pxing.label;


import com.alibaba.fastjson.JSONObject;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.client.result.UpdateResult;
import com.pxing.label.business.domain.vo.LabelTaskImageVo;
import com.pxing.label.business.domain.vo.LabelTaskViaVo;
import com.pxing.label.business.domain.vo.LabelViaProjectVo;
import com.pxing.label.business.service.LabelTaskService;
import com.pxing.label.business.service.LabelViaService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = PxingLabelApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SmartAdminApplicationTests {

    @Autowired
    MongoTemplate mongoTemplate;

    @Autowired
    private LabelTaskService labelTaskService;

    @Autowired
    private LabelViaService labelViaService;
    @Test
    public  void test1(){
        LabelTaskViaVo labelTaskViaVo= new LabelTaskViaVo();
        labelTaskViaVo.setTaskId("1001");
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("key1","values");
        labelTaskViaVo.setVia_project_info(jsonObject);
        UpdateResult updateResult=labelTaskService.updateLabelTaskViaInfo(labelTaskViaVo);
    }

    @Test
    public  void test2(){
        LabelTaskViaVo labelTaskViaVo= new LabelTaskViaVo();
        labelTaskViaVo.setTaskId("888");
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("key2","values2");
        labelTaskViaVo.setVia_project_info(jsonObject);
        labelTaskViaVo= mongoTemplate.insert(labelTaskViaVo);
        if (labelTaskViaVo != null) {
            System.out.println("success");
        } else {
            System.out.println("false");
        }
    }

    @Test
    public  void test3(){
        labelTaskService.insertLabelTaskViaInfo(new LabelTaskViaVo());
    }


    @Test
    public  void test4(){
        // 查询条件
        Query query=Query.query(Criteria.where("taskId").is("taskId"));
        LabelTaskViaVo labelTaskViaVo= mongoTemplate.findOne(query,LabelTaskViaVo.class);
       // Criteria query1= Criteria.where("task_name").is("pedestrian_reid");

        Query query1=Query.query(Criteria.where("task_name").is("pedestrian_reid"));
        List<LabelTaskImageVo> list=mongoTemplate.find(query1, LabelTaskImageVo.class);
        System.out.println(list.toString());
        for(LabelTaskImageVo labelTaskImageVo : list){
            Query query2=Query.query(Criteria.where("image_id").is(labelTaskImageVo.getImage_id()).and("task_name").is(labelTaskImageVo.getTask_name()));
            Update update=new Update();
            update.set("annotationInfo", labelTaskImageVo.getAnnotationInfo());
            UpdateResult updateResult= mongoTemplate.updateFirst(query2, update ,LabelTaskImageVo.class);
            System.out.println(updateResult);
        }
    }

    @Test
    public  void test5(){
        String taskName="pedestrian_reid";
        String streamId="4134cf44-ffd4-11eb-92e8-000c293913c8";
        labelViaService.insertSreamViaProject(streamId,taskName);

    }

    @Test
    public  void test6(){
        String taskName="pedestrian_reid";
        String streamId="4134cf44-ffd4-11eb-92e8-000c293913c8";
        labelViaService.getSreamViaProject(streamId);

    }



}
