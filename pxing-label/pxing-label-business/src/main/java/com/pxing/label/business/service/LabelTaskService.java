package com.pxing.label.business.service;



import com.pxing.label.business.domain.vo.LabelTaskViaVo;
import com.pxing.label.business.domain.vo.LabelTaskVo;


import java.util.List;


public interface LabelTaskService {
    public List<LabelTaskVo> selectLabelTaskList(LabelTaskVo labelTask);

    List<LabelTaskViaVo> selectLabelTaskViaInfo(String taskId);
}
