package com.pxing.label.business.service;

import com.mongodb.client.result.UpdateResult;
import com.pxing.label.business.domain.vo.*;



import java.util.List;


public interface LabelCheckService {
    List<LabelTaskImageVo> getUnFinishedCheckedImage(String taskName, String userName, int qa_level);

    List<UpdateResult> qa(LabelImageCheck labelImageCheck);

}
