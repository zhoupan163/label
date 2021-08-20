package com.pxing.label.business.service;



import com.mongodb.client.result.UpdateResult;
import com.pxing.label.business.domain.vo.*;


import java.util.List;


public interface LabelTaskService {
    List<LabelTaskVo> selectLabelTaskList(LabelTaskVo labelTask);

    List<LabelTaskViaVo> selectLabelTaskViaInfo(String taskId);


    UpdateResult updateLabelTaskViaInfo(LabelTaskViaVo labelTaskViaVo);

    void insertLabelTaskViaInfo(LabelTaskViaVo labelTaskViaVo);

    void assiginTask(String taskId, String labelBy);

    void labelTask(String taskId, String userName);

    List<LabelStreamVo> getLabelTaskStream(String taskName);

    void assignLabelTaskStream(String streamId, String userName);

    List<LabelTaskImageVo> getLabelTaskUnfinishedStream(String taskName, String token);

    void changeStreamStatus(LabelViaProjectVo labelViaProjectVo);
}
