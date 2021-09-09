package com.pxing.label.business.service.impl;





import com.pxing.label.business.domain.entity.ImageEntity;
import com.pxing.label.business.domain.entity.TaskImageEntity;
import com.pxing.label.business.domain.entity.TaskStreamEntity;
import com.pxing.label.business.service.ImageService;
import com.pxing.label.business.service.TaskImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
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
    public void addTaskImages(Long streamId, Long taskId) {
          List<ImageEntity> imageEntityList= imageService.selectImageListByStreamId(streamId);
          List<TaskImageEntity> taskImageEntityList =new ArrayList<>();

          for(ImageEntity imageEntity: imageEntityList){
              TaskImageEntity taskImageEntity=new TaskImageEntity();
              taskImageEntity.setTask_id(taskId);
              taskImageEntity.setStream_id(streamId);
              taskImageEntity.setImageId(imageEntity.getId());
              taskImageEntity.setStatus(0);
              taskImageEntity.setImage_lock(0);
              taskImageEntityList.add(taskImageEntity);
          }

          mongoTemplate.insert(taskImageEntityList, TaskImageEntity.class);

    }
}
