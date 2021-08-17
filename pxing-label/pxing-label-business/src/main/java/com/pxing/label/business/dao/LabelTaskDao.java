package com.pxing.label.business.dao;


import com.pxing.label.business.domain.vo.LabelTaskVo;
import org.apache.ibatis.annotations.Param;


import java.util.List;

public interface LabelTaskDao {

    List<LabelTaskVo> selectLabelTaskList(LabelTaskVo labelTaskVo);
}
