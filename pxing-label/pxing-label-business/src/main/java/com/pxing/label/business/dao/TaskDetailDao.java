package com.pxing.label.business.dao;



import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import com.pxing.label.business.domain.entity.TaskDetailEntity;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;


public interface TaskDetailDao extends BaseMapper<TaskDetailEntity> {

    @Update("update task_detail set total=total + ${frameSize}, un_label=un_label + ${frameSize} where task_name= '${taskName}'")
    int updateTotal(@Param("frameSize") Integer frameSize, @Param("taskName") String taskName);

    @Update("update task_detail set un_label=un_label - ${number}, labeling=labeling + ${number} where task_name= '${taskName}'" +
            "and un_label >${number}")
    int pullTaskImage4Label(@Param("taskName") String taskName, @Param("number") Integer number);

}
