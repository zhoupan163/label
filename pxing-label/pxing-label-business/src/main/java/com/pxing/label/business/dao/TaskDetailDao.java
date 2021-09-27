package com.pxing.label.business.dao;



import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import com.pxing.label.business.domain.entity.TaskDetailEntity;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;


public interface TaskDetailDao extends BaseMapper<TaskDetailEntity> {

    @Update("update task_detail set total=total + ${frameSize} where task_name= '${taskName}'")
    int updateTotal(@Param("frameSize") Integer frameSize, @Param("taskName") String taskName);

}
