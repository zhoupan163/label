package com.pxing.label.business.service.impl;



import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.pxing.label.business.dao.VideoGroupDao;
import com.pxing.label.business.dao.VideoGroupIdDao;
import com.pxing.label.business.domain.entity.VideoGroupEntity;
import com.pxing.label.business.domain.entity.VideoGroupIdEntity;
import com.pxing.label.business.service.VideoGroupIdService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;


@Service
public class VideoGroupIdServiceImp implements VideoGroupIdService{
    @Autowired
    private VideoGroupIdDao videoGroupIdDao;

    @Value("${uploadPath}")
    private  String savePath;


    @Value("${uploadUrl}")
    private  String uploadUrl;



    @Override
    public List<VideoGroupIdEntity> selectVideoGroupIdList(String groupName) {
        QueryWrapper<VideoGroupIdEntity> queryWrapper= new QueryWrapper<>();
        return videoGroupIdDao.selectList(queryWrapper.eq("group_name", groupName));
    }

    @Override
    public int insertVideoGroupId(VideoGroupIdEntity videoGroupIdEntity) throws IOException {
        //String savePath = "/home/zhoup/ngnix/file/data/";
        String img_name= videoGroupIdEntity.getGroupName() +videoGroupIdEntity.getPersonId()+".jpg";
        videoGroupIdEntity.getFile().transferTo(new File(savePath + img_name));
        videoGroupIdEntity.setImgUrl(uploadUrl+ img_name);
        return videoGroupIdDao.insert(videoGroupIdEntity);
    }

    @Override
    public int deleteVideoGroupIdByIds(Long[] ids) {
        return videoGroupIdDao.deleteBatchIds(Arrays.asList(ids));
    }
}
