package com.pxing.label.business.service;


import com.pxing.label.business.domain.entity.LabelProjectEntity;


import java.util.List;

public interface LabelProjectService {

    List<LabelProjectEntity> selectLabelProjectList(LabelProjectEntity labelProject);

    int insertLabelProject(LabelProjectEntity labelProject);

    int updateLabelProject(LabelProjectEntity labelProject);

    int deleteLabelProjectByIds(Long[] projectIds);
}
