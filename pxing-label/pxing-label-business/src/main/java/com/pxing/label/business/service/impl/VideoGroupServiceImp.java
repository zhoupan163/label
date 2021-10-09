package com.pxing.label.business.service.impl;



import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.pxing.label.business.dao.VideoGroupDao;
import com.pxing.label.business.domain.dto.TaskGroupDto;
import com.pxing.label.business.domain.entity.VideoGroupEntity;
import com.pxing.label.business.service.VideoGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class VideoGroupServiceImp implements VideoGroupService {
    @Autowired
    private VideoGroupDao videoGroupDao;
    @Override
    public List<VideoGroupEntity> selectVideoGroupList(TaskGroupDto taskGroupDto) {
        System.out.println(taskGroupDto.getDateRange());
        QueryWrapper queryWrapper= new QueryWrapper();
        if (taskGroupDto.getGroupName()!=null) {
            queryWrapper.like("group_name", taskGroupDto.getGroupName());
        };
        if (taskGroupDto.getTaskName()!=null) {
            queryWrapper.like("task_name", taskGroupDto.getTaskName());
        };
        if (taskGroupDto.getDateRange()!=null && taskGroupDto.getDateRange().size()>0) {
            queryWrapper.ge("create_time", taskGroupDto.getDateRange().get(0));
            queryWrapper.le("create_time", taskGroupDto.getDateRange().get(1));
        };
        return videoGroupDao.selectList(queryWrapper);
    }

    @Override
    public int insertVideoGroup(VideoGroupEntity videoGroupEntity) {
        return videoGroupDao.insert(videoGroupEntity);
    }
}
