package com.pxing.label.business.service;


import com.pxing.label.business.domain.vo.LabelViaProjectVo;

import java.util.List;

public interface LabelViaService {

    List<LabelViaProjectVo> getSreamViaProject(String taskName, String streamId, String userName, String type);

    int updateViaInfo(LabelViaProjectVo labelViaProjectVo);

    int commitViaInfo(LabelViaProjectVo labelViaProjectVo);

    void test();
}
