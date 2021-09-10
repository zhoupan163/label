package com.pxing.label.business.dao;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pxing.label.business.domain.entity.ProjectEntity;

import java.util.List;

public interface LabelProjectDao extends BaseMapper<ProjectEntity> {

    List<ProjectEntity> selectLabelProjectList(QueryWrapper<ProjectEntity> querywrapper);
}
