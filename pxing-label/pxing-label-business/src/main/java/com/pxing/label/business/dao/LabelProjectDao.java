package com.pxing.label.business.dao;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pxing.label.business.domain.entity.LabelProjectEntity;

import java.util.List;

public interface LabelProjectDao extends BaseMapper<LabelProjectEntity> {

    List<LabelProjectEntity> selectLabelProjectList(QueryWrapper<LabelProjectEntity> querywrapper);
}
