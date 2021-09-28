package com.pxing.label.business.service.impl;





import com.pxing.label.business.domain.entity.ImageEntity;
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
import java.util.List;


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
        for(int i=0; i< number; i++){
            count+= (int)mongoTemplate.updateFirst(query, update ,TaskImageEntity.class).getModifiedCount();
        }
        return count;
    }

    @Override
    public int checkTaskImage(String taskName, String type, String username) {
        Criteria criteria= Criteria.where("task_name").is(taskName).and(type).is(username);
        if(type!=null && type.equals("label")){
            criteria.and("status").is(0);
        }else if(type!=null && type.equals("qa1")){
            criteria.and("status").is(1);
        }else if(type!=null && type.equals("qa1")){
            criteria.and("status").is(2);
        }

        Query query=Query.query(criteria);
        return (int)mongoTemplate.count(query, TaskImageEntity.class);
    }


}
