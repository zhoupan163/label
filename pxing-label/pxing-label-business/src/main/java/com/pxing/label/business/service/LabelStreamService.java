package com.pxing.label.business.service;


import com.pxing.label.business.domain.vo.LabelStreamVo;

import java.util.List;

public interface LabelStreamService {

    List<LabelStreamVo> getLabelTaskUnfinishedStream(String taskName, String userName);
}
