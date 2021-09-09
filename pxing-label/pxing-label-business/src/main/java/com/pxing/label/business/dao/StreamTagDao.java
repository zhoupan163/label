package com.pxing.label.business.dao;



import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pxing.label.business.domain.entity.StreamTagEntity;
import com.pxing.label.business.domain.vo.StreamTagVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;


public interface StreamTagDao extends BaseMapper<StreamTagEntity> {

    @Select("select stream_id,remark from project_stream s where s.project_id= #{projectId} and  s.stream_id not in " +
            "(select stream_id from task_stream where task_id = #{taskId})")
    List<StreamTagVo> getTaggedStreamList(@Param("projectId")Long projectId, @Param("taskId")Long taskId);
}
