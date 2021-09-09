package com.pxing.label.business.service.impl;




import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.pxing.label.business.dao.TaskStreamDao;
import com.pxing.label.business.domain.entity.TaskStreamEntity;
import com.pxing.label.business.service.TaskStreamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class TaskStreamServiceImp implements TaskStreamService {

    @Autowired
    private TaskStreamDao taskStreamDao;

    @Override
    public int insertTaskStream(TaskStreamEntity taskStreamEntity) {
           taskStreamEntity.setStatus(0);
           taskStreamEntity.setSize(0);
        return taskStreamDao.insert(taskStreamEntity);
    }

    @Override
    public List<TaskStreamEntity> getTaskStream(Long taskId, String type, String userName) {
        QueryWrapper<TaskStreamEntity> querywrapper = new QueryWrapper<>();
        querywrapper.eq("task_id", taskId);
        querywrapper.eq(type, userName);
        return taskStreamDao.selectList(querywrapper.orderByDesc("status"));
    }

    @Override
    public int assignTaskStream(String streamId, String taskId, String type, String userName) {
        UpdateWrapper<TaskStreamEntity> updateWrapper= new UpdateWrapper<>();
        updateWrapper.eq("task_id", taskId).eq("stream_id", streamId).set(type, userName);
        return taskStreamDao.update(null, updateWrapper);
    }

}
