package com.pxing.label.business.service.impl;


import com.pxing.label.business.dao.StreamTagDao;
import com.pxing.label.business.domain.entity.StreamEntity;
import com.pxing.label.business.domain.entity.StreamTagEntity;
import com.pxing.label.business.domain.vo.StreamTagVo;
import com.pxing.label.business.service.StreamTagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class StreamTagServiceImp implements StreamTagService {

    @Autowired
   private StreamTagDao streamTagDao;

    @Override
    public int tagStreamList(Long streamId, List<Long> tagList) {
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
    public List<StreamTagVo> getTaggedStreamList(Long projectId, Long taskId) {
        return streamTagDao.getTaggedStreamList(projectId, taskId);
    }
}
