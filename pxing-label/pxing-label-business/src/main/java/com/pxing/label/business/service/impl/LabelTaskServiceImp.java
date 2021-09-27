package com.pxing.label.business.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.pxing.label.business.dao.LabelTaskDao;
import com.pxing.label.business.domain.entity.TaskEntity;
import com.pxing.label.business.domain.vo.*;
import com.pxing.label.business.service.TaskDetailService;
import com.pxing.label.business.service.LabelTaskService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LabelTaskServiceImp implements LabelTaskService {

    @Autowired
    private LabelTaskDao labelTaskDao;

    @Autowired
    private TaskDetailService taskDetailService;

    @Override
    public List<TaskEntity> selectLabelTaskList(LabelTaskVo labelTaskVo) {

        return labelTaskDao.selectList(new QueryWrapper<>());
    }

    @Override
    public int insertTaskEntity(TaskEntity taskEntity) {
        labelTaskDao.insert(taskEntity);
        return taskDetailService.insertTaskDetail(taskEntity.getTaskName());
    }

}
