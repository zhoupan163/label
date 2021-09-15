package com.pxing.label.business.service.impl;





import com.pxing.label.business.domain.entity.ImageEntity;
import com.pxing.label.business.domain.entity.TaskImageEntity;
import com.pxing.label.business.domain.entity.TaskStreamEntity;
import com.pxing.label.business.service.ImageService;
import com.pxing.label.business.service.TaskImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;


@Service
public class TaskImageServiceImp implements TaskImageService {

    @Autowired
    private ImageService imageService;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public void addTaskImages(String streamId, String taskName) {
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
        Query query=Query.query(Criteria.where("task_name").is(taskName).and("status").is(0).and("stream_id").is(streamId).and("jpg_url").is(jpgUrl));
        Update update= new Update();
        update.set("status", 5);
        return (int)mongoTemplate.updateFirst(query,update, TaskImageEntity.class).getModifiedCount();
    }

    @Override
    public List<TaskImageEntity> getFinishedImageList(String taskName, List<String> streamIdList) {
        Query query=Query.query(Criteria.where("task_name").is(taskName).and("status").is(3).and("stream_id").in(streamIdList));
        return mongoTemplate.find(query, TaskImageEntity.class);
    }
}
