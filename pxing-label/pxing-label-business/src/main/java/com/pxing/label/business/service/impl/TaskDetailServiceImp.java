package com.pxing.label.business.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.microsoft.schemas.vml.STExt;
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
        if(type!=null && type.equals("label")){
            return taskDetailDao.pullTaskImage4Label(taskName, number);
        }else if(type!=null && type.equals("qa1")){
            return taskDetailDao.pullTaskImage4Qa1(taskName, number);
        }else if(type!=null && type.equals("qa2")){
            return taskDetailDao.pullTaskImage4Qa2(taskName, number);
        }
        return 0;
    }

    @Override
    public int discardImage(String taskName, Integer number) {

        return taskDetailDao.discardImage(taskName, number);
    }

    @Override
    public int commitLabeled(String taskName, Integer number) {
        return taskDetailDao.commitLabeled(taskName, number);
    }

    @Override
    public int commitQa1(String taskName, Integer number) {
        return taskDetailDao.commitQa1(taskName, number);
    }

    @Override
    public int commitQa2(String taskName, int passCount) {
        return taskDetailDao.commitQa2(taskName, passCount);
    }
}
