package com.pxing.label.business.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.pxing.label.business.dao.TaskDetailDao;
import com.pxing.label.business.domain.entity.TaskDetailEntity;
import com.pxing.label.business.service.TaskDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaskDetailServiceImp implements TaskDetailService {

    @Autowired
    private TaskDetailDao taskDetailDao;

    @Override
    public TaskDetailEntity getTaskDetail(String taskName) {
        QueryWrapper<TaskDetailEntity> queryWrapper= new QueryWrapper<>();
        queryWrapper.eq("task_name", taskName);
        return taskDetailDao.selectOne(queryWrapper);
    }

    @Override
    public int insertTaskDetail(String taskName) {
        TaskDetailEntity taskDetailEntity= new TaskDetailEntity();
        taskDetailEntity.setTaskName(taskName);
        return taskDetailDao.insert(taskDetailEntity);
    }

    @Override
    public int updateTaskDetailTotal(Integer frameSize, String taskName) {

        return taskDetailDao.updateTotal(frameSize, taskName);
    }

    @Override
    public int updateTaskDetail(String taskName, String type, Integer number) {
        UpdateWrapper<TaskDetailEntity> updateWrapper =new UpdateWrapper<>();
        updateWrapper.eq("task_name", taskName);
        if(type.equals("label")){
            return taskDetailDao.pullTaskImage4Label(taskName, number);
        }
        return 0;
    }
}