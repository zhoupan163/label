package com.pxing.label.business.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.pxing.label.business.dao.StreamDao;
import com.pxing.label.business.domain.entity.ProjectStreamEntity;
import com.pxing.label.business.domain.entity.StreamEntity;
import com.pxing.label.business.service.StreamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class StreamServiceImp implements StreamService {

    @Autowired
    private StreamDao streamDao;

    @Autowired
    private MongoTemplate mongoTemplate;
    @Override
    public List<ProjectStreamEntity> selectStreamList() {
          //待优化查询条件
          Query query=Query.query(Criteria.where("frame_size").nin(0));
          mongoTemplate.find(query, ProjectStreamEntity.class);
        return mongoTemplate.find(query, ProjectStreamEntity.class);
    }

    @Override
    public int updateTagStatusById(String streamId) {
        Query query=Query.query(Criteria.where("stream_id").is(streamId));
        Update update= new Update();
        update.set("tag_status", 1);
        return (int) mongoTemplate.updateMulti(query, update, ProjectStreamEntity.class).getModifiedCount();
    }
}
