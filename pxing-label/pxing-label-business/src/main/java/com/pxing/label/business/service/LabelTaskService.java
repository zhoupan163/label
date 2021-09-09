package com.pxing.label.business.service;



import com.mongodb.client.result.UpdateResult;
import com.pxing.label.business.domain.entity.LabelTaskEntity;
import com.pxing.label.business.domain.vo.*;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;


import java.util.List;


public interface LabelTaskService {
    List<LabelTaskVo> selectLabelTaskList(LabelTaskVo labelTask);

    List<LabelTaskViaVo> selectLabelTaskViaInfo(String taskId);

    UpdateResult updateLabelTaskViaInfo(LabelTaskViaVo labelTaskViaVo);

    void insertLabelTaskViaInfo(LabelTaskViaVo labelTaskViaVo);

    List<LabelStreamVo> getLabelTaskStream(String taskName, Query query);

    void assignLabelTaskStream(String taskName, String streamId, String userName, String type);

    List<LabelTaskImageVo> getLabelTaskUnfinishedStream(String taskName, String token);

    void commitTaskImage(LabelViaProjectVo labelViaProjectVo);

    List<LabelTaskImageVo> getFinishedImageList(String taskName, List<String> streamIdList);

    List<Update> updateLabelTaskImages(LabelViaProjectVo labelViaProjectVo);

    int discardImage(String taskName, String streamId, String imgId);

    List<LabelTaskImageVo> selectUnTaggedImageList(String streamId);

    int insertLabelTask(LabelTaskEntity labelTaskEntity);

}
