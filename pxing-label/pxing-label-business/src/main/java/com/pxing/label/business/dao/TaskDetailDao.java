package com.pxing.label.business.dao;



import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import com.pxing.label.business.domain.entity.TaskDetailEntity;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;


public interface TaskDetailDao extends BaseMapper<TaskDetailEntity> {

    @Update("update task_detail set total=total + ${frameSize}, un_label=un_label + ${frameSize} where task_name= '${taskName}'")
    int updateTotal(@Param("frameSize") Integer frameSize, @Param("taskName") String taskName);

    @Update("update task_detail set un_label=un_label - ${number}, labeling=labeling + ${number} where task_name= '${taskName}'" +
            "and un_label >=${number}")
    int pullTaskImage4Label(@Param("taskName") String taskName, @Param("number") Integer number);

    @Update("update task_detail set discarded= discarded + ${number}, labeling= labeling - ${number} where task_name= '${taskName}'" +
            "and labeling >=${number}")
    int discardImage(@Param("taskName") String taskName, @Param("number") Integer number);

    @Update("update task_detail set labeling= labeling - ${number},labeled= labeled + ${number} where task_name= '${taskName}'")
    int commitLabeled(@Param("taskName") String taskName, @Param("number") Integer number);

    @Update("update task_detail set labeled= labeled - ${number}, qa1= qa1 + ${number} where task_name= '${taskName}'" +
            "and labeled >=${number}")
    int pullTaskImage4Qa1(@Param("taskName") String taskName, @Param("number") Integer number);

    @Update("update task_detail set qa1ed= qa1ed + ${number},qa1= qa1 - ${number} where task_name= '${taskName}'"+
            "and qa1 >=${number}")
    int passQa1(@Param("taskName") String taskName, @Param("number") Integer number);

    @Update("update task_detail set qa1= qa1 - ${number}, labeling= labeling + ${number} where task_name= '${taskName}'"+
            "and qa1 >=${number}")
    int rejectQa1(@Param("taskName") String taskName, @Param("number") Integer number);

    @Update("update task_detail set  qa1ed= qa1ed - ${number}, qa2= qa2 + ${number} where task_name= '${taskName}'" +
            "and qa1ed >=${number}")
    int pullTaskImage4Qa2(@Param("taskName") String taskName, @Param("number") Integer number);

    @Update("update task_detail set qa2= qa2 - ${number}, labeling= labeling+ ${number} where task_name= '${taskName}'"+
            "and qa2 >=${number}")
    int rejectQa2(@Param("taskName") String taskName, @Param("number") Integer number);

    @Update("update task_detail set finished= finished + ${number},qa2=qa2 - ${number} where task_name= '${taskName}'"+
            "and qa2 >=${number}")
    int passQa2(@Param("taskName") String taskName, @Param("number") Integer number);


}
