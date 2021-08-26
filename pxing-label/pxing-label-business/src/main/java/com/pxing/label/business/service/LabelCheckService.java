package com.pxing.label.business.service;

import com.mongodb.client.result.UpdateResult;
import com.pxing.label.business.domain.vo.*;
import org.springframework.data.mongodb.core.query.Update;


import java.util.List;


public interface LabelCheckService {
    List<LabelTaskImageVo> getUnFinishedCheckedImage(String taskName, String userName, String qa_level);

    List<UpdateResult> qa(String qaType, String streamId, String imgUrl, String qaComment, String operation );
}
