package com.pxing.label.business.service;


import com.pxing.label.business.domain.vo.LabelViaProjectVo;

import java.util.List;

public interface LabelViaService {

    List<LabelViaProjectVo> insertSreamViaProject(String streamId,String taskName);

    List<LabelViaProjectVo> getSreamViaProject(String taskName, String userName, String type);

    List<LabelViaProjectVo> updateSreamViaProject(LabelViaProjectVo labelViaProjectVo);

}
