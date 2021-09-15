package com.pxing.label.business.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.pxing.label.business.dao.ImageDao;
import com.pxing.label.business.domain.entity.ImageEntity;
import com.pxing.label.business.domain.entity.StreamEntity;
import com.pxing.label.business.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ImageServiceImp implements ImageService {

    @Autowired
    private ImageDao imageDao;

    @Autowired
    private MongoTemplate mongoTemplate;


    @Override
    public List<ImageEntity> selectImageListByStreamId(String streamId) {
        Query query= new Query(Criteria.where("stream_id").is(streamId));
        return mongoTemplate.find(query, ImageEntity.class);
    }
}
