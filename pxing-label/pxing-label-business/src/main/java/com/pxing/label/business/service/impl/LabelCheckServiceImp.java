package com.pxing.label.business.service.impl;

import com.mongodb.client.result.UpdateResult;
import com.pxing.label.business.domain.vo.*;
import com.pxing.label.business.service.LabelCheckService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class LabelCheckServiceImp implements LabelCheckService {
    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public List<LabelTaskImageVo> getUnFinishedCheckedImage(String taskName, String userName, String qa_level) {
        Query query = Query.query(Criteria.where("task_name").is(taskName).and("qa" + qa_level).is(userName).and("image_status").is(qa_level));
        List<LabelTaskImageVo> list = mongoTemplate.find(query, LabelTaskImageVo.class);
        return list;

    }

    @Override
    public List<UpdateResult> qa(String qaType, String streamId, String imgUrl, String qaComment, String operation) {
        Query query = Query.query(Criteria.where("stream_id").is(streamId).and("jpg_url").is(imgUrl));
        List<UpdateResult> updateResultList = new ArrayList<>();

        Update update = new Update();
        if (operation.equals("reject")) {
            update.set("image_status", "4");
            update.set("qa_comment", qaComment);
        } else {
            if (qaType.equals("qa1")) {
                update.set("image_status", "2");
            } else {
                update.set("image_status", "3");
            }
        }

        UpdateResult updateResult = mongoTemplate.updateFirst(query, update, LabelTaskImageVo.class);
        updateResultList.add(updateResult);
        return updateResultList;
    }
}
