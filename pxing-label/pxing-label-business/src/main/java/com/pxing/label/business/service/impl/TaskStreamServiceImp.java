package com.pxing.label.business.service.impl;




import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.mongodb.client.result.UpdateResult;
import com.pxing.label.business.dao.TaskStreamDao;
import com.pxing.label.business.domain.entity.TaskImageEntity;
import com.pxing.label.business.domain.entity.TaskStreamEntity;
import com.pxing.label.business.service.TaskStreamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.LongStream;


@Service
public class TaskStreamServiceImp implements TaskStreamService {

    @Autowired
    private TaskStreamDao taskStreamDao;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public int insertTaskStream(TaskStreamEntity taskStreamEntity) {
           taskStreamEntity.setStatus(0);
           taskStreamEntity.setSize(0);
        return taskStreamDao.insert(taskStreamEntity);
    }

    @Override
    public List<TaskStreamEntity> getTaskStream(Long taskId, String type, String userName) {
        QueryWrapper<TaskStreamEntity> querywrapper = new QueryWrapper<>();
        if(type.equals("label")){
            querywrapper.eq(type, userName).eq("status", 0);
        }else if (type.equals("qa1")){
            querywrapper.eq(type, userName).eq("status", 1);
        }else{
            querywrapper.eq(type, userName).eq("status", 2);
        }
        return taskStreamDao.selectList(querywrapper);
    }

    @Override
    public int assignTaskStream(Long streamId, Long taskId, String type, String userName) {

        Query query=Query.query(Criteria.where("task_id").is(taskId).and("stream_id").is(streamId));
        Update update= new Update();
        update.set(type,userName);
        UpdateResult updateResult= mongoTemplate.updateMulti(query, update, TaskImageEntity.class);

        UpdateWrapper<TaskStreamEntity> updateWrapper= new UpdateWrapper<>();
        updateWrapper.eq("task_id", taskId).eq("stream_id", streamId).set(type, userName);
        return taskStreamDao.update(null, updateWrapper);
    }

    @Override
    public int commitTaskStream(long taskId, Long streamId) {
        UpdateWrapper<TaskStreamEntity> updateWrapper= new UpdateWrapper<>();
        updateWrapper.eq("task_id", taskId).eq("stream_id", streamId).set("status", 1);
        return taskStreamDao.update(null, updateWrapper);
    }

    @Override
    public List<TaskStreamEntity> getUnFinishedTaskStream(Long taskId, String type, String userName) {
        QueryWrapper<TaskStreamEntity> querywrapper = new QueryWrapper<>();
        querywrapper.eq("task_id", taskId);
        if(type.equals("label")){
            querywrapper.eq(type, userName).in("status", 0,4);
        }else if (type.equals("qa1")){
            querywrapper.eq(type, userName).eq("status", 1);
        }else{
            querywrapper.eq(type, userName).eq("status", 2);
        }
        return taskStreamDao.selectList(querywrapper.orderByDesc("status"));
    }

    @Override
    public List<TaskStreamEntity> getFinishedTaskStream(Long taskId) {
        QueryWrapper<TaskStreamEntity> querywrapper = new QueryWrapper<>();
        querywrapper.eq("task_id", taskId).eq("status", 3);
        return taskStreamDao.selectList(querywrapper);
    }

}
