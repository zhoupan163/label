package com.pxing.label.business.dao;



import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pxing.label.business.domain.entity.LabelTagEntity;
import com.pxing.label.business.domain.vo.LabelTagVo;
import org.apache.ibatis.annotations.Select;


import java.util.List;


public interface LabelTagDao extends BaseMapper<LabelTagEntity> {

    @Select("select t.id, t.tag_name, s.scene_name, t.scene_id from label_tag t join label_scene s on s.id= t.scene_id " +
            "where s.project_id= #{projectId}")
    List<LabelTagVo> selectTagListByProjectId(Long projectId);
}
