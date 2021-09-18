package com.pxing.label.business.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.pxing.label.business.dao.StreamTagDao;
import com.pxing.label.business.domain.entity.ProjectStreamEntity;
import com.pxing.label.business.domain.entity.StreamTagEntity;
import com.pxing.label.business.domain.entity.TaskStreamEntity;
import com.pxing.label.business.service.StreamTagService;
import com.pxing.label.business.service.TaskStreamService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.stream.Collectors;


@Service
public class StreamTagServiceImp implements StreamTagService {

    @Autowired
    private StreamTagDao streamTagDao;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private TaskStreamService taskStreamService;

    @Override
    public int tagStreamList(String streamId, List<Long> tagList) {
        int count= 0;
        for(long tagId : tagList){
            StreamTagEntity streamTagEntity= new StreamTagEntity();
            streamTagEntity.setTagId(tagId);
            streamTagEntity.setStreamId(streamId);
            count+= streamTagDao.insert(streamTagEntity);
            //streamTagEntityList.add(streamTagEntity);
        }
        return count;
    }

    @Override
    public List<ProjectStreamEntity> getTaggedStreamList(String projectName, String  taskName) {
        List<TaskStreamEntity> taskStreamEntityList= taskStreamService.getTaskStreamList(taskName);
        List<String> streamIdList= taskStreamEntityList.stream().map(TaskStreamEntity ::getStreamId).collect(Collectors.toList());
        Query query= new Query(Criteria.where("tag_status").is(1).and("stream_id").nin(streamIdList));
        return mongoTemplate.find(query, ProjectStreamEntity.class);
    }

    @Override
    public List<StreamTagEntity> getTagListBystream(String streamId) {
        QueryWrapper<StreamTagEntity> queryWrapper= new QueryWrapper<>();
        queryWrapper.eq("stream_id", streamId).select("tag_id");
        return streamTagDao.selectList(queryWrapper);
    }

    @Override
    public int updateTagStream(String streamId, List<Long> tagList) {
        QueryWrapper<StreamTagEntity> queryWrapper= new QueryWrapper<>();
        queryWrapper.eq("stream_id", streamId);
        streamTagDao.delete(queryWrapper);

        return tagStreamList(streamId, tagList);
    }

}
