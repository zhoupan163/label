package com.pxing.label.business.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pxing.label.business.domain.vo.LabelStreamVo;




import java.util.List;
public interface LabelStreamDao extends BaseMapper<LabelStreamVo> {

    //List<LabelStreamVo> getLabelTaskUnfinishedStream(@Param("taskName") String taskName, @Param("labelBy")String labelBy);

    void insertLabelStreamVo(LabelStreamVo labelStreamVo);

    void updateLabelStreamVo(LabelStreamVo labelStreamVo);

    List<LabelStreamVo> getLabelTaskUnCheckedStream(LabelStreamVo labelStreamVo);
}
