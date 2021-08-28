package com.pxing.label.business.service;


import com.pxing.label.business.domain.vo.LabelStreamVo;

import java.util.List;

public interface LabelStreamService {

    List<LabelStreamVo> getLabelTaskUnfinishedStream(String taskName, String userName);

    void insertLabelStreamVo(LabelStreamVo labelStreamVo);

    void updateLabelStreamService(LabelStreamVo labelStreamVo);

    List<LabelStreamVo> getUnCheckedStream(LabelStreamVo labelStreamVo);
}
