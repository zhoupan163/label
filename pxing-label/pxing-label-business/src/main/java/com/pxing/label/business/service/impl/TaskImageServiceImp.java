package com.pxing.label.business.service.impl;





import com.alibaba.fastjson.JSONArray;
import com.pxing.label.business.domain.dto.TaskImgDto;
import com.pxing.label.business.domain.entity.ImageEntity;
import com.pxing.label.business.domain.entity.ProjectStreamEntity;
import com.pxing.label.business.domain.entity.TaskImageEntity;
import com.pxing.label.business.service.ImageService;
import com.pxing.label.business.service.TaskDetailService;
import com.pxing.label.business.service.TaskImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class TaskImageServiceImp implements TaskImageService {

    @Autowired
    private ImageService imageService;

    @Autowired
    private TaskDetailService taskDetailService;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public void addTaskImages( String streamId, String taskName, String groupName) {
          List<ImageEntity> imageEntityList= imageService.selectImageListByStreamId(streamId);
          List<TaskImageEntity> taskImageEntityList =new ArrayList<>();

          for(ImageEntity imageEntity: imageEntityList){
              TaskImageEntity taskImageEntity=new TaskImageEntity();
              taskImageEntity.setTaskName(taskName);
              taskImageEntity.setStreamId(streamId);
              taskImageEntity.setImageId(imageEntity.getImageId());
              taskImageEntity.setJpgUrl(imageEntity.getJpgUrl());
              taskImageEntity.setPngUrl(imageEntity.getPngUrl());
              taskImageEntity.setHeight(imageEntity.getHeight());
              taskImageEntity.setWidth(imageEntity.getWidth());
              taskImageEntity.setStatus(0);
              taskImageEntity.setImageLock(0);
              taskImageEntity.setLabel("");
              taskImageEntity.setQa1("");
              taskImageEntity.setQa2("");
              taskImageEntity.setAnnotationInfo(new ArrayList<>());
              taskImageEntityList.add(taskImageEntity);
              taskImageEntity.setQaComment(new JSONArray());
          }
          mongoTemplate.insert(taskImageEntityList, TaskImageEntity.class);
    }

    @Override
    public int discardImage(String taskName, String streamId, String jpgUrl) {
        Query query=Query.query(Criteria.where("task_name").is(taskName).and("jpg_url").is(jpgUrl));
        Update update= new Update();
        update.set("status", 5);
        int a= (int)mongoTemplate.updateFirst(query,update, TaskImageEntity.class).getModifiedCount();
        if(a>0){
           taskDetailService.discardImage(taskName, a);
        }
        return a;
    }

    @Override
    public List<TaskImageEntity> getFinishedImageList(String taskName, List<String> streamIdList) {
        Query query=Query.query(Criteria.where("task_name").is(taskName).and("status").is(3).and("stream_id").in(streamIdList));
        return mongoTemplate.find(query, TaskImageEntity.class);
    }

    @Override
    public List<TaskImageEntity> getTaskImageEntityList(String taskName, String streamId) {
        Query query=Query.query(Criteria.where("task_name").is(taskName).and("stream_id").in(streamId));
        return mongoTemplate.find(query, TaskImageEntity.class);
    }

    @Override
    public int pullTaskImage(String taskName, String type, Integer number, String userName) {
        Criteria criteria= Criteria.where("task_name").is(taskName).and(type).is("");
        if(type!=null && type.equals("label")){
            criteria.and("status").is(0);
        }else if(type!=null && type.equals("qa1")){
            criteria.and("status").is(1);
        }else if(type!=null && type.equals("qa2")){
            criteria.and("status").is(2);
        }
        Query query=Query.query(criteria).limit(number);
        Update update= new Update();
        update.set(type, userName);
        int count=0;
        List<TaskImageEntity> taskImageEntityList= mongoTemplate.find(query, TaskImageEntity.class);
        for(int i=0; i< number; i++){
            count+= (int)mongoTemplate.updateFirst(query, update ,TaskImageEntity.class).getModifiedCount();
        }
        return count;
    }

    @Override
    public int checkTaskImage(String taskName, String type, String username) {
        Criteria criteria= Criteria.where("task_name").is(taskName).and(type).is(username);
        if(type!=null && type.equals("label")){
            criteria.and("status").in(0,4);
        }else if(type!=null && type.equals("qa1")){
            criteria.and("status").is(1);
        }else if(type!=null && type.equals("qa2")){
            criteria.and("status").is(2);
        }

        Query query=Query.query(criteria);
        return (int)mongoTemplate.count(query, TaskImageEntity.class);
    }

    @Override
    public Map<String,Object> getTaskImageEntityList(TaskImgDto taskImgDto) {
        Criteria criteria= Criteria.where("_id").nin("");
        if (taskImgDto.getTaskName()!= null){
            criteria.and("task_name").regex(taskImgDto.getTaskName());
        };
        if(taskImgDto.getImageId()!=null){
            criteria.and("image_id").regex(taskImgDto.getImageId());
        };
        if(taskImgDto.getStreamId()!=null){
            criteria.and("stream_id").regex(taskImgDto.getStreamId());
        };
        if(taskImgDto.getStatus()!=null){
            criteria.and("status").is(taskImgDto.getStatus());
        };
        if(taskImgDto.getLabel()!=null){
            criteria.and("label").regex(taskImgDto.getLabel());
        };
        if(taskImgDto.getQa1()!=null){
            criteria.and("qa1").regex(taskImgDto.getQa1());
        };
        if(taskImgDto.getQa2()!=null){
            criteria.and("qa2").regex(taskImgDto.getQa2());
        };
        Map<String,Object> map= new HashMap<>();
        Query query=Query.query(criteria).limit(taskImgDto.getPageSize()).skip( (taskImgDto.getPageNum() -1)*
                taskImgDto.getPageSize());
        map.put("list",  mongoTemplate.find(query, TaskImageEntity.class));
        map.put("total", mongoTemplate.count(Query.query(criteria), TaskImageEntity.class));
        return map;
    }
}
