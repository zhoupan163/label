package com.pxing.label.business.service.impl;




import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.mongodb.client.result.UpdateResult;
import com.pxing.label.business.dao.TaskStreamDao;
import com.pxing.label.business.domain.entity.TaskImageEntity;
import com.pxing.label.business.domain.entity.TaskStreamEntity;
import com.pxing.label.business.service.TaskDetailService;
import com.pxing.label.business.service.TaskStreamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.stream.LongStream;


@Service
public class TaskStreamServiceImp implements TaskStreamService {

    @Autowired
    private TaskStreamDao taskStreamDao;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private TaskDetailService taskDetailService;

    @Override
    public int insertTaskStream(TaskStreamEntity taskStreamEntity) {
           taskStreamEntity.setStatus(0);
           taskDetailService.updateTaskDetailTotal(taskStreamEntity.getSize(), taskStreamEntity.getTaskName());
        return taskStreamDao.insert(taskStreamEntity);
    }

    @Override
    public List<TaskStreamEntity> getTaskStream(String taskName,String groupName, String type, String userName) {
        QueryWrapper<TaskStreamEntity> querywrapper = new QueryWrapper<>();
        querywrapper.eq("group_name", groupName).eq("task_name", taskName);
        if(type.equals("label")){
            querywrapper.eq(type, userName).eq("status", 0);
        }else if (type.equals("qa1")){
            querywrapper.eq(type, userName).eq("status", 1);
        }else if (type.equals("qa2")){
            querywrapper.eq(type, userName).eq("status", 2);
        }else{
            //预览功能  不做处理 返回所有数据
        }
        return taskStreamDao.selectList(querywrapper);
    }

    @Override
    public int assignTaskStream(String streamId, String taskName, String type, String userName) {

        Query query=Query.query(Criteria.where("task_name").is(taskName).and("stream_id").is(streamId));
        Update update= new Update();
        update.set(type,userName);
        UpdateResult updateResult= mongoTemplate.updateMulti(query, update, TaskImageEntity.class);

        UpdateWrapper<TaskStreamEntity> updateWrapper= new UpdateWrapper<>();
        updateWrapper.eq("task_name", taskName).eq("stream_id", streamId).set(type, userName);
        return taskStreamDao.update(null, updateWrapper);
    }

    @Override
    public int commitTaskStream(String taskName, String streamId) {
        UpdateWrapper<TaskStreamEntity> updateWrapper= new UpdateWrapper<>();
        updateWrapper.eq("task_name", taskName).eq("stream_id", streamId).set("status", 1);
        return taskStreamDao.update(null, updateWrapper);
    }

    @Override
    public List<TaskStreamEntity> getUnFinishedTaskStream(String taskName,String  groupName, String type, String userName) {
        QueryWrapper<TaskStreamEntity> querywrapper = new QueryWrapper<>();
        querywrapper.eq("task_name", taskName).eq("group_name", groupName);
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
    public List<TaskStreamEntity> getFinishedTaskStream(String taskName, String  groupName) {
        QueryWrapper<TaskStreamEntity> querywrapper = new QueryWrapper<>();
        querywrapper.eq("task_name", taskName).eq("group_name", groupName).eq("status", 3);
        return taskStreamDao.selectList(querywrapper);
    }

    @Override
    public List<TaskStreamEntity> getTaskStreamList(String taskName) {
        QueryWrapper<TaskStreamEntity> querywrapper = new QueryWrapper<>();
        querywrapper.select("stream_id").eq("task_name", taskName);
        return taskStreamDao.selectList(querywrapper);
    }

    @Override
    public List<TaskStreamEntity> getTaskStreamListByIds(Long[] ids) {
        QueryWrapper<TaskStreamEntity> querywrapper = new QueryWrapper<>();
        return taskStreamDao.selectList(querywrapper.in("id", Arrays.asList(ids)));
    }

    @Override
    public List<TaskStreamEntity> getTaskStreamListByTaskName(String taskName) {
        QueryWrapper<TaskStreamEntity> querywrapper = new QueryWrapper<>();
        return  taskStreamDao.selectList(querywrapper.in("task_name", taskName).notIn("status",0,4));
    }


}
