package com.pxing.label.business.service.impl;

import com.pxing.label.business.dao.StreamDao;
import com.pxing.label.business.domain.dto.ProjectStreamDto;
import com.pxing.label.business.domain.entity.ProjectStreamEntity;
import com.pxing.label.business.service.StreamService;
import com.pxing.label.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.data.domain.PageRequest;
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
    public List<ProjectStreamEntity> selectStreamList(ProjectStreamDto projectStreamDto) {
          //待优化查询条件
          Criteria criteria= Criteria.where("frame_size").nin(0);
          if (projectStreamDto.getProjectName()!=null){
              criteria.and("project_name").is(projectStreamDto.getProjectName());
          };
          if(projectStreamDto.getSensorLocation()!=null){
              criteria.and("sensor_location").regex(projectStreamDto.getSensorLocation());
          };
        if(projectStreamDto.getRemark()!=null){
            criteria.and("comment").regex(projectStreamDto.getRemark());
        };
        if(projectStreamDto.getTagStatus()!=null){
            criteria.and("tag_status").is(projectStreamDto.getTagStatus());
        };
        if(projectStreamDto.getDateRange().size()>0){
            System.out.println(DateUtils.getTimeStamp(projectStreamDto.getDateRange().get(0)));
            System.out.println(DateUtils.getTimeStamp(projectStreamDto.getDateRange().get(1)));
            criteria.and("update_time").gte(DateUtils.getTimeStamp(projectStreamDto.getDateRange().get(0))).lte
                    (DateUtils.getTimeStamp(projectStreamDto.getDateRange().get(1)));
        };
        Query query=Query.query(criteria);
          //Query query=Query.query(criteria).with(PageRequest.of(projectStreamDto.getPageNum()* projectStreamDto.getPageSize(),
             //     projectStreamDto.getPageSize()));
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
