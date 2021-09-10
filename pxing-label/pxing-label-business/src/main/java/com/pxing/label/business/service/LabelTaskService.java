package com.pxing.label.business.service;

import com.pxing.label.business.domain.vo.*;



import java.util.List;


public interface LabelTaskService {
    List<LabelTaskVo> selectLabelTaskList(LabelTaskVo labelTask);

}
