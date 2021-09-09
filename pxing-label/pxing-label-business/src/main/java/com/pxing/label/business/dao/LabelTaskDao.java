package com.pxing.label.business.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pxing.label.business.domain.entity.LabelTaskEntity;
import com.pxing.label.business.domain.vo.LabelTaskVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;


import java.util.List;

public interface LabelTaskDao extends BaseMapper<LabelTaskEntity> {

    @Select("select t.*, p.* from label_task1 t left join label_project p on t.project_id= p.id ")
    List<LabelTaskVo> selectLabelTaskList(LabelTaskVo labelTaskVo);

    void updateLabelBy(@Param("taskId") String taskId,@Param("labelBy")String labelBy);
}
