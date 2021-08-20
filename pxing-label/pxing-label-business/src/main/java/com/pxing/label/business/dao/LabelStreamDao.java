package com.pxing.label.business.dao;


import com.pxing.label.business.domain.vo.LabelStreamVo;

import org.apache.ibatis.annotations.Param;


import java.util.List;

public interface LabelStreamDao {

    List<LabelStreamVo> getLabelTaskUnfinishedStream(@Param("taskName") String taskName, @Param("labelBy")String labelBy);

}
