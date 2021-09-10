package com.pxing.label.business.service;


import com.pxing.label.business.domain.entity.ProjectEntity;


import java.util.List;

public interface LabelProjectService {

    List<ProjectEntity> selectLabelProjectList(ProjectEntity labelProject);

    int insertLabelProject(ProjectEntity labelProject);

    int updateLabelProject(ProjectEntity labelProject);

    int deleteLabelProjectByIds(Long[] projectIds);
}
