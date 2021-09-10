package com.pxing.label.business.service.impl;



import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.pxing.label.business.dao.LabelProjectDao;
import com.pxing.label.business.domain.entity.ProjectEntity;
import com.pxing.label.business.service.LabelProjectService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class LabelProjectServiceImp implements LabelProjectService {

    @Autowired
    private LabelProjectDao labelProjectDao;

    @Override
    public List<ProjectEntity> selectLabelProjectList(ProjectEntity labelProject) {
        QueryWrapper<ProjectEntity> querywrapper = new QueryWrapper<>();

        return labelProjectDao.selectLabelProjectList(querywrapper);
    }

    @Override
    public int insertLabelProject(ProjectEntity labelProject) {
        return labelProjectDao.insert(labelProject);
    }

    @Override
    public int updateLabelProject(ProjectEntity labelProject) {
        return 0;
    }

    @Override
    public int deleteLabelProjectByIds(Long[] projectIds) {
        return 0;
    }
}
