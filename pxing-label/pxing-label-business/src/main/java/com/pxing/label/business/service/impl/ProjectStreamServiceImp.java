package com.pxing.label.business.service.impl;


import com.pxing.label.business.dao.ProjectStreamDao;
import com.pxing.label.business.domain.entity.ProjectStreamEntity;
import com.pxing.label.business.service.ProjectStreamService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
public class ProjectStreamServiceImp implements ProjectStreamService {
    @Autowired
    private ProjectStreamDao projectStreamDao;
    @Override
    public int insertProjectStream(Long streamId, Long projectId) {
        ProjectStreamEntity projectStreamEntity= new ProjectStreamEntity();
        projectStreamEntity.setProjectId(projectId);
        projectStreamEntity.setStreamId(streamId);
        return projectStreamDao.insert(projectStreamEntity);
    }
}
