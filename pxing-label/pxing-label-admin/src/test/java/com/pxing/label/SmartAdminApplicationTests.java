package com.pxing.label;


import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.mongodb.client.result.UpdateResult;
import com.pxing.label.business.dao.ImageDao;
import com.pxing.label.business.dao.StreamDao;
import com.pxing.label.business.dao.TaskStreamDao;
import com.pxing.label.business.domain.entity.*;
import com.pxing.label.business.domain.vo.LabelTaskImageVo;
import com.pxing.label.business.domain.vo.LabelTaskViaVo;
import com.pxing.label.business.service.LabelStreamService;
import com.pxing.label.business.service.LabelTaskService;
import com.pxing.label.business.service.LabelViaService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = PxingLabelApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SmartAdminApplicationTests {

    @Autowired
    MongoTemplate mongoTemplate;

    @Autowired
    private LabelTaskService labelTaskService;

    @Autowired
    private LabelViaService labelViaService;

    @Autowired
    private TaskStreamDao taskStreamDao;
    @Test
    public  void test1(){
        LabelTaskViaVo labelTaskViaVo= new LabelTaskViaVo();
       // labelTaskViaVo.settaskName("1001");
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("key1","values");
        labelTaskViaVo.setVia_project_info(jsonObject);
        //UpdateResult updateResult=labelTaskService.updateLabelTaskViaInfo(labelTaskViaVo);
    }

    @Test
    public  void test2(){
        LabelTaskViaVo labelTaskViaVo= new LabelTaskViaVo();
        //labelTaskViaVo.settaskName("888");
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
        //labelTaskService.insertLabelTaskViaInfo(new LabelTaskViaVo());
    }


    @Test
    public  void test4(){
        Query query1=Query.query(Criteria.where("jpg_url").is
                ("http://10.66.33.113:8080/pl/dataset/jpg/camera_body_front_up_rgb/20210804/9ec51a6e-0994-11ec-92e8-000c293913c8/a076fb18-0994-11ec-92e8-000c293913c8.jpg"));
        List<TaskImageEntity> taskImageEntityList= mongoTemplate.find(query1 , TaskImageEntity.class);
        System.out.println("ok");

        //UpdateWrapper<TaskStreamEntity> updateWrapper= new UpdateWrapper<>();
        //updateWrapper.eq("task_name", "行人跟踪").eq("stream_id", "9ec51a6e-0994-11ec-92e8-000c293913c8").set("label", "admin");
        //int a= taskStreamDao.update(null, updateWrapper);
       // System.out.println("ok");
    }

    @Test
    public  void test5(){
        String taskName="pedestrian_reid";
        String streamId="4134cf44-ffd4-11eb-92e8-000c293913c8";
        //labelViaService.insertSreamViaProject(streamId,taskName);

    }

    @Test
    public  void test6(){
        String taskName="pedestrian_reid";

       // labelViaService.getSreamViaProject((long) 1, (long) 1,"admin", "label");
    }

    @Test
    public void test7(){
        Query query=Query.query(Criteria.where("stream_id").is("4134cf44-ffd4-11eb-92e8-000c293913c8"));
        Update update=new Update();
        update.set("image_status", 0);
        update.set("annotationInfo", "");
        update.set("qa_comment", "");
        update.set("label","");
        update.set("image_lock",0);
        update.set("qa1","");
        update.set("qa2","");
        UpdateResult updateResult= mongoTemplate.updateMulti(query, update ,LabelTaskImageVo.class);
        System.out.println(updateResult.toString());
    }

    @Autowired
    private LabelStreamService labelStreamService;



    @Test
    public void test10(){
        //pedestrian_reid
        Query query=Query.query(Criteria.where("frame_size").is(113));
        List<ProjectStreamEntity> list= mongoTemplate.find(query, ProjectStreamEntity.class);
        System.out.println("aa");
    }

    @Autowired
    private ImageDao imageDao;
    @Autowired
    private StreamDao streamDao;
    @Test
    public  void test11(){
        Query query=Query.query(Criteria.where("image_status").nin(10)).with(Sort.by("stream_id"));
        List<LabelTaskImageVo> list=mongoTemplate.find(query, LabelTaskImageVo.class);
        Set<String> set= new HashSet<>();

        String streamId= "";
        long id=0;

        for(LabelTaskImageVo labelTaskImageVo : list){
            if(!labelTaskImageVo.getStream_id().equals(streamId)){
                streamId= labelTaskImageVo.getStream_id();

                StreamEntity streamEntity =new StreamEntity();
                streamEntity.setCreateBy("admin");
                streamEntity.setSensorLocation(labelTaskImageVo.getSensor_location());
                streamEntity.setTopicType(labelTaskImageVo.getTopic_type());
                streamEntity.setSensorType(labelTaskImageVo.getSensor_type());
                streamEntity.setWidth(labelTaskImageVo.getWidth());
                streamEntity.setHeight(labelTaskImageVo.getHeight());
                streamEntity.setTagStatus(0);

                streamDao.insert(streamEntity);
                id= streamEntity.getId();
                System.out.println(streamEntity.getId());
            };
            ImageEntity imageEntity= new ImageEntity();

           // imageEntity.setStreamId(id);
            imageEntity.setFrameIndex(labelTaskImageVo.getFrame_index());
            imageEntity.setSensorLocation(labelTaskImageVo.getSensor_location());
            imageEntity.setSensorType(labelTaskImageVo.getSensor_type());
            imageEntity.setTopicType(labelTaskImageVo.getTopic_type());
            imageEntity.setWidth(labelTaskImageVo.getWidth());
            imageEntity.setHeight(labelTaskImageVo.getHeight());
            imageEntity.setJpgUrl(labelTaskImageVo.getJpg_url());
            imageEntity.setPngUrl(labelTaskImageVo.getPng_url());
            imageEntity.setThumbnailUrl(labelTaskImageVo.getThumbnail_url());
            //imageEntity.setCreateBy("admin");
            imageDao.insert(imageEntity);
         }
    }


}




